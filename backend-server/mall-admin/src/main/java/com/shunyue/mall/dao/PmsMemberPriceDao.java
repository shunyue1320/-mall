package com.shunyue.mall.dao;

import com.shunyue.mall.model.PmsMemberPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员价格管理自定义Dao
 * Created by macro on 2018/4/26.
 */
public interface PmsMemberPriceDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsMemberPrice> memberPriceList);

    /**
     * 会员阶梯价格自定义Dao
     * Created by macro on 2018/4/26.
     */
    interface PmsProductLadderDao {
        /**
         * 批量创建
         */
        int insertList(@Param("list") List<PmsProductLadder> productLadderList);
    }
}
