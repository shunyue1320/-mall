package com.shunyue.mall.controller;

import cn.hutool.core.collection.CollUtil;
import com.shunyue.mall.common.api.CommonPage;
import com.shunyue.mall.common.api.CommonResult;
import com.shunyue.mall.dto.UmsAdminLoginParam;
import com.shunyue.mall.model.UmsAdmin;
import com.shunyue.mall.dto.UmsAdminParam;
import com.shunyue.mall.model.UmsRole;
import com.shunyue.mall.service.UmsAdminService;
import com.shunyue.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Api(tags = "UmsAdminController")
@Tag(name = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    @ResponseBody
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/info")
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal) {
        // principal 是当前用户 token 内的身份信息
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        // 通过 username 去数据库获取当前用户信息
        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        // 获取该用户的前端页面路由表
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        List<UmsRole> roleList = adminService.getRoleList(umsAdmin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return CommonResult.success(data);
    }


    @ApiOperation(value = "刷新token")
    @GetMapping("/refreshToken")
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        return CommonResult.success("成功");
    }


    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonResult<CommonPage<UmsAdmin>> list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSzie,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum
    ) {
        List<UmsAdmin> adminList = adminService.list(keyword, pageSzie, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping(value = "/role/{adminId}")
    @ResponseBody
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("修改帐号状态")
    @PostMapping(value = "/updateStatus/{adminId}")
    @ResponseBody
    public  CommonResult updateStatus(@PathVariable Long adminId, @RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        int count = adminService.update(adminId, umsAdmin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
