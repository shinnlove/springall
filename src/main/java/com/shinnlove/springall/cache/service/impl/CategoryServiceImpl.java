/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.cache.service.impl;

import com.shinnlove.springall.cache.domain.model.Category;
import com.shinnlove.springall.cache.service.CategoryService;

/**
 * 服务实现。
 *
 * @author shinnlove.jinsheng
 * @version $Id: CategoryServiceImpl.java, v 0.1 2018-07-25 下午10:37 shinnlove.jinsheng Exp $$
 */
public class CategoryServiceImpl implements CategoryService {

    @Override
    public Category getCategoryBySortId(int sortId) {
        return new Category(sortId, "服装");
    }

}