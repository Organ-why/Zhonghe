package com.zhonghe.shiangou.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.data.bean.ProInfo;

import java.util.List;

/**
 * Date: 2017/8/7.
 * Author: whyang
 * 分类商品列表
 */
public class HomeCategoryListView {
    private Context context;
    private LayoutInflater inflater;

    public HomeCategoryListView(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void initView(List<HomeCategoryInfo> categoryInfos, ViewGroup parentView) {
        for (HomeCategoryInfo info : categoryInfos) {
            View inflate = inflater.inflate(R.layout.layout_home_category_item, null);
            inflate.findViewById(R.id.id_home_category_item_title_iv);//分类大图片
            LinearLayout itemList = (LinearLayout) inflate.findViewById(R.id.id_home_category_item_ll);//商品列表
            for (ProInfo itemInfo : info.getProList()) {
                View itemView = inflater.inflate(R.layout.item_home_category_item, null);
                itemList.addView(itemView);
            }

            parentView.addView(inflate);
        }

    }
}
