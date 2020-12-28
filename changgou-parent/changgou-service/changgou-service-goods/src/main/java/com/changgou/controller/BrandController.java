package com.changgou.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author DJ
 * @Date 2020-12-26 22:48:42
 */
@RestController
@CrossOrigin
@RequestMapping("brand")
public class BrandController {
    @Resource
    private BrandService brandService;

    @GetMapping("list")
    public Result<List<Brand>> findAll() {
        List<Brand> brandList = brandService.findAll();
        return new Result<>(true, StatusCode.OK, "查询品牌集合成功", brandList);
    }

    @PostMapping("search")
    public Result<List<Brand>> findList(@RequestBody Brand brand) {
        List<Brand> brandList = brandService.findList(brand);
        return new Result<>(true, StatusCode.OK, "查询品牌集合成功", brandList);
    }

    @GetMapping("find/{pageNum}/{pageSize}")
    public Result<PageInfo<Brand>> findPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        PageInfo<Brand> brandPageInfo = brandService.findPage(pageNum, pageSize);
        return new Result<>(true, StatusCode.OK, "查询品牌分页集合成功", brandPageInfo);
    }

    @PostMapping("find/{pageNum}/{pageSize}")
    public Result<PageInfo<Brand>> findPage(@RequestBody Brand brand, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        PageInfo<Brand> brandPageInfo = brandService.findPage(brand, pageNum, pageSize);
        int i = 10/0;
        return new Result<>(true, StatusCode.OK, "根据条件查询品牌分页集合成功", brandPageInfo);
    }

}
