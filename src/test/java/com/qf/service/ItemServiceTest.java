package com.qf.service;

import com.qf.AcTest;
import com.qf.pojo.Item;
import com.qf.utils.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ItemServiceTest extends AcTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void findItemAndLimit() {

        PageInfo<Item> pa = itemService.findItemAndLimit(null, 1, 4);
        for (Item item : pa.getList()) {
            System.out.println(item);
        }

    }
}