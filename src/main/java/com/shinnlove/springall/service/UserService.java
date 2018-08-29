/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service;

import com.shinnlove.springall.model.User;

/**
 * 服务接口。
 *
 * @author shinnlove.jinsheng
 * @version $Id: UserService.java, v 0.1 2018-06-03 下午4:07 shinnlove.jinsheng Exp $$
 */
public interface UserService {

    User getEmailByName(String name);

}