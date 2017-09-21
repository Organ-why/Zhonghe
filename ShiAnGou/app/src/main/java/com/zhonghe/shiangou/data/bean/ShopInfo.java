package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/9/20
 * desc:商家信息
 */

public class ShopInfo extends BaseBean {

    /**
     * merchant_id : 4
     * merchant_name : 永旺
     * grade : 4
     * address : 总部基地
     * merchant_thumb : http://test.shiangou.com.cn/merchant/Attachment/Merchant/20170919/thumb_59c0f5c005544.jpg
     * between : 0.906
     */

    private String merchant_id;
    private String merchant_name;
    private float grade;
    private String address;
    private String merchant_thumb;
    private double between;

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

    public double getBetween() {
        return between;
    }

    public void setBetween(double between) {
        this.between = between;
    }
}
