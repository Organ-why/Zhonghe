package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/5
 * desc:退货列表
 */

public class RefundsItemInfo extends BaseBean {

    /**
     * return_id : 19
     * order_sn : 150452061645293
     * user_id : 21
     * explain :
     * total_price : 0
     * order_id : 528
     * goods_list : [{"rec_id":"185","order_id":"528","goods_id":"185","goods_name":"yyyy","goods_sn":"150452061645293","product_id":"0","goods_number":"1","market_price":"0.00","goods_price":"0.00","discount_fee":"0.00","goods_attr":"1","send_number":"0","is_real":"0","extension_code":"","parent_id":"0","is_gift":"0","goods_attr_id":"","good_id":"185","goods_thumb":"http://test.shiangou.com.cn/images/201708/thumb_img/185_thumb_G_1504172299162.jpg","goods_count":"http://test.shiangou.com.cn/1"}]
     */

    private String return_id;
    private String order_sn;
    private String user_id;
    private String explain;
    private double total_price;
    private String order_id;
    private int pay_class;
    //    pay_class 付款类型 0为金钱， 1为积分
    private List<GoodsInfo> goods_list;

    public int getPay_class() {
        return pay_class;
    }

    public void setPay_class(int pay_class) {
        this.pay_class = pay_class;
    }

    public String getReturn_id() {
        return return_id;
    }

    public void setReturn_id(String return_id) {
        this.return_id = return_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public List<GoodsInfo> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsInfo> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * rec_id : 185
         * order_id : 528
         * goods_id : 185
         * goods_name : yyyy
         * goods_sn : 150452061645293
         * product_id : 0
         * goods_number : 1
         * market_price : 0.00
         * goods_price : 0.00
         * discount_fee : 0.00
         * goods_attr : 1
         * send_number : 0
         * is_real : 0
         * extension_code :
         * parent_id : 0
         * is_gift : 0
         * goods_attr_id :
         * good_id : 185
         * goods_thumb : http://test.shiangou.com.cn/images/201708/thumb_img/185_thumb_G_1504172299162.jpg
         * goods_count : http://test.shiangou.com.cn/1
         */

        private String rec_id;
        private String order_id;
        private String goods_id;
        private String goods_name;
        private String goods_sn;
        private String product_id;
        private String goods_number;
        private String market_price;
        private double goods_price;
        private String discount_fee;
        private String goods_attr;
        private String send_number;
        private String is_real;
        private String extension_code;
        private String parent_id;
        private String is_gift;
        private String goods_attr_id;
        private String good_id;
        private String goods_thumb;
        private String goods_count;

        public String getRec_id() {
            return rec_id;
        }

        public void setRec_id(String rec_id) {
            this.rec_id = rec_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_sn() {
            return goods_sn;
        }

        public void setGoods_sn(String goods_sn) {
            this.goods_sn = goods_sn;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(String goods_number) {
            this.goods_number = goods_number;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public double getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
        }

        public String getDiscount_fee() {
            return discount_fee;
        }

        public void setDiscount_fee(String discount_fee) {
            this.discount_fee = discount_fee;
        }

        public String getGoods_attr() {
            return goods_attr;
        }

        public void setGoods_attr(String goods_attr) {
            this.goods_attr = goods_attr;
        }

        public String getSend_number() {
            return send_number;
        }

        public void setSend_number(String send_number) {
            this.send_number = send_number;
        }

        public String getIs_real() {
            return is_real;
        }

        public void setIs_real(String is_real) {
            this.is_real = is_real;
        }

        public String getExtension_code() {
            return extension_code;
        }

        public void setExtension_code(String extension_code) {
            this.extension_code = extension_code;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getIs_gift() {
            return is_gift;
        }

        public void setIs_gift(String is_gift) {
            this.is_gift = is_gift;
        }

        public String getGoods_attr_id() {
            return goods_attr_id;
        }

        public void setGoods_attr_id(String goods_attr_id) {
            this.goods_attr_id = goods_attr_id;
        }

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public String getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(String goods_count) {
            this.goods_count = goods_count;
        }
    }
}
