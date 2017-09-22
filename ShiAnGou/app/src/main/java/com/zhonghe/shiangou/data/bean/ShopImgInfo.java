package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/9/22
 * desc:商户图片
 */

public class ShopImgInfo extends BaseBean {

    /**
     * photo_id : 9
     * photo_path : /merchant/Attachment/Merchant/20170918/thumb_59bf58a1167de.jpg
     * merchant_id : 2
     * photo_album : 1
     */

    private String photo_id;
    private String photo_path;
    private String merchant_id;
    private String photo_album;

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getPhoto_album() {
        return photo_album;
    }

    public void setPhoto_album(String photo_album) {
        this.photo_album = photo_album;
    }
}
