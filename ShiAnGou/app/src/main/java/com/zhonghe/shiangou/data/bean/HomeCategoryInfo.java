package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * Date: 2017/8/7.
 * Author: whyang
 */
public class HomeCategoryInfo extends BaseBean {
    int id;
    String categoryName;
    List<ProInfo> proList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProInfo> getProList() {
        return proList;
    }

    public void setProList(List<ProInfo> proList) {
        this.proList = proList;
    }

}
