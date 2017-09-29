package com.zhonghe.shiangou.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * auther: whyang
 * date: 2017/8/31
 * desc:
 */

public class CharPay extends BaseBean {

    /**
     * status : 1
     * data : {"appid":"wx91470ddde903ce11","noncestr":"yUcBZVQogw22kyx3vDxYi1G0DebWj2ii","package":"Sign=WXPay","partnerid":"1487655792","prepayid":"wx20170831173401f61e6926de0008671960","timestamp":1504172041,"sign":"AF94E5077A12C7D3ABA1A357D02D25AD","out_trade_no":"14876557921111111_3537"}
     */

    private String status;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseBean {
        /**
         * appid : wx91470ddde903ce11
         * noncestr : yUcBZVQogw22kyx3vDxYi1G0DebWj2ii
         * package : Sign=WXPay
         * partnerid : 1487655792
         * prepayid : wx20170831173401f61e6926de0008671960
         * timestamp : 1504172041
         * sign : AF94E5077A12C7D3ABA1A357D02D25AD
         * out_trade_no : 14876557921111111_3537
         */

        private String appid;
        private String noncestr;
        private String packageX;
        private String partnerid;
        private String prepayid;
        private int timestamp;
        private String sign;
        private String out_trade_no;
        //支付宝
        String ali_secret;
        int pay_type;

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public String getAli_secret() {
            return ali_secret;
        }

        public void setAli_secret(String ali_secret) {
            this.ali_secret = ali_secret;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }
    }
}
