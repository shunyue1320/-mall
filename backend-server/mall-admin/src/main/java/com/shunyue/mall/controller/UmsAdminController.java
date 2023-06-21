package com.shunyue.mall.controller;

import com.shunyue.mall.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Api(tags = "UmsAdminController")
@Tag(name = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {

//    @ApiOperation(value = "用户注册")
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    @RequestBody
//    public CommonResult<UmsAdmin> reg
}
