package com.qf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-15 22:02
 **/

@Data
@NoArgsConstructor//无参构造
@AllArgsConstructor//有参构造
public class ResultVo {

    private Integer code;

    private String msg;

    private Object data;
}
