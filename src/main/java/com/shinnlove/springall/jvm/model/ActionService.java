/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.jvm.model;

/**
 * 动作服务接口。
 *
 * @author shinnlove.jinsheng
 * @version $Id: ActionService.java, v 0.1 2019-03-09 16:21 shinnlove.jinsheng Exp $$
 */
public interface ActionService {

    /**
     * 打招呼的动作。
     *
     * @param toWho
     * @return
     */
    String sayHello(String toWho);

}
