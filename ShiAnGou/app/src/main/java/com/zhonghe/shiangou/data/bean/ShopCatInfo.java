package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/9/20
 * desc: 商家类型
 */

public class ShopCatInfo extends BaseBean {

    /**
     * type_id : 1
     * type_name : 美食
     * type_parent : 0
     * type_menus : 53
     */

    private String type_id;
    private String type_name;
    private String type_parent;
    private String type_menus;
    private String type_img;

    public String getType_img() {
        return type_img;
    }

    public void setType_img(String type_img) {
        this.type_img = type_img;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_parent() {
        return type_parent;
    }

    public void setType_parent(String type_parent) {
        this.type_parent = type_parent;
    }

    public String getType_menus() {
        return type_menus;
    }

    public void setType_menus(String type_menus) {
        this.type_menus = type_menus;
    }
}
