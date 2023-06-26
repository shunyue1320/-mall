package com.shunyue.mall.service;

import com.shunyue.mall.model.UmsAdmin;

/**
 * 后台用户缓存操作Service
 */
public interface UmsAdminCacheService {
    /**
     * 删除后台用户缓存
     */
    void delAdmin(Long adminId);

    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(UmsAdmin admin);

    /**
     * 获取缓存后台用户信息
     */
    UmsAdmin getAdmin(String username);
}
