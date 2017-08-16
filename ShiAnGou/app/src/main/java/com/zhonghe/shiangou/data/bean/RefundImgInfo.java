package com.zhonghe.shiangou.data.bean;

/**
 * Created by a on 2017/8/16.
 */

public class RefundImgInfo extends BaseBean {
    boolean isAdd;
    String imgUrl;


    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
