package com.shunyue.mall.mapper;

import com.shunyue.mall.model.PmsSkuStock;
import com.shunyue.mall.model.PmsSkuStockExample;

import java.util.List;

public interface PmsSkuStockMapper {
    int deleteByExample(PmsSkuStockExample example);

    List<PmsSkuStock> selectByExample(PmsSkuStockExample example);

    int updateByPrimaryKeySelective(PmsSkuStock record);
}
