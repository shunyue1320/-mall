package com.shunyue.mall.mapper;

import com.shunyue.mall.model.PmsMemberPriceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsMemberPriceMapper {
    int deleteByExample(PmsMemberPriceExample example);
}