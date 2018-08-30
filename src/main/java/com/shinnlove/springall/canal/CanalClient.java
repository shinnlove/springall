/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.canal;

import static com.alibaba.otter.canal.protocol.CanalEntry.*;

import java.util.List;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Canal客户端使用示例。
 *
 * @author shinnlove.jinsheng
 * @version $Id: CanalClient.java, v 0.1 2018-08-30 下午2:36 shinnlove.jinsheng Exp $$
 */
public class CanalClient {

    /** zookeeper IP套接字 */
    private static final String ZK_SERVERS  = "192.168.61.129:2181";

    /** 订阅目标 */
    private static final String DESTINATION = "product";

    /** 订阅用户名 */
    private static final String USER_NAME   = "root";

    /** 订阅密码 */
    private static final String PASSWORD    = "";

    public static void main(String[] args) throws InvalidProtocolBufferException {

        // 建立canal客户端并连接zk订阅canal服务端
        CanalConnector connector = CanalConnectors.newClusterConnector(ZK_SERVERS, DESTINATION,
            USER_NAME, PASSWORD);
        connector.connect();
        connector.subscribe("product_.*\\.product_.*");

        // 不间断处理
        while (true) {

            // Step1：批量拉取1000个日志（不处理不确认模式）
            // 返回条件：尝试拿batchSize条记录，有多少取多少，不会阻塞等待!!!
            Message message = connector.getWithoutAck(1000);

            // Step2：执行日志消费逻辑，可投递到MQ
            for (Entry entry : message.getEntries()) {
                // 如果是行数据
                if (entry.getEntryType() == EntryType.ROWDATA) {
                    // 解析行变更
                    RowChange row = RowChange.parseFrom(entry.getStoreValue());
                    // 行数据操作类型
                    EventType rowEventType = row.getEventType();
                    for (RowData rowData : row.getRowDatasList()) {
                        // 如果是删除（枚举直接比较即可）
                        if (rowEventType == EventType.DELETE) {
                            List<Column> columns = rowData.getBeforeColumnsList();
                            // 做删除
                        }
                        if (rowEventType == EventType.INSERT || rowEventType == EventType.UPDATE) {
                            List<Column> columns = rowData.getAfterColumnsList();
                            // 做新增或修改
                        }
                    }

                } // end rowData

            } // end for

            // Step3：确认日志消费成功（对拉取的每一批消息都有一个Id，确认这个Id即可）
            connector.ack(message.getId());

        } // end while

    }

}