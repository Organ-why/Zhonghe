package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/8/29
 * desc: 确认商品
 */

public class ConfirmRspInfo extends BaseBean {
    AddressInfo default_add;
    List<CartGoods> com_goods;
    int tatal;
    int com_count;

    public ConfirmRspInfo() {
    }

    public AddressInfo getDefault_add() {
        return default_add;
    }

    public void setDefault_add(AddressInfo default_add) {
        this.default_add = default_add;
    }

    public List<CartGoods> getCom_goods() {
        return com_goods;
    }

    public void setCom_goods(List<CartGoods> com_goods) {
        this.com_goods = com_goods;
    }

    public int getTatal() {
        return tatal;
    }

    public void setTatal(int tatal) {
        this.tatal = tatal;
    }

    public int getCom_count() {
        return com_count;
    }

    public void setCom_count(int com_count) {
        this.com_count = com_count;
    }
}
