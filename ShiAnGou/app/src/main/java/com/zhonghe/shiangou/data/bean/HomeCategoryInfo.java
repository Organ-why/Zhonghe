package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * Date: 2017/8/7.
 * Author: whyang
 */
public class HomeCategoryInfo extends BaseBean {


    /**
     * categoryname : Fruits
     * cat_images : http://test.shiangou.com.cn/data/afficheimg/shuiguo.png
     * cat_thumb : http://test.shiangou.com.cn/data/afficheimg/shuiguo2.png
     * cat_name : 蔬菜水果
     * cat_id : 33
     * list : [{"goods_id":"105","cat_id":"33","goods_sn":"ECS000105","goods_name":"qq","goods_name_style":"+","click_count":"0","brand_id":"0","provider_name":"","goods_number":"1","goods_weight":"0.000","market_price":"0.01","virtual_sales":"0","shop_price":"0.01","promote_price":"0.00","promote_start_date":"0","promote_end_date":"0","warn_number":"1","keywords":"","goods_brief":"","goods_desc":"","goods_thumb":"","goods_img":"http://test.shiangou.com.cn/","original_img":"","is_real":"1","extension_code":"","is_on_sale":"1","is_alone_sale":"1","is_shipping":"0","integral":"0","add_time":"1502761701","sort_order":"100","is_delete":"0","is_best":"0","is_new":"0","is_hot":"0","is_promote":"0","bonus_type_id":"0","last_update":"1502761701","goods_type":"0","seller_note":"","give_integral":"-1","rank_integral":"-1","suppliers_id":"0","is_check":null},{"goods_id":"106","cat_id":"33","goods_sn":"ECS000106","goods_name":"fff","goods_name_style":"+","click_count":"0","brand_id":"0","provider_name":"","goods_number":"1","goods_weight":"0.000","market_price":"0.00","virtual_sales":"0","shop_price":"0.00","promote_price":"0.00","promote_start_date":"0","promote_end_date":"0","warn_number":"1","keywords":"","goods_brief":"","goods_desc":"","goods_thumb":"","goods_img":"http://test.shiangou.com.cn/","original_img":"","is_real":"1","extension_code":"","is_on_sale":"1","is_alone_sale":"1","is_shipping":"0","integral":"0","add_time":"1502767425","sort_order":"100","is_delete":"0","is_best":"0","is_new":"0","is_hot":"0","is_promote":"0","bonus_type_id":"0","last_update":"1502767425","goods_type":"0","seller_note":"","give_integral":"-1","rank_integral":"-1","suppliers_id":"0","is_check":null},{"goods_id":"78","cat_id":"49","goods_sn":"s1","goods_name":"秋木耳","goods_name_style":"+","click_count":"31","brand_id":"16","provider_name":"","goods_number":"1111","goods_weight":"0.000","market_price":"50.00","virtual_sales":"500","shop_price":"50.00","promote_price":"0.00","promote_start_date":"0","promote_end_date":"0","warn_number":"1","keywords":"","goods_brief":"","goods_desc":"","goods_thumb":"images/201708/thumb_img/78_thumb_G_1502069487693.jpg","goods_img":"http://test.shiangou.com.cn/images/201708/goods_img/78_G_1502069487118.jpg","original_img":"images/201708/source_img/78_G_1502069487981.jpg","is_real":"1","extension_code":"","is_on_sale":"1","is_alone_sale":"1","is_shipping":"0","integral":"0","add_time":"1502069487","sort_order":"100","is_delete":"0","is_best":"0","is_new":"0","is_hot":"0","is_promote":"0","bonus_type_id":"0","last_update":"1502069487","goods_type":"0","seller_note":"","give_integral":"-1","rank_integral":"-1","suppliers_id":"1","is_check":null},{"goods_id":"79","cat_id":"50","goods_sn":"s2","goods_name":"小白菜","goods_name_style":"+","click_count":"6","brand_id":"16","provider_name":"","goods_number":"1111","goods_weight":"0.000","market_price":"8.00","virtual_sales":"60000","shop_price":"8.00","promote_price":"0.00","promote_start_date":"0","promote_end_date":"0","warn_number":"1","keywords":"","goods_brief":"","goods_desc":"","goods_thumb":"images/201708/thumb_img/79_thumb_G_1502069695632.jpg","goods_img":"http://test.shiangou.com.cn/images/201708/goods_img/79_G_1502069695674.jpg","original_img":"images/201708/source_img/79_G_1502069695146.jpg","is_real":"1","extension_code":"","is_on_sale":"1","is_alone_sale":"1","is_shipping":"0","integral":"0","add_time":"1502069695","sort_order":"100","is_delete":"0","is_best":"0","is_new":"0","is_hot":"0","is_promote":"0","bonus_type_id":"0","last_update":"1502069695","goods_type":"0","seller_note":"","give_integral":"-1","rank_integral":"-1","suppliers_id":"1","is_check":null}]
     */

    private String categoryname;
    private String cat_images;
    private String cat_thumb;
    private String cat_name;
    private String cat_id;
    private List<GoodsInfo> list;

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCat_images() {
        return cat_images;
    }

    public void setCat_images(String cat_images) {
        this.cat_images = cat_images;
    }

    public String getCat_thumb() {
        return cat_thumb;
    }

    public void setCat_thumb(String cat_thumb) {
        this.cat_thumb = cat_thumb;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public List<GoodsInfo> getList() {
        return list;
    }

    public void setList(List<GoodsInfo> list) {
        this.list = list;
    }


}
