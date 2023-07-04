package com.shunyue.mall.controller;


import com.shunyue.mall.common.api.CommonPage;
import com.shunyue.mall.common.api.CommonResult;
import com.shunyue.mall.model.UmsResource;
import com.shunyue.mall.security.component.DynamicSecurityMetadataSource;
import com.shunyue.mall.service.UmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台资源管理Controller
 */

@Controller
@Api(tags = "UmsResourceController")
@Tag(name = "UmsResourceController", description = "后台资源管理")
@RequestMapping("/resource")
public class UmsResourceController {

    @Autowired
    private UmsResourceService resourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("添加后台资源")
    @PostMapping("/create")
    @ResponseBody
    public CommonResult create(@RequestBody UmsResource umsResource) {
        int count = resourceService.create(umsResource);
        // 删除缓存
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改后台资源")
    @PostMapping("/update/{id}")
    @ResponseBody
    public CommonResult update(
            @PathVariable Long id,
            @RequestBody UmsResource umsResource
    ) {
        int count = resourceService.update(id, umsResource);
        // 删除缓存
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation("根据ID获取资源详情")
    @GetMapping("/{id}")
    @ResponseBody
    public CommonResult<UmsResource> getItem(@PathVariable Long id) {
        UmsResource umsResource = resourceService.getItem(id);
        return CommonResult.success(umsResource);
    }


    @ApiOperation("根据ID删除后台资源")
    @PostMapping("/delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = resourceService.delete(id);
        // 删除缓存
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据ID删除后台资源")
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<UmsResource>> list(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "keyword", required = false) String namekeyword,
            @RequestParam(value = "urlKeyword", required = false) String urlKeyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum
    ) {
        List<UmsResource> resourceList = resourceService.list(categoryId, namekeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(resourceList));
    }

    @ApiOperation("根据ID删除后台资源")
    @GetMapping("/listAll")
    @ResponseBody
    public CommonResult<CommonPage<UmsResource>> list() {
        List<UmsResource> resourceList = resourceService.listAll();
        return CommonResult.success(CommonPage.restPage(resourceList));
    }
}
