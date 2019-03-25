/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.util.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shinnlove.springall.util.code.SystemResultCode;

/**
 * 标准系统错误定义。
 *
 * 温馨提示：
 * 一般你抛出一个错误会用`throw new RuntimeException("reason")`;
 * 但不建议你这么做，因为多层级错误只会保留堆栈关系，而不会将每次简短的错误信息压栈
 *      ——当你提示给用户的时候，显示错误信息可能不完整(你想递归取cause?你试试...)、或者想完整显示又苦于所有堆栈。
 *
 * 请用`throw new SystemException...`来代替，可以完整保留整个异常堆栈的简要信息、错误码，然后按序打印!!!
 *
 * 一般我们定义异常有三种情况：
 * 1、(自定义)我认为是一个异常，如：某个值不满足预期值、某个服务返回业务处理结果isSuccess是false；
 *
 * if (isSuccess == false) {
 *     throw new SystemException(SystemResultCode.服务调用业务处理异常, "尼玛为什么业务没处理成功");
 * }
 *
 * 2、(被引发)有一个已检查的cause异常引起了新的异常，被我try...catch捕捉到了，进而构造成我指定的某大类异常；
 *
 * private void calculate() throws SystemException {
 *    int a = 10, b = 0;
 *    try {
 *        System.out.println(String.valueOf(a / b));
 *    } catch(Exception e) {
 *        // you may use
 *        throw new SystemException(SystemResultCode.计算指标异常, e);
 *        // another choice
 *        throw new SystemException(SystemResultCode.计算指标异常, e, "I have sth. to say, the fucking...");
 *    }
 * }
 *
 * 3、(转型)将一个已检查的`SystemException`小类异常包装转换成某统一类型异常，方便统一切面处理。
 *
 * class CalculateException extends SystemException {
 *     ...
 * }
 *
 * try {
 *      calculate();
 * } catch(SystemException e) {
 *      throw new CalculateException(SystemResultCode.主链路指标计算异常, e);
 * }
 *
 * 最后你可以很优雅的打印：
 *
 * a) 摘要信息
 *
 * catch(SystemException e) {
 *     System.out.println(e.getDigestStr());
 * }

 * b) 根本原因
 *
 * catch(SystemException e) {
 *     System.out.println(e.getRootCause());
 * }
 *
 * c) 最上层原因（类似你throw new RuntimeException("reason")里的reason）
 *
 * catch(SystemException e) {
 *     System.out.println(e.getBrief());
 * }
 *
 * d) 完整错误码与开发人员所需堆栈
 *
 * catch(SystemException e) {
 *     System.out.println(e.getDetailStr());
 * }
 *
 *
 * @author shinnlove.jinsheng
 * @version $Id: SystemException.java, v 0.1 2018-01-17 下午8:16 shinnlove.jinsheng Exp $$
 */
public class SystemException extends RuntimeException implements BaseException {

    /** 错误码枚举 */
    private final SystemResultCode          resultCode;

    /** 异常场景信息详情(给开发看的)：0->[错误码=>错误信息] */
    private final List<Map<String, String>> detail = new ArrayList<>();

    /** 异常场景信息摘要(不带错误码) */
    private final List<String>              digest = new ArrayList<>();

