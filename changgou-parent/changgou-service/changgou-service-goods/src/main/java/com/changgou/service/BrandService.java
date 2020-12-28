package com.changgou.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author DJ
 * @Date 2020-12-26 22:51:21
 */
public interface BrandService {
    List<Brand> findAll();

    List<Brand> findList(Brand brand);

    PageInfo<Brand> findPage(Integer pageNum, Integer pageSize);

    PageInfo<Brand> findPage(Brand brand,Integer pageNum,Integer pageSize);
}
