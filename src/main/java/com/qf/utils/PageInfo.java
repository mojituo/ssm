package com.qf.utils;

import lombok.Data;

import java.util.List;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-16 11:34
 **/

@Data
public class PageInfo<T> {

    //商品信息和分页信息

    //当前页
    private Integer page;
    //每页显示条数
    private Integer size;
    //总条数
    private Long total;
    //总页数
    private Integer pages;
    //计算其实索引
    private Integer offset;   //(page - 1) * size
    //商品信息
    private List<T> list;


    public PageInfo(Integer page, Integer size, Long total,  List<T> list) {
        this.page = page <= 0 ? 1 : page ;
        this.size = size <= 0 ? 5 : size ;
        this.total = total ;
        this.list = list;

        this.pages = (int) (this.total%this.size==0?this.total/this.size:this.total/this.size+1);

        this.offset = (page-1)*size;
    }
}
