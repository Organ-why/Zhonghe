package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/9/5
 * desc:列表
 */

public class PointItemListInfo extends BaseBean {

    /**
     * goods_id : 186
     * exchange_integral : 1000
     * is_exchange : 1
     * is_hot : 0
     * goods_thumb : http://test.shiangou.com.cn/
     * goods_name : null
     */

    private String goods_id;
    private double exchange_integral;
    private String is_exchange;
    private String is_hot;
    private String goods_thumb;
    private String goods_name;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public double getExchange_integral() {
        return exchange_integral;
    }

    public void setExchange_integral(double exchange_integral) {
        this.exchange_integral = exchange_integral;
    }

    public String getIs_exchange() {
        return is_exchange;
    }

    public void setIs_exchange(String is_exchange) {
        this.is_exchange = is_exchange;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getGoods_thumb() {
        return goods_thumb;
    }

    public void setGoods_thumb(String goods_thumb) {
        this.goods_thumb = goods_thumb;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
}
