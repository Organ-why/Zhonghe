package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/8/28
 * desc:
 */

public class AddressSelectInfo extends BaseBean {
    List<AddressInfo> address;

    public List<AddressInfo> getAddress() {
        return address;
    }

    public void setAddress(List<AddressInfo> address) {
        this.address = address;
    }
}
