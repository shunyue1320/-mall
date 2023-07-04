package com.shunyue.mall.service;


import com.shunyue.mall.model.UmsMenu;
import com.shunyue.mall.model.UmsRole;

import java.util.List;

/**
 * 后台角色管理Service
 */
public interface UmsRoleService {
    /**
     * 添加角色
     */
    int create(UmsRole role);

    /**
     * 根据管理员ID获取对应菜单
     */
    List<UmsMenu> getMenuList(Long adminId);

    /**
     * 获取所有角色列表
     */
    List<UmsRole> list();

    /**
     * 修改角色信息
     */
    int update(Long id, UmsRole role);
}
