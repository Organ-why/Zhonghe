package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/6
 * desc: 退货详情
 */

public class RefundsDetailInfo extends BaseBean {

    /**
     * return_id : 11
     * order_sn : 150452061645293
     * user_id : 21
     * explain : 还打不打好的九点半
     * return_sn :
     * datetime : null
     * order_id : 528
     * total_price : 0
     * sales_state : [{"state_id":"1","stage":"1","datetime":"2017-09-06 11:03","return_id":"11"},{"state_id":"2","stage":"2","datetime":"2017-09-06 11:35","return_id":"11"},{"state_id":"3","stage":"3","datetime":"2017-09-06 14:21","return_id":"11"}]
     */

    private String return_id;
    private String order_sn;
    private String user_id;
    private String explain;
    private String return_sn;
    private String datetime;
    private String order_id;
    private String total_price;
    private List<SalesStateBean> sales_state;

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

    public String getReturn_sn() {
        return return_sn;
    }

    public void setReturn_sn(String return_sn) {
        this.return_sn = return_sn;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public List<SalesStateBean> getSales_state() {
        return sales_state;
    }

    public void setSales_state(List<SalesStateBean> sales_state) {
        this.sales_state = sales_state;
    }

    public static class SalesStateBean extends BaseBean {
        /**
         * state_id : 1
         * stage : 1
         * datetime : 2017-09-06 11:03
         * return_id : 11
         */

        private String state_id;
        private String stage;
        private String datetime;
        private String return_id;

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getReturn_id() {
            return return_id;
        }

        public void setReturn_id(String return_id) {
            this.return_id = return_id;
        }
    }
}
