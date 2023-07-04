package com.shunyue.mall.mapper;

import com.shunyue.mall.model.UmsResource;
import com.shunyue.mall.model.UmsResourceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsResourceMapper {

    int insert(UmsResource record);

    List<UmsResource> selectByExample(UmsResourceExample example);

    UmsResource selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsResource record);
}