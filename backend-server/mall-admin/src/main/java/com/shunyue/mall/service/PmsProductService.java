package com.shunyue.mall.service;

import com.shunyue.mall.dto.PmsProductParam;
import com.shunyue.mall.dto.PmsProductQueryParam;
import com.shunyue.mall.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductService {

    /**
     * 创建商品
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int create(PmsProductParam productParam);

    /**
     * 查询商品列表
     */
    List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize,Integer pageNum);

    /**
     * 更新商品
     */
    @Transactional
    int update(Long id, PmsProductParam productParam);

    /**
     * 批量修改商品推荐状态
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus);
}
