package com.shunyue.mall.service;

import com.shunyue.mall.dto.PmsProductQueryParam;
import com.shunyue.mall.model.PmsProduct;

import java.util.List;

public interface PmsProductService {
    List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize,Integer pageNum);
}
