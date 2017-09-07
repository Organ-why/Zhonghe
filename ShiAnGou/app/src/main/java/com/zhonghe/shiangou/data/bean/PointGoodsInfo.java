package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/5
 * desc:积分商品详情
 */

public class PointGoodsInfo extends BaseBean {

    private String banner;
    private List<PointItemListInfo> list;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<PointItemListInfo> getData() {
        return list;
    }

    public void setData(List<PointItemListInfo> list) {
        this.list = list;
    }


}
