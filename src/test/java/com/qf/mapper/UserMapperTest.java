package com.qf.mapper;

import com.qf.AcTest;
import com.qf.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UserMapperTest extends AcTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findCountByUsername() {
        /*Integer count = userMapper.findCountByUsername("admin");

        System.err.println(count);*/
        Integer admin = userMapper.findCountByUsername("admin");
        System.out.println(admin);
    }


    @Test
    @Transactional
    public void save() {
        User user = new User();
        user.setUsername("xxxx");
        user.setPassword(".....");
        user.setPhone("11111111111");

        Integer save = userMapper.save(user);

        System.out.println(save);
    }

    @Test
    public void findByUsername() {

        User u = userMapper.findByUsername("admin");
        System.out.println(u);
    }
}