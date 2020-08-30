package com.kakarot.pojo.vo;

import com.kakarot.pojo.bo.ShopcartBO;

import java.util.List;

public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;

    private List<ShopcartBO> toBeRemovedShopCartList;

    public List<ShopcartBO> getToBeRemovedShopCartList() {
        return toBeRemovedShopCartList;
    }

    public void setToBeRemovedShopCartList(List<ShopcartBO> toBeRemovedShopCartList) {
        this.toBeRemovedShopCartList = toBeRemovedShopCartList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}