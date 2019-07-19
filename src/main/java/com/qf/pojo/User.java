package com.qf.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 *
 * @program: three
 * @author: 羊角
 * @create: 2019-07-15 19:58
 **/

@Data
public class User {


    private Long id;

    @NotBlank(message = "用户名为必填项,岂能为空")
    private String username;

    @NotBlank(message = "密码为必填项,岂能为空")
    private String password;
    @NotBlank(message = "手机号为必填项,岂能为空")
    private String phone;

    private Date created;

}
