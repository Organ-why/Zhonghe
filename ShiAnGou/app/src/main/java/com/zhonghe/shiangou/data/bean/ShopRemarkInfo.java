package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/20
 * desc:
 */

public class ShopRemarkInfo extends BaseBean {

    /**
     * comment_id : 1
     * user_id : 21
     * merchant_id : 4
     * grade : 3
     * details : 店家不错，环境也很好，总体不错
     * datetime : 1502019139
     * menus : 41
     * state : 1
     * nick_name : VGHsan
     * user_pic : http://test.shiangou.com.cn/app/private/Uploads/1504763946602194.jpg
     * photolist : [{"photo_id":"1","path":"images/201708/goods_img/156_G_1504057780292.jpg","datetime":"1502019139","comment_id":"1"}]
     */

    private String comment_id;
    private String user_id;
    private String merchant_id;
    private float grade;
    private String details;
    private long datetime;
    private String menus;
    private String state;
    private String nick_name;
    private String user_pic;
    private List<PhotolistBean> photolist;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public List<PhotolistBean> getPhotolist() {
        return photolist;
    }

    public void setPhotolist(List<PhotolistBean> photolist) {
        this.photolist = photolist;
    }

    public static class PhotolistBean extends BaseBean{
        /**
         * photo_id : 1
         * path : images/201708/goods_img/156_G_1504057780292.jpg
         * datetime : 1502019139
         * comment_id : 1
         */

        private String photo_id;
        private String path;
        private String datetime;
        private String comment_id;

        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }
    }
}
