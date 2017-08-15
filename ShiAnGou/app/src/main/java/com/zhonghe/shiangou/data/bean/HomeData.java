package com.zhonghe.shiangou.data.bean;


import java.util.List;

/**
 * Created by a on 2017/8/11.
 */

public class HomeData extends BaseBean {
    List<HomeCategoryInfo> category;
    List<BaseBannerInfo> banner;

    public List<HomeCategoryInfo> getCategory() {
        return category;
    }

    public void setCategory(List<HomeCategoryInfo> category) {
        this.category = category;
    }

    public List<BaseBannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BaseBannerInfo> banner) {
        this.banner = banner;
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "category=" + category +
                ", banner=" + banner +
                '}';
    }
}
