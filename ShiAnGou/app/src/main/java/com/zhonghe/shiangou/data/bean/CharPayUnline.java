package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/9/27
 * desc: 线下支付凭证
 */

public class CharPayUnline {
    String pay_type, ali_secret;

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getAli_secret() {
        return ali_secret;
    }

    public void setAli_secret(String ali_secret) {
        this.ali_secret = ali_secret;
    }
}
