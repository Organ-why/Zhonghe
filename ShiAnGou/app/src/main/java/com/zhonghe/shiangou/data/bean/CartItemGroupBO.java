package com.zhonghe.shiangou.data.bean;

import java.util.List;

/**
 * Created by a on 2017/8/9.
 */

public class CartItemGroupBO extends BaseBean {
    List<CartItemBO> childPro;
    int Id;
    boolean isCheck;
    String groupName;
    String groupImg;
    String groupIcon;

    public CartItemGroupBO() {
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<CartItemBO> getChildPro() {
        return childPro;
    }

    public void setChildPro(List<CartItemBO> childPro) {
        this.childPro = childPro;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupImg() {
        return groupImg;
    }

    public void setGroupImg(String groupImg) {
        this.groupImg = groupImg;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }
}
