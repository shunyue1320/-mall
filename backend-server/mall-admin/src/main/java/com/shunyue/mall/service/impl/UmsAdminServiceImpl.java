package com.shunyue.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.shunyue.mall.bo.AdminUserDetails;
import com.shunyue.mall.common.exception.Asserts;
import com.shunyue.mall.dao.UmsAdminRoleRelationDao;
import com.shunyue.mall.dto.UmsAdminParam;
import com.shunyue.mall.mapper.UmsAdminMapper;
import com.shunyue.mall.model.UmsAdmin;
import com.shunyue.mall.model.UmsAdminExample;
import com.shunyue.mall.model.UmsResource;
import com.shunyue.mall.security.util.JwtTokenUtil;
import com.shunyue.mall.service.UmsAdminCacheService;
import com.shunyue.mall.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
         // 存在该用户返回 null
        if (umsAdminList.size() > 0) {
            return null;
        }
        umsAdmin.setPassword(umsAdmin.getPassword());
        //将密码进行加密操作
//        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
//        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    @Override
    public  UmsAdmin getItem(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            // 通过用户名 username 获取用户信息
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
            // token
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 生成 token
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
//            insertLoginLog(username);
        } catch (AuthenticationException e) {
            //
        }
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        // 存在用户则通过 id 去 ums_role ums_role_resource_relation ums_resource 关联表里读取该用户可访问的的后台路由表
        if (admin != null) {
            List<UmsResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("该用户不存在");
    }

    /**
     * 通过 用户id 获取该用户授权的后台路由列表
     * @param adminId
     * @return
     */
    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        //先从缓存中获取数据
        List<UmsResource> resourceList = getCacheService().getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            return  resourceList;
        }
        //缓存中没有从数据库中获取
        resourceList = adminRoleRelationDao.getResourceList(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            //将数据库中的数据存入缓存中
            getCacheService().setResourceList(adminId, resourceList);
        }
        return resourceList;
    }

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        //先从缓存中获取数据
        UmsAdmin admin = getCacheService().getAdmin(username);
        if (admin != null) return admin;
        //缓存中没有从数据库中获取
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            //将数据库中的数据存入缓存中
            getCacheService().setAdmin(admin);
            return admin;
        }
        return null;
    }

    /**
     * 注入 UmsAdminCacheService 缓存服务
     * @return
     */
    @Override
    public UmsAdminCacheService getCacheService() {
        return SpringUtil.getBean(UmsAdminCacheService.class);
    }
}
