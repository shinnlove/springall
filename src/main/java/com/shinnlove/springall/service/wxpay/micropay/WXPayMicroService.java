/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.service.wxpay.micropay;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.shinnlove.springall.dao.mch.MchWXPayConfigRepository;
import com.shinnlove.springall.dao.model.WXPayRecord;
import com.shinnlove.springall.dao.wxpay.WXPayRecordRepository;
import com.shinnlove.springall.service.wxpay.order.WXPayQueryOrderService;
import com.shinnlove.springall.service.wxpay.request.WXPayRequestService;
import com.shinnlove.springall.service.wxpay.util.WXPayAssert;
import com.shinnlove.springall.util.code.SystemResultCode;
import com.shinnlove.springall.util.exception.SystemException;
import com.shinnlove.springall.util.log.LoggerUtil;
import com.shinnlove.springall.util.wxpay.sdkplus.config.WXPayMchConfig;
import com.shinnlove.springall.util.wxpay.sdkplus.consts.WXPayConstants;
import com.shinnlove.springall.util.wxpay.sdkplus.service.request.micro.MicroPayClient;

/**
 * 微信刷卡支付服务。
 *
 * 刷卡支付中包含微信订单查询服务，因为当验密支付的时候，第一次microPay返回的是USERPAYING的状态。
 *
 * @author shinnlove.jinsheng
 * @version $Id: WXPayMicroService.java, v 0.1 2019-01-11 20:30 shinnlove.jinsheng Exp $$
 */
@Service
public class WXPayMicroService {

    /** log4j2日志 */
    private static final Logger      LOGGER = LoggerFactory.getLogger(WXPayMicroService.class);

    /** 平台支付层的id常量 */
    private static final String      PAY_ID = "pay_id";

    /** tx-template */
    @Autowired
    private TransactionTemplate      transactionTemplate;

    /** 微信支付记录仓储 */
    @Autowired
    private WXPayRecordRepository    wxPayRecordRepository;

    /** 微信配置仓储 */
    @Autowired
    private MchWXPayConfigRepository mchWXPayConfigRepository;

    /** 微信支付服务SDK */
    @Autowired
    private WXPayRequestService      wxPayRequestService;

    /** 微信支付订单查询服务 */
    @Autowired
    private WXPayQueryOrderService   wxPayQueryOrderService;

