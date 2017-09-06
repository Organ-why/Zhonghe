package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/5
 * desc:积分商品详情
 */

public class PointGoodsInfo extends BaseBean {

    private List<String> banner;
    private List<PointItemInfo> data;

    public List<String> getBanner() {
        return banner;
    }

    public void setBanner(List<String> banner) {
        this.banner = banner;
    }

    public List<PointItemInfo> getData() {
        return data;
    }

    public void setData(List<PointItemInfo> data) {
        this.data = data;
    }


}
