package com.shunyue.mall.mapper;

import com.shunyue.mall.model.PmsProduct;
import com.shunyue.mall.model.PmsProductExample;

import java.util.List;

public interface PmsProductMapper {

    int insertSelective(PmsProduct record);
    List<PmsProduct> selectByExample(PmsProductExample example);

}
