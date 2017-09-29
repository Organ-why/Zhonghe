package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * whyang
 * 2017/8/18.
 */

public class GoodsdetailInfo extends BaseBean {


    /**
     * goods : {"goods_id":"115","cat_id":"139","goods_sn":"ECS000115","goods_name":"对对对","goods_name_style":"+","click_count":"1","brand_id":null,"provider_name":"","goods_number":"1","goods_weight":"0.000","market_price":"0.00","virtual_sales":"0","shop_price":"0.00","promote_price":"0.00","promote_start_date":"0","promote_end_date":"0","warn_number":"1","keywords":"","goods_brief":"","goods_desc":"","goods_thumb":"","goods_img":"","original_img":"","is_real":"1","extension_code":"","is_on_sale":"1","is_alone_sale":"1","is_shipping":"0","integral":"0","add_time":"1503300252","sort_order":null,"is_delete":"0","is_best":"0","is_new":"0","is_hot":"0","is_promote":"0","bonus_type_id":"0","last_update":"1503300765","goods_type":"0","seller_note":"","give_integral":"-1","rank_integral":"-1","suppliers_id":"0","is_check":null,"jifen":"0","brand_name":null,"brand_logo":null,"brand_desc":null,"site_url":null,"is_show":null}
     * goods_img : ["http://test.shiangou.com.cn/images/201708/goods_img/115_P_1503300252077.png","http://test.shiangou.com.cn/images/201708/goods_img/115_P_1503300252083.png","http://test.shiangou.com.cn/images/201708/goods_img/115_P_1503300252379.png","http://test.shiangou.com.cn/images/201708/goods_img/115_P_1503300252048.png","http://test.shiangou.com.cn/images/201708/goods_img/115_P_1503300252269.png"]
     * goods_type : [{"goods_attr_id":"2","goods_id":"115","attr_id":"2","attr_value":"25斤行不行","attr_price":""},{"goods_attr_id":"1","goods_id":"115","attr_id":"1","attr_value":"108斤袋装","attr_price":""}]
     * goods_ping : {"comment_id":"7","comment_type":"0","id_value":"0","email":"","user_name":"2","content":"1111111","comment_rank":"55","add_time":"1503242513","ip_address":"","status":"0","parent_id":"0","user_id":"2","img":"./Uploads/1503242513877553.jpg","goods_id":"115"}
     */

    private GoodsBean goods;
    private GoodsPingBean goods_ping;
    private List<String> goods_img;
    private List<GoodsTypeBean> goods_type;
    //1收藏 0 未收藏
    private int collect;

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public GoodsPingBean getGoods_ping() {
        return goods_ping;
    }

    public void setGoods_ping(GoodsPingBean goods_ping) {
        this.goods_ping = goods_ping;
    }

    public List<String> getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(List<String> goods_img) {
        this.goods_img = goods_img;
    }

    public List<GoodsTypeBean> getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(List<GoodsTypeBean> goods_type) {
        this.goods_type = goods_type;
    }

    public static class GoodsBean extends BaseBean {
        /**
         * goods_id : 115
         * cat_id : 139
         * goods_sn : ECS000115
         * goods_name : 对对对
         * goods_name_style : +
         * click_count : 1
         * brand_id : null
         * provider_name :
         * goods_number : 1
         * goods_weight : 0.000
         * market_price : 0.00
         * virtual_sales : 0
         * shop_price : 0.00
         * promote_price : 0.00
         * promote_start_date : 0
         * promote_end_date : 0
         * warn_number : 1
         * keywords :
         * goods_brief :
         * goods_desc :
         * goods_thumb :
         * goods_img :
         * original_img :
         * is_real : 1
         * extension_code :
         * is_on_sale : 1
         * is_alone_sale : 1
         * is_shipping : 0
         * integral : 0
         * add_time : 1503300252
         * sort_order : null
         * is_delete : 0
         * is_best : 0
         * is_new : 0
         * is_hot : 0
         * is_promote : 0
         * bonus_type_id : 0
         * last_update : 1503300765
         * goods_type : 0
         * seller_note :
         * give_integral : -1
         * rank_integral : -1
         * suppliers_id : 0
         * is_check : null
         * jifen : 0
         * brand_name : null
         * brand_logo : null
         * brand_desc : null
         * site_url : null
         * is_show : null
         * img_width": 790,
         * "img_height": 560
         */

        private float img_width, img_height;
        private String goods_id;
        private String cat_id;
        private String goods_sn;
        private String goods_name;
        private String goods_name_style;
        private String click_count;
        private String brand_id;
        private String provider_name;
        private String goods_number;
        private String goods_weight;
        private String market_price;
        private String virtual_sales;
        private String shop_price;
        private String promote_price;
        private String promote_start_date;
        private String promote_end_date;
        private String warn_number;
        private String keywords;
        private String goods_brief;
        private String goods_desc;
        private String goods_thumb;
        private String goods_img;
        private String original_img;
        private String is_real;
        private String extension_code;
        private String is_on_sale;
        private String is_alone_sale;
        private String is_shipping;
        private String integral;
        private String add_time;
        private String sort_order;
        private String is_delete;
        private String is_best;
        private String is_new;
        private String is_hot;
        private String is_promote;
        private String bonus_type_id;
        private String last_update;
        private String goods_type;
        private String seller_note;
        private String give_integral;
        private String rank_integral;
        private String suppliers_id;
        private String is_check;
        private String jifen;
        private String brand_name;
        private String brand_logo;
        private String brand_desc;
        private String site_url;
        private String is_show;
        private List<String> img_desc;

