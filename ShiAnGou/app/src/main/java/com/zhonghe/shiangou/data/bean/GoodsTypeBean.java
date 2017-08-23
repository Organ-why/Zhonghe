package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/8/23
 * desc: SKU
 */

public class GoodsTypeBean extends BaseBean {

    /**
     * goods_attr_id : 2
     * goods_id : 115
     * attr_id : 2
     * attr_value : 25斤行不行
     * attr_price :
     */

    private String goods_attr_id;
    private String goods_id;
    private String attr_id;
    private String attr_value;
    private String attr_price;

    public String getGoods_attr_id() {
        return goods_attr_id;
    }

    public void setGoods_attr_id(String goods_attr_id) {
        this.goods_attr_id = goods_attr_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getAttr_value() {
        return attr_value;
    }

    public void setAttr_value(String attr_value) {
        this.attr_value = attr_value;
    }

    public String getAttr_price() {
        return attr_price;
    }

    public void setAttr_price(String attr_price) {
        this.attr_price = attr_price;
    }
}

