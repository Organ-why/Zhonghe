package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/6
 * desc:
 */

public class LogisticsInfo extends BaseBean {

    /**
     * number : 1202516745301
     * type : yunda
     * list : [{"time":"2017-01-07 16:05:38","status":"湖南省炎陵县公司快件已被 已签收 签收"},{"time":"2017-01-07 16:02:43","status":"湖南省炎陵县公司快件已被 已签收 签收"},{"time":"2017-01-07 15:43:42","status":"湖南省炎陵县公司进行派件扫描；派送业务员：陈晓东；联系电话：18173377752"},{"time":"2017-01-06 18:26:08","status":"湖南长沙分拨中心从站点发出，本次转运目的地：湖南省炎陵县公司"},{"time":"2017-01-06 17:06:52","status":"湖南长沙分拨中心在分拨中心进行卸车扫描"},{"time":"2017-01-05 23:48:08","status":"浙江杭州分拨中心进行装车扫描，即将发往：湖南长沙分拨中心"},{"time":"2017-01-05 23:44:03","status":"浙江杭州分拨中心进行中转集包扫描，将发往：湖南长沙分拨中心"},{"time":"2017-01-05 23:35:40","status":"浙江杭州分拨中心在分拨中心进行称重扫描"},{"time":"2017-01-05 20:01:03","status":"浙江主城区公司杭州拱墅区祥符桥服务部进行揽件扫描"}]
     * deliverystatus : 3
     * issign : 1
     * typename : 韵达
     */

    private String number;
    private String type;
    //1 在途中 2 派件中 3 已签收 4派送失败
    private String deliverystatus;

    private String issign;
    private String typename;
    private String express;
    private List<ListBean> list;

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
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

    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public String getIssign() {
        return issign;
    }

    public void setIssign(String issign) {
        this.issign = issign;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean extends BaseBean{
        /**
         * time : 2017-01-07 16:05:38
         * status : 湖南省炎陵县公司快件已被 已签收 签收
         */

        private String time;
        private String status;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
