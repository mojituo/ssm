package com.qf.service;

import com.qf.AcTest;
import com.qf.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceTest extends AcTest {


    @Autowired
    private UserService userService;

    @Test
    public void checkUsername() {
        Integer co = userService.checkUsername("admin");
        System.out.println(co);
    }

    @Test
    public void register() {


        User user = new User();
        user.setUsername("xxxx");
        user.setPassword("666666");
        user.setPhone("11111111111");
        Integer register = userService.register(user);
        System.out.println(register);

    }

    @Test
    public void login() {

        User user = userService.login("admin", "666666");
        System.out.println(user);

    }
}