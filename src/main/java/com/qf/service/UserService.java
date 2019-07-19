package com.qf.service;

import com.qf.pojo.User;

public interface UserService {


    Integer checkUsername(String username);

    Integer register(User user);


    User login(String username, String password);
}
