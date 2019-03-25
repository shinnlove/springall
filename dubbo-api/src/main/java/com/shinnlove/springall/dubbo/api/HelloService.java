/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.shinnlove.springall.dubbo.api;

/**
 * 服务接口。
 *
 * @author shinnlove.jinsheng
 * @version $Id: HelloService.java, v 0.1 2019-03-25 16:20 shinnlove.jinsheng Exp $$
 */
public interface HelloService {

    /**
     * Rpc方法之一。
     *
     * @param name
     * @return
     */
    String sayHello(String name);

}
