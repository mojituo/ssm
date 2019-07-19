package com.qf.service.impl;

import com.qf.mapper.ItemMapper;
import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-16 21:42
 **/

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageInfo<Item> findItemAndLimit(String name, Integer page, Integer size) {

        Long total = itemMapper.findCountByName(name);

        List<Item> data = itemMapper.findByItemByNameLikeAndLimit(name, (page-1)*size, size);

        PageInfo<Item> pageInfo = new PageInfo<>(page,size,total,data);

        return pageInfo;
    }

    //添加用户信息
    @Override
    public Integer save(Item item) {

        return itemMapper.save(item);
    }

    //删除商品
    @Override
    public Integer delete(Long id) {
        return itemMapper.delete(id);
    }

    //根据id查询商品
    @Override
    public Item findById(Long id) {

        return itemMapper.findById(id);
    }

    //修改商品
    @Override
    public Integer update(Item item) {
        return itemMapper.update(item);
    }
}
