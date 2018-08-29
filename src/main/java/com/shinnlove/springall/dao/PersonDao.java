/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.dao;

import org.springframework.data.repository.Repository;

import com.shinnlove.springall.model.Person;

/**
 * 扩展自`org.springframework.data.repository.Repository`的接口DAO，都会被JPA实现并注入到spring的bean工厂中。
 *
 * @author shinnlove.jinsheng
 * @version $Id: PersonDao.java, v 0.1 2018-08-29 下午10:06 shinnlove.jinsheng Exp $$
 */
public interface PersonDao extends Repository<Person, Integer> {

    Person getById(Integer id);

}