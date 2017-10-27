package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2017/8/8.
 * Author: whyang
 */
public class HomeCategoryTitleAdapter extends AbsAdapter<HomeCategoryInfo> {


    List<HomeCategoryInfo> datas;

    public HomeCategoryTitleAdapter(Context context) {
        super(context);
    }

    public HomeCategoryTitleAdapter(Context context, List<HomeCategoryInfo> datas) {
        super(context, datas);
        this.datas = datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_category_title, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeCategoryInfo info = datas.get(position);
        ProjectApplication.mImageLoader.loadImage(holder.idItemHomeCategoryTitleIv, info.getCat_thumb());
        holder.idItemHomeCategoryTitleName.setText(info.getCat_name());
        int width = Util.GetWindowWidth(mContext) / 4;
        LinearLayout.LayoutParams categoryParams = new LinearLayout.LayoutParams(width, Util.dip2px(mContext, 75));
        holder.idItemHomeCategoryTitleLl.setLayoutParams(categoryParams);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.id_item_home_category_title_iv)
        SimpleDraweeView idItemHomeCategoryTitleIv;
        @BindView(R.id.id_item_home_category_title_name)
        TextView idItemHomeCategoryTitleName;
        @BindView(R.id.id_item_home_category_title_ll)
        LinearLayout idItemHomeCategoryTitleLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
