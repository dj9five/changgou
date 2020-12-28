package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author DJ
 * @Date 2020-12-26 22:50:57
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public List<Brand> findList(Brand brand) {
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    private Example createExample(Brand brand) {
        //下面两行固定写法 Class不同而已
        Example example = new Example(Brand.class);

        Example.Criteria criteria = example.createCriteria();
        if (brand != null) {
            if (StringUtils.isNotBlank(brand.getName())) {
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            if (StringUtils.isNotBlank(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
        }
        return example;
    }

    @Override
    public PageInfo<Brand> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Brand> brandList = brandMapper.selectAll();
        return new PageInfo<>(brandList);
    }

    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = createExample(brand);
        List<Brand> brandList = brandMapper.selectByExample(example);
        return new PageInfo<>(brandList);
    }
}