    /**
     * 构造函数，自定义错误的时候用，没有cause的ex!
     *
     * Tips：错误信息跟着错误码中的描述走的构造器。
     *
     * @param resultCode
     */
    public SystemException(SystemResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
        this.digest.add(resultCode.getMessage());
        addDetail(resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * 构造函数，自定义错误的时候用，没有cause的ex!
     *
     * Tips：可以自定义错误信息提示的构造器，message是提示给前端的简要错误。
     *
     * @param resultCode
     * @param message
     */
    public SystemException(SystemResultCode resultCode, String message) {
        super(resultCode.getMessage() + ":" + message);
        this.resultCode = resultCode;
        this.digest.add(resultCode.getMessage() + ":" + message);
        addDetail(resultCode.getCode(), resultCode.getMessage() + ":" + message);
    }

    /**
     * 推荐使用构造器：
     *
     * 使用自定义的错误信息打印摘要。
     *
     * Tips：当外部引起错误，不想吃掉错误堆栈、并且增加内+外的简要描述信息时使用。
     *
     * @param resultCode 内部错误码
     * @param cause 引起错误的原因（外）
     * @param message 错误原因描述（内+外）
     */
    public SystemException(SystemResultCode resultCode, Throwable cause, String message) {
        super(resultCode.getMessage() + ":" + message, cause);
        this.resultCode = resultCode;
        String msg = resultCode.getMessage() + ":" + message + ":" + cause.getMessage();
        this.digest.add(msg);
        addDetail(resultCode.getCode(), msg);
    }

    /**
     * 推荐使用构造器：
     *
     * 使用自定义的错误信息打印摘要。
     *
     * Tips：当外部引起错误，不想吃掉错误堆栈、并且增加内+外的简要描述信息时使用。
     *
     * @param resultCode 内部错误码
     * @param cause 引起错误的原因（外）
     * @param message 错误原因描述（内+外）
     */
    public SystemException(SystemResultCode resultCode, SystemException cause, String message) {
        super(resultCode.getMessage() + ":" + message, cause);
        this.resultCode = resultCode;
        // 原来的
        this.digest.addAll(cause.getDigest());
        // 这个异常的
        this.digest.add(resultCode.getMessage());
        // 原来的
        this.detail.addAll(cause.getDetail());
        // 这个异常的
        addDetail(resultCode.getCode(), resultCode.getMessage() + ":" + message);
    }

    /**
     * 构造函数。
     *
     * 没有自定义的错误信息，使用错误码的错误信息打印摘要。
     *
     * Tips：当外部引起错误，不想吃掉错误堆栈使用。
     *
     * @param resultCode
     * @param cause
     */
    public SystemException(SystemResultCode resultCode, Throwable cause) {
        super(resultCode.getMessage(), cause);
        this.resultCode = resultCode;
        this.digest.add(resultCode.getMessage());
        addDetail(resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * 构造函数。
     *
     * 专门用来将一个`SystemException`类型错误转换为另一个`SystemException`子类。
     *
     * @param resultCode
     * @param cause
     */
    public SystemException(SystemResultCode resultCode, SystemException cause) {
        super(resultCode.getMessage() + ":" + cause.getResultCode().getMessage(), cause);
        this.resultCode = resultCode;
        // 原来的
        this.digest.addAll(cause.getDigest());
        // 这个异常的
        this.digest.add(resultCode.getMessage());
        // 原来的
        this.detail.addAll(cause.getDetail());
        // 这个异常的
        addDetail(resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * (请少用)系统错误构造器，只给出错误提示信息。
     *
     * @param message
     */
    public SystemException(String message) {
        super(message);
        SystemResultCode code = SystemResultCode.SYSTEM_ERROR;
        this.resultCode = code;
        this.digest.add(code.getMessage() + ":" + message);
        addDetail(code.getCode(), code.getMessage() + ":" + message);
    }

    /**
     * (请少用)系统错误构造器，有cause的ex时候使用。
     *
     * @param message
     * @param throwable
     */
    public SystemException(String message, Throwable throwable) {
        super(message, throwable);
        SystemResultCode code = SystemResultCode.SYSTEM_ERROR;
        this.resultCode = code;
        String msg = code.getMessage() + ":" + message + ":" + throwable.getMessage();
        this.digest.add(msg);
        addDetail(code.getCode(), msg);
    }

    /**
     * Getter method for property resultCode.
     *
     * @return property value of resultCode
     */
    public SystemResultCode getResultCode() {
        return resultCode;
    }

    /**
     * 错误代码
     *
     * @return
     */
    public String getCode() {
        return null == resultCode ? "" : resultCode.getCode();
    }

    /**
     * @see BaseException#getResultCodeStr()
     */
    @Override
    public String getResultCodeStr() {
        return null == resultCode ? "" : resultCode.getCode();
    }

    /**
     * 错误描述
     *
     * @return
     */
    public String getDescription() {
        return null == resultCode ? "" : resultCode.getMessage();
    }

    /**
     * @see BaseException#getException()
     */
    @Override
    public Exception getException() {
        return this;
    }

    /**
     * @see BaseException#getBrief()
     */
    @Override
    public String getBrief() {
        int len = digest.size();
        if (len > 0) {
            return digest.get(len - 1);
        }
        return "";
    }

    /**
     * @see BaseException#getBrief()
     */
    @Override
    public String getRootCause() {
        return digest.size() > 0 ? digest.get(0) : "";
    }

    /**
     * @see BaseException#getDigest()
     */
    @Override
    public List<String> getDigest() {
        return digest;
    }

    /**
     * @see BaseException#getDigest()
     */
    @Override
    public String getDigestStr() {
        if (digest.isEmpty()) {
            return "";
        }
        // 改写ArrayList的ToString()
        StringBuilder sb = new StringBuilder();
        int len = digest.size();
        if (len > 0) {
            for (int i = len - 1; i > 0; i--) {
                sb.append(digest.get(i)).append(", ");
            }
            sb.append(digest.get(0));
        }
        return digest.toString();
    }

    /**
     * @see BaseException#getDetail()
     */
    @Override
    public List<Map<String, String>> getDetail() {
        return detail;
    }

    /**
     * @see BaseException#getDetail()
     */
    @Override
    public String getDetailStr() {
        if (detail.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int len = detail.size();
        if (len > 0) {
            for (int i = len - 1; i > 0; i--) {
                sb.append(detail.get(i).toString()).append(", ");
            }
            sb.append(detail.get(0).toString());
        }
        return sb.toString();
    }

    /**
     * @see BaseException#addDetail(String, String)
     */
    @Override
    public void addDetail(String key, String value) {
        Map<String, String> oneDetail = new HashMap<>();
        oneDetail.put(key, value);
        detail.add(oneDetail);
    }

}