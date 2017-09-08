package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/5
 * desc:积分商品详情
 */

public class PointGoodsInfo extends BaseBean {

    private String banner;
    private List<PointItemInfo> list;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<PointItemInfo> getData() {
        return list;
    }

    public void setData(List<PointItemInfo> list) {
        this.list = list;
    }


}
