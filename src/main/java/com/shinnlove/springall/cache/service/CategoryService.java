/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shinnlove.springall.cache.service;

import com.shinnlove.springall.cache.domain.model.Category;

/**
 * 分类服务。
 *
 * @author shinnlove.jinsheng
 * @version $Id: CategoryService.java, v 0.1 2018-07-25 下午10:31 shinnlove.jinsheng Exp $$
 */
public interface CategoryService {

    /**
     * 根据排序获取一个商品类目。
     *
     * @param sortId
     * @return
     */
    Category getCategoryBySortId(int sortId);

}
