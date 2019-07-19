package com.qf.service.impl;

import com.qf.mapper.UserMapper;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-15 22:12
 **/

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer checkUsername(String username) {
        //加个健壮性代码
        if (!com.alibaba.druid.util.StringUtils.isEmpty(username)){
            username=username.trim();
        }


        return userMapper.findCountByUsername(username);
    }

    //添加用户
    @Override
    public Integer register(User user) {
    //对密码进行加密
       String newPwd = new Md5Hash(user.getPassword(),null,1024).toString();

        System.out.println(newPwd);
        user.setPassword(newPwd);

        return userMapper.save(user);
    }

    @Override
    public User login(String username, String password) {
        //1.根据用户名查询用户信息
        User user = userMapper.findByUsername(username);
        //2.查询查询的是否为null
        if (user!=null) {
            //3.如果不为null,判断密码
            String password1 = new Md5Hash(password, null, 1024).toString();
//        4.如果密码正确,则返回查询到的user
            if (user.getPassword().equals(password1)){
                return user;
            }
        }
        //5.其他情况统一返回null
        return null;

    }


}
