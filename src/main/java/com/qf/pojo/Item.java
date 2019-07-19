package com.qf.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-16 11:18
 * 商品表对应的实体类
 **/

@Data
public class Item {

    private Long id;
    @NotBlank(message = "商品名称为必填项,岂能为空")
    private String name;
    @NotNull(message = "商品价格为必填项,岂能为空")
    private BigDecimal price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "商品的生产日期为必填项,岂能为空!")
    private Date productionDate;
    @NotBlank(message = "商品的描述为必填项,岂能为空!")
    private String description;

    private String pic;

    private Date created;

}
