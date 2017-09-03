package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/3
 * desc:订单列表item
 */

public class OrderInfo extends BaseBean {
    String order_id, order_sn, pay_status;
    double price;
    int count;
    List<GoodsInfo> goods_list;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public List<GoodsInfo> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsInfo> goods_list) {
        this.goods_list = goods_list;
    }
}
