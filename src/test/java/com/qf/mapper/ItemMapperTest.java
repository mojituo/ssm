package com.qf.mapper;

import com.qf.AcTest;
import com.qf.pojo.Item;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ItemMapperTest extends AcTest {


    @Autowired
    private ItemMapper itemMapper;
    @Test
    public void findCountByName() {
        Long  count= itemMapper.findCountByName("英国");
        System.out.println(count);
    }

    @Test
    public void findByItemByNameLikeAndLimit() {

        List<Item> it = itemMapper.findByItemByNameLikeAndLimit("英国", 0, 5);
        for (Item item : it) {
            System.out.println(item);

        }
    }


    @Test
    public void save(){
        Item item = new Item();

        item.setName("莫吉托");
        item.setPrice(new BigDecimal(11111));
        item.setDescription("真迪好");
        item.setProductionDate(new Date());
        item.setPic("xxxxxxxx");

        Integer save = itemMapper.save(item);
        System.out.println(save);
    }
}