        public float getImg_width() {
            return img_width;
        }

        public void setImg_width(float img_width) {
            this.img_width = img_width;
        }

        public float getImg_height() {
            return img_height;
        }

        public void setImg_height(float img_height) {
            this.img_height = img_height;
        }

        public List<String> getImg_desc() {
            return img_desc;
        }

        public void setImg_desc(List<String> img_desc) {
            this.img_desc = img_desc;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getGoods_sn() {
            return goods_sn;
        }

        public void setGoods_sn(String goods_sn) {
            this.goods_sn = goods_sn;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_name_style() {
            return goods_name_style;
        }

        public void setGoods_name_style(String goods_name_style) {
            this.goods_name_style = goods_name_style;
        }

        public String getClick_count() {
            return click_count;
        }

        public void setClick_count(String click_count) {
            this.click_count = click_count;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getProvider_name() {
            return provider_name;
        }

        public void setProvider_name(String provider_name) {
            this.provider_name = provider_name;
        }

        public String getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(String goods_number) {
            this.goods_number = goods_number;
        }

        public String getGoods_weight() {
            return goods_weight;
        }

        public void setGoods_weight(String goods_weight) {
            this.goods_weight = goods_weight;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getVirtual_sales() {
            return virtual_sales;
        }

        public void setVirtual_sales(String virtual_sales) {
            this.virtual_sales = virtual_sales;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getPromote_price() {
            return promote_price;
        }

        public void setPromote_price(String promote_price) {
            this.promote_price = promote_price;
        }

        public String getPromote_start_date() {
            return promote_start_date;
        }

        public void setPromote_start_date(String promote_start_date) {
            this.promote_start_date = promote_start_date;
        }

        public String getPromote_end_date() {
            return promote_end_date;
        }

        public void setPromote_end_date(String promote_end_date) {
            this.promote_end_date = promote_end_date;
        }

        public String getWarn_number() {
            return warn_number;
        }

        public void setWarn_number(String warn_number) {
            this.warn_number = warn_number;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getGoods_brief() {
            return goods_brief;
        }

        public void setGoods_brief(String goods_brief) {
            this.goods_brief = goods_brief;
        }

        public String getGoods_desc() {
            return goods_desc;
        }

        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
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

        public String getIs_on_sale() {
            return is_on_sale;
        }

        public void setIs_on_sale(String is_on_sale) {
            this.is_on_sale = is_on_sale;
        }

        public String getIs_alone_sale() {
            return is_alone_sale;
        }

        public void setIs_alone_sale(String is_alone_sale) {
            this.is_alone_sale = is_alone_sale;
        }

        public String getIs_shipping() {
            return is_shipping;
        }

        public void setIs_shipping(String is_shipping) {
            this.is_shipping = is_shipping;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getSort_order() {
            return sort_order;
        }

        public void setSort_order(String sort_order) {
            this.sort_order = sort_order;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public String getIs_best() {
            return is_best;
        }

        public void setIs_best(String is_best) {
            this.is_best = is_best;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(String is_hot) {
            this.is_hot = is_hot;
        }

        public String getIs_promote() {
            return is_promote;
        }

        public void setIs_promote(String is_promote) {
            this.is_promote = is_promote;
        }

        public String getBonus_type_id() {
            return bonus_type_id;
        }

        public void setBonus_type_id(String bonus_type_id) {
            this.bonus_type_id = bonus_type_id;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public String getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public String getSeller_note() {
            return seller_note;
        }

        public void setSeller_note(String seller_note) {
            this.seller_note = seller_note;
        }

        public String getGive_integral() {
            return give_integral;
        }

        public void setGive_integral(String give_integral) {
            this.give_integral = give_integral;
        }

        public String getRank_integral() {
            return rank_integral;
        }

        public void setRank_integral(String rank_integral) {
            this.rank_integral = rank_integral;
        }

        public String getSuppliers_id() {
            return suppliers_id;
        }

        public void setSuppliers_id(String suppliers_id) {
            this.suppliers_id = suppliers_id;
        }

        public String getIs_check() {
            return is_check;
        }

        public void setIs_check(String is_check) {
            this.is_check = is_check;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getBrand_logo() {
            return brand_logo;
        }

        public void setBrand_logo(String brand_logo) {
            this.brand_logo = brand_logo;
        }

        public String getBrand_desc() {
            return brand_desc;
        }

        public void setBrand_desc(String brand_desc) {
            this.brand_desc = brand_desc;
        }

        public String getSite_url() {
            return site_url;
        }

        public void setSite_url(String site_url) {
            this.site_url = site_url;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }
    }


}
