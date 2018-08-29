/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.shinnlove.springall.model.User;
import com.shinnlove.springall.service.UserService;

/**
 * 具体服务类。
 *
 * 用注解发布，这样能够让多个RPC框架扫到这个服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: UserServiceImpl.java, v 0.1 2018-06-03 下午4:09 shinnlove.jinsheng Exp $$
 */
public class UserServiceImpl implements UserService {

    private static final Map<String, User> userMap = new HashMap<>();

    static {
        userMap.put("qianqian", new User("qianqian", "957047288@qq.com"));
        userMap.put("bianbian", new User("bianbian", "306086640@qq.com"));
    }

    @Override
    public User getEmailByName(String name) {
        return userMap.get(name);
    }

}