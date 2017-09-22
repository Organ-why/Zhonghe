package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/20
 * desc:
 */

public class UnlineShopDetailInfo extends BaseBean {

    /**
     * merchant_id : 4
     * merchant_name : 永旺
     * grade : 4
     * phone : 123123
     * intro : 北京市丰台区永旺
     * menus : 49
     * merchant_type : 2
     * province_id : 110000
     * city_id : 110100
     * area_id : 110101
     * address : 总部基地
     * merchant_thumb : /merchant/Attachment/Merchant/20170919/thumb_59c0f5c005544.jpg
     * average : 290.00
     * province_name : 北京市
     * city_name : 市辖区
     * area_name : 东城区
     */

    private String merchant_id;
    private String merchant_name;
    private float grade;
    private String phone;
    private String intro;
    private String menus;
    private String merchant_type;
    private String province_id;
    private String city_id;
    private String area_id;
    private String address;
    private String merchant_thumb;
    private double average;
    private String province_name;
    private String city_name;
    private String area_name;
    private List<ShopRemarkInfo> commentlist;
    private int comment_num;
    private String photo_num;
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhoto_num() {
        return photo_num;
    }

    public void setPhoto_num(String photo_num) {
        this.photo_num = photo_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public List<ShopRemarkInfo> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<ShopRemarkInfo> commentlist) {
        this.commentlist = commentlist;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public String getMerchant_type() {
        return merchant_type;
    }

    public void setMerchant_type(String merchant_type) {
        this.merchant_type = merchant_type;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMerchant_thumb() {
        return merchant_thumb;
    }

    public void setMerchant_thumb(String merchant_thumb) {
        this.merchant_thumb = merchant_thumb;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
