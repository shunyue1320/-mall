package com.shunyue.mall.service.impl;


import com.shunyue.mall.dao.UmsRoleDao;
import com.shunyue.mall.mapper.UmsRoleMapper;
import com.shunyue.mall.model.UmsMenu;
import com.shunyue.mall.model.UmsRole;
import com.shunyue.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 后台角色管理Service实现类
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsRoleDao roleDao;

    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        // 将角色插入 ums_role 数据库里面
        return roleMapper.insert(role);
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        // 去数据库里面读取
        return roleDao.getMenuList(adminId);
    }
}