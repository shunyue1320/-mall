package com.shunyue.mall.mapper;

import com.shunyue.mall.model.UmsAdmin;
import com.shunyue.mall.model.UmsAdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsAdminMapper {
    int insert(UmsAdmin record);
    List<UmsAdmin> selectByExample(UmsAdminExample example);

    UmsAdmin selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsAdmin record);
}
