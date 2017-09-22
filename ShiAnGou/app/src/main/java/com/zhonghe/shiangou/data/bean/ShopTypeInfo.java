package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/9/22
 * desc: 商户分类
 */

public class ShopTypeInfo extends BaseBean {

    /**
     * cat_id : 2
     * cat_name : 火锅
     */

    private String cat_id;
    private String cat_name;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
}
