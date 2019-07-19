package com.qf.mapper;

import com.qf.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    //1.根据用户名查询数据条数
    Integer findCountByUsername(@Param("username") String username);

    //2.添加用户信息
    Integer save(User user);

    //3.登录
    User findByUsername(@Param("username")String username);
}
