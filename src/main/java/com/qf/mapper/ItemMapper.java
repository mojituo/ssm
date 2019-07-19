package com.qf.mapper;

import com.qf.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-16 21:43
 **/


public interface ItemMapper {


    //1.查询商品的总条数
    Long findCountByName(@Param("name")String name);

    //2.分页查询商品的总信息
    List<Item> findByItemByNameLikeAndLimit(@Param("name")String name,
                                           @Param("offset")Integer offset,
                                           @Param("size")Integer size);
    //3.添加商品
    Integer save(Item item);

    //4.删除商品
    Integer delete(Long id);

    Item findById(Long id);

    Integer update(Item item);
}
