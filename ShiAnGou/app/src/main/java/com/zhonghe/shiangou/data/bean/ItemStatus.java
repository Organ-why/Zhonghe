package com.zhonghe.shiangou.data.bean;

/**
 * auther: whyang
 * date: 2017/9/11
 * desc:
 * ItemStatus 用封装列表每一项的状态，包括：
 * viewType item的类型，group item 还是 subitem
 * groupItemIndex 一级索引位置
 * subItemIndex 如果该 item 是一个二级子项目，则保存子项目索引
 */

public class ItemStatus extends BaseBean {
    public static final int VIEW_TYPE_GROUPITEM = 0;
    public static final int VIEW_TYPE_SUBITEM = 1;

    private int viewType;
    private int groupItemIndex = 0;
    private int subItemIndex = -1;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getGroupItemIndex() {
        return groupItemIndex;
    }

    public void setGroupItemIndex(int groupItemIndex) {
        this.groupItemIndex = groupItemIndex;
    }

    public int getSubItemIndex() {
        return subItemIndex;
    }

    public void setSubItemIndex(int subItemIndex) {
        this.subItemIndex = subItemIndex;
    }
}
