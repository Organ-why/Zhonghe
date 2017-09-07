package com.zhonghe.shiangou.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * auther: whyang
 * date: 2017/8/24
 * desc:购物车列表
 */

public class CartGoods extends BaseBean {

    /**
     * goods_id : 118
     * cat_id : 33
     * goods_sn : hahaha11
     * goods_name : 红红绿绿
     * goods_name_style : +
     * click_count : 6
     * brand_id : 17
     * provider_name :
     * goods_number : 99
     * goods_weight : 0.000
     * market_price : 79.20
     * virtual_sales : 0
     * shop_price : 66.00
     * promote_price : 0.00
     * promote_start_date : 0
     * promote_end_date : 0
     * warn_number : 1
     * keywords :
     * goods_brief :
     * goods_desc : <p>还行吧</p>
     * goods_thumb : images/201708/thumb_img/118_thumb_G_1503379094922.jpg
     * goods_img : images/201708/goods_img/118_G_1503379094344.jpg
     * original_img : images/201708/source_img/118_G_1503379094276.jpg
     * is_real : 1
     * extension_code :
     * is_on_sale : 1
     * is_alone_sale : 1
     * is_shipping : 0
     * integral : 0
     * add_time : 1503379094
     * sort_order : 100
     * is_delete : 1
     * is_best : 0
     * is_new : 0
     * is_hot : 0
     * is_promote : 0
     * bonus_type_id : 0
     * last_update : 1503472258
     * goods_type : 10
     * seller_note :
     * give_integral : -1
     * rank_integral : -1
     * suppliers_id : 0
     * is_check : null
     * jifen : 0
     */

    boolean isCheck;
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
    private double shop_price;
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
    /**
     * rec_id : 267
     * user_id : 21
     * session_id :
     * product_id : 0
     * goods_price : 250
     * goods_count : 5
     * goods_attr : null
     * parent_id : 0
     * rec_type : 0
     * is_gift : 0
     * can_handsel : 0
     * goods_attr_id : null
     * is_check : null
     * label : 农场直销
     */

    private String rec_id;
    private String user_id;
    private String session_id;
    private String product_id;
    private String goods_price;
    private String goods_count;
    private String goods_attr;
    private String parent_id;
    private String rec_type;
    private String is_gift;
    private String can_handsel;
    private String goods_attr_id;
    private String is_checkX;
    private String label;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
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

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
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

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(String goods_count) {
        this.goods_count = goods_count;
    }

    public String getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(String goods_attr) {
        this.goods_attr = goods_attr;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getRec_type() {
        return rec_type;
    }

    public void setRec_type(String rec_type) {
        this.rec_type = rec_type;
    }

    public String getIs_gift() {
        return is_gift;
    }

    public void setIs_gift(String is_gift) {
        this.is_gift = is_gift;
    }

    public String getCan_handsel() {
        return can_handsel;
    }

    public void setCan_handsel(String can_handsel) {
        this.can_handsel = can_handsel;
    }

    public String getGoods_attr_id() {
        return goods_attr_id;
    }

    public void setGoods_attr_id(String goods_attr_id) {
        this.goods_attr_id = goods_attr_id;
    }

    public String getIs_checkX() {
        return is_checkX;
    }

    public void setIs_checkX(String is_checkX) {
        this.is_checkX = is_checkX;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
