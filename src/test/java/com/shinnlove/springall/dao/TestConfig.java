/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 注意数据库连接信息。
 *
 * @author shinnlove.jinsheng
 * @version $Id: TestConfig.java, v 0.1 2018-08-29 下午10:10 shinnlove.jinsheng Exp $$
 */
public class TestConfig {

    private static ApplicationContext ctx ;
    static {
        // 通过静态代码块的方式,让程序加载spring的配置文件
        ctx = new ClassPathXmlApplicationContext("classpath:META-INF/spring/applicationContext.xml");
    }

    /** 测试spring容器是否实例化了数据源 。如果实例化了，说明Spring容器整合没问题 */
    @Test
    public void testDataSouce() throws SQLException {
        DataSource dataSouce = (DataSource) ctx.getBean("dataSource");
        System.out.println("数据源："+ dataSouce);
        System.out.println("连接："+ dataSouce.getConnection());
    }

}
