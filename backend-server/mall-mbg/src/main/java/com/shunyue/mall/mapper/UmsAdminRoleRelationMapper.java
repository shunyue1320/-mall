package com.shunyue.mall.mapper;

import com.shunyue.mall.model.UmsAdminRoleRelation;
import com.shunyue.mall.model.UmsAdminRoleRelationExample;

import java.util.List;

public interface UmsAdminRoleRelationMapper {
    int deleteByExample(UmsAdminRoleRelationExample example);

    List<UmsAdminRoleRelation> selectByExample(UmsAdminRoleRelationExample example);
}
