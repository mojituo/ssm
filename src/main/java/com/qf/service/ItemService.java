package com.qf.service;

import com.qf.pojo.Item;
import com.qf.utils.PageInfo;

public interface ItemService {

    PageInfo<Item> findItemAndLimit(String name, Integer page, Integer size);
    //添加用户信息
    Integer save(Item item);

    //删除商品
    Integer delete (Long id);

    Item findById(Long id);

    Integer update(Item item);
}
