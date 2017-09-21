package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/20
 * desc: 线下商城首页
 */

public class UnlineHomeInfo extends BaseBean {
    List<ShopInfo> list;
    List<ShopCatInfo> cat;
    String banner;

    public List<ShopInfo> getList() {
        return list;
    }

    public void setList(List<ShopInfo> list) {
        this.list = list;
    }

    public List<ShopCatInfo> getCat() {
        return cat;
    }

    public void setCat(List<ShopCatInfo> cat) {
        this.cat = cat;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
