package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/8/28
 * desc:
 */

public class AddressSelectInfo extends BaseBean {
    List<AddressArea> address;

    public List<AddressArea> getAddress() {
        return address;
    }

    public void setAddress(List<AddressArea> address) {
        this.address = address;
    }
}
