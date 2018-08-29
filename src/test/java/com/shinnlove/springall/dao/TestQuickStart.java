/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao;

import com.shinnlove.springall.model.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试快速使用hibernate jpa。
 *
 * @author shinnlove.jinsheng
 * @version $Id: TestQuickStart.java, v 0.1 2018-08-29 下午10:10 shinnlove.jinsheng Exp $$
 */
public class TestQuickStart {

    private static ApplicationContext ctx;

    static {
        // 通过静态代码块的方式,让程序加载spring的配置文件
        ctx = new ClassPathXmlApplicationContext("classpath:META-INF/spring/applicationContext.xml");
    }

    /**
     * 测试PersonDao中定义的getById的方法能否查询出结果
     */
    @Test
    public void testGetById() {
        PersonDao personDao = ctx.getBean(PersonDao.class);
        Person person = personDao.getById(1);
        if (person != null) {
            System.out.println("查询结果： person=" + person);
        }
    }

}
