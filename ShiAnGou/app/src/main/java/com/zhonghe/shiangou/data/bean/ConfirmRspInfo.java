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
    double total;
    int com_count;
    String order_sn;

    public ConfirmRspInfo() {

    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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


    public int getCom_count() {
        return com_count;
    }

    public void setCom_count(int com_count) {
        this.com_count = com_count;
    }
}
