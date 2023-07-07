package com.shunyue.mall.controller;


import com.shunyue.mall.common.api.CommonPage;
import com.shunyue.mall.common.api.CommonResult;
import com.shunyue.mall.dto.PmsProductParam;
import com.shunyue.mall.dto.PmsProductQueryParam;
import com.shunyue.mall.model.PmsProduct;
import com.shunyue.mall.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(tags = "PmsProductController")
@Tag(name = "PmsProductController", description = "商品管理")
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    PmsProductService productService;

    @ApiOperation("创建商品")
    @PostMapping("/create")
    @ResponseBody
    public CommonResult create(@RequestBody PmsProductParam productParam) {
        int count = productService.create(productParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("创建商品")
    @PostMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<PmsProduct>> list(
            PmsProductQueryParam productQueryParam,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
    ) {
         List<PmsProduct> productList = productService.list(productQueryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(productList));
    }
}
