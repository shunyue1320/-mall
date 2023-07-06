package com.shunyue.mall.model;


import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 商品会员价格设置
 */
public class PmsMemberPrice {
    private Long id;

    private Long productId;

    private Long memberLevelId;

    @ApiModelProperty(value = "会员价格")
    private BigDecimal memberPrice;

    @ApiModelProperty(value = "会员名：钻石会员 | 白金会员 | 黄金会员")
    private String memberLevelName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(Long memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getMemberLevelName() {
        return memberLevelName;
    }

    public void setMemberLevelName(String memberLevelName) {
        this.memberLevelName = memberLevelName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", memberLevelId=").append(memberLevelId);
        sb.append(", memberPrice=").append(memberPrice);
        sb.append(", memberLevelName=").append(memberLevelName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
