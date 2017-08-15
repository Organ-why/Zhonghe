package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date: 2017/8/8.
 * Author: whyang
 */
public class HomeCategoryTitleAdapter extends AbsAdapter<HomeCategoryInfo> {




    public HomeCategoryTitleAdapter(Context context) {
        super(context);
    }

    public HomeCategoryTitleAdapter(Context context, List<HomeCategoryInfo> datas) {
        super(context, datas);
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
        int width = Utilm.GetWindowWidth(mContext) / 4;
        LinearLayout.LayoutParams categoryParams = new LinearLayout.LayoutParams(width, Utilm.dip2px(mContext, 75));
        holder.idItemHomeCategoryTitleLl.setLayoutParams(categoryParams);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.id_item_home_category_title_iv)
        ImageView idItemHomeCategoryTitleIv;
        @Bind(R.id.id_item_home_category_title_name)
        TextView idItemHomeCategoryTitleName;
        @Bind(R.id.id_item_home_category_title_ll)
        LinearLayout idItemHomeCategoryTitleLl;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
