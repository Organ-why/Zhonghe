package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/9/5
 * desc:
 */

public class PointDetailInfo extends BaseBean {
    AddressInfo address;
    PointItemInfo goods;

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }

    public PointItemInfo getGoods() {
        return goods;
    }

    public void setGoods(PointItemInfo goods) {
        this.goods = goods;
    }
}
