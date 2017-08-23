package com.zhonghe.shiangou.data.bean;

/**
 * Date: 2017/8/4.
 * Author: whyang
 */
public class BaseBannerInfo extends BaseBean {
    String imgUrl;
    int Id;
    /**
     * banner_id : 1
     * banner_images : http://test.shiangou.com.cn/data/afficheimg/banner1@2x.jpg
     * banner_type : 1
     * banner_url : null
     */

    private String banner_id;
    private String banner_images;
    private String banner_type;
    private String banner_url;

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

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner_images() {
        return banner_images;
    }

    public void setBanner_images(String banner_images) {
        this.banner_images = banner_images;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }
}
