package com.zhonghe.shiangou.data.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a on 2017/8/11.
 */

public class HomeData extends BaseBean {
    /**
     * state : 1
     * msg : 请求成功
     * datas : {}
     */
    private DatasBean datas;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }


    public static class DatasBean {
        @SerializedName("category")
        private List<HomeCategoryInfo> categoryX;
        @SerializedName("banner")
        private List<BaseBannerInfo> bannerX;

        public List<HomeCategoryInfo> getCategoryX() {
            return categoryX;
        }

        public void setCategoryX(List<HomeCategoryInfo> categoryX) {
            this.categoryX = categoryX;
        }

        public List<BaseBannerInfo> getBannerX() {
            return bannerX;
        }

        public void setBannerX(List<BaseBannerInfo> bannerX) {
            this.bannerX = bannerX;
        }


    }
}
