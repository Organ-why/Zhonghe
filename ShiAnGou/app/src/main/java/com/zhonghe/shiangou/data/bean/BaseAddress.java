package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/8/28
 * desc:
 */

public class BaseAddress extends BaseBean {

    /**
     * areaid : 1
     * areaname : 北京
     * parentid : 0
     * arrparentid : 0
     * child : 1
     * listorder : 0
     */

    private String areaid;
    private String areaname;
    private String parentid;
    private String arrparentid;
    private String child;
    private String listorder;

    // "arrchildid": "1,37,38,41,42,43,44,45,46,47,48,4
    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getArrparentid() {
        return arrparentid;
    }

    public void setArrparentid(String arrparentid) {
        this.arrparentid = arrparentid;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getListorder() {
        return listorder;
    }

    public void setListorder(String listorder) {
        this.listorder = listorder;
    }
}
