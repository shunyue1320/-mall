package com.shunyue.mall.mapper;

import com.shunyue.mall.model.PmsProduct;
import com.shunyue.mall.model.PmsProductExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductMapper {

    int insertSelective(PmsProduct record);
    List<PmsProduct> selectByExample(PmsProductExample example);


    /**
     *  更新商品信息
     */
    int updateByPrimaryKeySelective(PmsProduct record);

    int updateByExampleSelective(@Param("record") PmsProduct record, @Param("example") PmsProductExample example);
}
