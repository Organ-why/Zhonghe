package com.zhonghe.shiangou.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/3
 * desc:订单列表item
 */

public class OrderInfo extends BaseBean {
    //    运单号：number
//    快递公司代码：type
//    快递公司名 ： express
    String order_sn, pay_status, number, type, express;
    double price;
    int count;
//    pay_class 付款类型 0为金钱， 1为积分
    private int pay_class;
    List<GoodsInfo> goods_list;

    public int getPay_class() {
        return pay_class;
    }

    public void setPay_class(int pay_class) {
        this.pay_class = pay_class;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    /**
     * goods_list : {"goods_name":"欧华 肉馅  肉片","goods_thumb":"images/201708/thumb_img/128_thumb_G_1503997923403.png","goods_desc":"<p><img src=\"/images/upload/Image/%E7%8C%AA%E8%82%89%E8%AF%A6%E6%83%85.png\" width=\"790\" height=\"7140\" alt=\"\" />&nbsp;<\/p>","goods_img":"images/201708/goods_img/128_G_1503640974997.jpg","goods_id":"128","goods_attr_id":null,"shop_price":"77.00","goods_count":"1"}
     * price : 0
     */


    private GoodsListBean goods_listX;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public List<GoodsInfo> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsInfo> goods_list) {
        this.goods_list = goods_list;
    }

    public GoodsListBean getGoods_listX() {
        return goods_listX;
    }

    public void setGoods_listX(GoodsListBean goods_listX) {
        this.goods_listX = goods_listX;
    }

    public static class GoodsListBean {
        /**
         * goods_name : 欧华 肉馅  肉片
         * goods_thumb : images/201708/thumb_img/128_thumb_G_1503997923403.png
         * goods_desc : <p><img src="/images/upload/Image/%E7%8C%AA%E8%82%89%E8%AF%A6%E6%83%85.png" width="790" height="7140" alt="" />&nbsp;</p>
         * goods_img : images/201708/goods_img/128_G_1503640974997.jpg
         * goods_id : 128
         * goods_attr_id : null
         * shop_price : 77.00
         * goods_count : 1
         */

        private String goods_name;
        private String goods_thumb;
        private String goods_desc;
        private String goods_img;
        private String goods_id;
        private Object goods_attr_id;
        private String shop_price;
        private String goods_count;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public String getGoods_desc() {
            return goods_desc;
        }

        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public Object getGoods_attr_id() {
            return goods_attr_id;
        }

        public void setGoods_attr_id(Object goods_attr_id) {
            this.goods_attr_id = goods_attr_id;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(String goods_count) {
            this.goods_count = goods_count;
        }
    }
}
