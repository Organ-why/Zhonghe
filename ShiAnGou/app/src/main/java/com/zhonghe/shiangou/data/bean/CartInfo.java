package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/8/24
 * desc:
 */

public class CartInfo extends BaseBean {
    List<CartGoods> carts;
    String num;

    public List<CartGoods> getCarts() {
        return carts;
    }

    public void setCarts(List<CartGoods> carts) {
        this.carts = carts;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
