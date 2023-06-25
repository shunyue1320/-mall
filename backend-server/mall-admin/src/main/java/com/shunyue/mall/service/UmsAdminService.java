package com.shunyue.mall.service;

import com.shunyue.mall.dto.UmsAdminParam;
import com.shunyue.mall.model.UmsAdmin;

/**
 * 后台用户管理Service
 */
public interface UmsAdminService {
    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);
}