    /**
     * 进行微信刷卡支付，尽可能等待用户微信侧的支付成功。
     *
     * TODO：JSAPI和刷卡的前置校验商户微信支付配置的代码可以抽象统一。
     *
     * 一、刷卡支付用户侧业务场景解读：
     * 
     * 宗旨：对于订单系统，刷卡支付只是一种支付方式，订单系统只关心这笔订单是否被支付了。
     * 
     * 微信刷卡支付时，如果用户输入密码出错2次、或者点了关闭，这次刷卡支付就是失败的。再次调用刷卡支付，传给微信同一个`out_trade_no`会永远返回`TRADE_ERROR`。
     *
     * 对于商户的订单系统来说，门店终端client提交过来的10件商品被打包下到了一个订单中，然后调用支付系统发起支付，这个订单是在系统中已经存在的。
     * 一般的，对于单机单zone的应用可以粗暴的将订单关闭，重复让门店终端再次提交下单，生成新订单进行刷卡支付。但一般不推荐这么做，系统中会出现大量无用订单。
     *
     * 在分布式环境中，当支付系统被订单系统调用微信刷卡支付时：支付系统应该将这笔订单记到一张订单号字段唯一的表中，并生成一个支付号作为`out_trade_no`传给微信。
     * 当用户因为输错密码、或不小心点了关闭支付按钮、还想重新刷卡支付时，订单系统会发起对原订单的支付重试。
     * 支付系统收到重试后，发现表中原订单未被支付，再生成一个支付号作为`out_trade_no`传给微信去做刷卡支付。
     * 这样就保证了同一笔订单能反复被刷卡支付、或选择其他支付方式，支付系统对订单系统屏蔽底层支付工具的差异。
     * 
     * 这个支付号在支付系统中唯一，当支付工具返回成功，这个支付号就是订单支付的唯一凭证。
     * 它既可以是微信支付的`out_trade_no`、也可以是支付宝的`out_trade_no`，与订单号彻底解耦。
     *
     * 二、刷卡支付分布式环境下系统交互方式：
     *
     * 一般的，对于单机单zone的应用，当门店终端提交刷卡支付请求后，用户进入验密流程，服务端需要等待轮询微信订单状态。此时应用会hold住请求，直到30秒超时。
     * 终端请求支付，开始显示Loading...直到支付成功、失败或超时。
     * 当超时返回的原因就是用户支付中（微信等待用户输入密码允许1分钟），终端会继续请求服务端，直到得到成功或失败的结果。这1、2次请求都是阻塞中。
     * 
     * 体验好点的应用，第一次提交刷卡支付得到微信用户支付中提示，会直接返回给终端，开启异步线程去查询订单是否支付成功，并勾兑数据库。
     * 终端第一时间得到响应，显示用户支付中...，设置定时3秒轮询10次服务端，直到成功或超时（超时就无脑撤销订单了、无论是否最终成功）。
     * 这样终端（前端）需要加入定时轮询的代码。
     *
     * 在分布式系统中，当终端提交下单刷卡的请求到服务端、订单系统请求支付系统进行支付时，支付系统可以hold住请求轮询直到超时（做法与单机单zone2次一样）。
     * 支付系统也可以当即将第一次结果返回给订单系统，随后开启定时异步线程去轮询订单状态，勾兑数据库，坐等订单系统再来轮询。
     *
     * 无论订单系统前端定时触发、服务端被动请求；还是订单系统hold住自己终端，在服务端异步轮询支付系统；这两种方案都可行，大道从简。
     *
     * 三、分布式系统下支付系统的幂等性：
     *
     * 当订单系统请求支付时，检查订单号是否存在，如果是首付将订单号记录到订单待支付表中（订单号唯一索引）、状态未支付；
     * 而后开启事务对这笔订单加以innodb行级悲观锁，锁住成功后判断订单是否已被支付，若支付退出事务。（幂等判断1）
     * 如果订单没有被支付，再判断支付表中是否已经存在大于等于一条刷卡支付的记录。
     * 如果存在则是某次刷卡遗留下一条记录，同时返回用户支付中，事务结束。（幂等判断2，其他线程不要去做历史清扫工作!）
     * 如果不存在刷卡支付记录，代表可以安全的做一次完整的刷卡支付：生成一个唯一的支付号连同订单id插入刷卡支付表中，再调用微信刷卡支付。
     * 如果整个刷卡过程成功，则最后update订单待支付表中的状态字段变成已支付，提交事务，在刷卡支付表中留下支付号这条记录。
     *
     * 如果整个刷卡过程因用户输入密码需要异步，可以提交事务留下这条记录，同时开启异步任务。（刷卡结果轮询异步化改造预留接口点）
     *
     * 如果整个刷卡过程失败，则抛错事务回滚，支付表中没有这个订单的支付记录、订单状态也没有被勾兑。
     * 如果整个刷卡过程10次都返回用户支付中，判定为刷卡过程超时遗留。则事务也完成提交、但不勾兑支付状态。留下一条刷卡支付记录，同时开启异步任务。（同步hold住主线程）
     *
     * 支付系统将为这次遗留任务开启一个异步任务：
     * 开启事务、对订单待支付表加行级悲观锁、加锁成功判断状态、订单未支付则查询刷卡记录表是否有历史刷卡记录。如果没有记录事务结束。（幂等判断3）
     * 如果有大于等于1条的刷卡遗留记录，判断这条记录状态：如果是进行中状态，就进行3秒一次、持续1分钟轮询订单状态。
     * 如果最终支付成功，勾兑待支付表状态，事务提交。如果最终支付失败，事务结束，释放锁。
     *
     * 异步任务将由完整刷卡过程产生，并且只持续1分钟，仍然无法判定则留下这条记录等待订单系统来做撤单。
     *
     * 四、刷卡撤单与幂等：
     *
     * 撤单场景只会发生在：用户显示自己手机已经支付了，而商家终端还显示用户支付中。
     * 此时商户终端发起撤单，订单系统调用支付系统撤单。
     *
     * 撤单同样需要开启事务，进行订单支付表的锁定，再查询刷卡记录表，将`out_trade_no`取出，调用微信撤单接口。
     * 当且仅当微信返回撤单成功，将这条刷卡记录的订单号、支付号记录到撤单表中，同时删除这条刷卡记录，事务结束。
     *
     * 主订单表的订单若未支付，还是可以重复支付。
     *
     * @param orderId    
     * @param merchantId
     * @param payParams
     * @return
     */
    public Map<String, String> microPay(final long orderId, final long merchantId,
                                        final Map<String, String> payParams) {
        // ready?
        WXPayMchConfig mchConfig = mchWXPayConfigRepository.queryWXPayConfigByMchId(merchantId);
        WXPayAssert.confAvailable(mchConfig);

        // 首付?(db-uk幂等)
        WXPayRecord record = wxPayRecordRepository.queryPayRecordByOrderId(orderId);
        if (record == null) {
            record = buildWXPayRecord(orderId, merchantId, payParams);
            wxPayRecordRepository.insertRecord(record);
        }

        Map<String, String> result = new HashMap<>();

        try {
            // 做刷卡支付
            result = wxPayRequestService.doPayRequest(
                new MicroPayClient(mchConfig),
                payParams,
                (resp) -> {

                    LoggerUtil.info(LOGGER, "刷卡支付resp=", resp);

                    WXPayAssert.checkMicroPayResp(resp);

                    String resultCode = resp.get(WXPayConstants.RESULT_CODE);
                    String errCode = resp.get(WXPayConstants.ERR_CODE);

                    if (WXPayConstants.FAIL.equalsIgnoreCase(resultCode)) {
                        if (!WXPayConstants.USERPAYING.equalsIgnoreCase(errCode)
                            && !WXPayConstants.SYSTEMERROR.equalsIgnoreCase(errCode)) {
                            String errMsg = resp.get(WXPayConstants.ERR_CODE_DES);
                            throw new SystemException("刷卡支付返回业务码状态错误：" + errMsg);
                        }
                    }

                    return null;
                });
        } catch (SystemException e) {
            // 有马甲
            throw e;
        } catch (Exception e) {
            // 超时、dns解析、签名验签、稀奇古怪错误等统统穿上马甲扔给切面
            throw new SystemException(SystemResultCode.WXPAY_MICRO_PAY_ERROR, e, e.getMessage());
        }

        // 第一次刷卡支付的返回码和业务结果
        String resultCode = result.get(WXPayConstants.RESULT_CODE);

        // 刷卡支付如果成功直接盖章平台支付id并返回
        if (WXPayConstants.SUCCESS.equalsIgnoreCase(resultCode)) {
            LoggerUtil.info(LOGGER, "刷卡支付免密返回结果：resp=", result);
            result.put(PAY_ID, String.valueOf(record.getId()));
            return result;
        }

        // 代码走到这里，一定是刷卡支付没有双SUCCESS（很有可能用户支付中），需要开始循环查询订单状态
        // TODO：这样做是hold住了主线程，导致商户侧订单系统请求支付系统一直waiting。
        //  可以直接返回用户支付中给订单系统，然后让其定时查询（最多30秒不行撤单）确定是否支付成功。
        //  这样如下的代码可以放在异步线程中去做，以DB状态为衔接点。
        int retryCount = 10;
        while (retryCount > 0) {

            // 特别注意：这里每一次的查询出错，都可以忽略；等到下一个周期再做查询

            try {

                Map<String, String> queryResult = wxPayQueryOrderService.queryWXPayOrder(orderId,
                    merchantId);

                LoggerUtil.info(LOGGER, "查询刷卡支付第", retryCount, "返回resp=", queryResult);

                String holdStatus = queryResult.get(WXPayConstants.MICRO_HOLD_STATUS);
                int hs = Integer.valueOf(holdStatus);

                if (hs == 1) {
                    // 成功，根据订单查询结果修改第一次刷卡支付字段，并直接函数返回...不再循环!!
                    result.put(WXPayConstants.RETURN_CODE, WXPayConstants.SUCCESS);
                    result.put(WXPayConstants.RESULT_CODE, WXPayConstants.SUCCESS);
                    result.remove(WXPayConstants.ERR_CODE);
                    result.remove(WXPayConstants.ERR_CODE_DES);

                    return result;

                } else if (hs == 2) {
                    sleepSilently(2);
                } else {
                    // 其他情况一律异常不再重新查询
                    break;
                }

            } catch (Exception e) {
                LoggerUtil.error(LOGGER, e, "刷卡支付验密中查询第", retryCount, "次出错，e=", e);
                sleepSilently(2);
            }

            retryCount--;

        } // end while

        // TODO：代码走到这里，不得不说努力10次都未成功真是遗憾，只好开启异步任务1分钟20次不停轮询了...

        return result;
    }

    /**
     * 安静地等待。
     *
     * @param timeout
     */
    private void sleepSilently(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            LoggerUtil.error(LOGGER, e, e);
        }
    }

    /**
     * 新创建一条微信支付记录。
     *
     * @param orderId
     * @param merchantId
     * @param payParams
     * @return
     */
    private WXPayRecord buildWXPayRecord(long orderId, long merchantId,
                                         Map<String, String> payParams) {
        WXPayRecord record = new WXPayRecord(orderId, merchantId);
        // 做个塞字段示例
        record.setOpenId(payParams.get(WXPayConstants.OPENID));
        return record;
    }

}