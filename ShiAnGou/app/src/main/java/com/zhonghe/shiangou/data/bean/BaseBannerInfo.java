package com.zhonghe.shiangou.data.bean;

/**
 * Date: 2017/8/4.
 * Author: whyang
 */
public class BaseBannerInfo extends BaseBean {
    String imgUrl;
    int Id;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
