package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CategoryChild;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/25
 * desc:子分类
 */

public class CategoryChildAdapter extends AbsAdapter<CategoryChild> {

    public CategoryChildAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_category_child, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        CategoryChild child = mList.get(i);
        ProjectApplication.mImageLoader.loadImage(holder.idItemCategoryChildImg, child.getCat_images());
        holder.idItemCategoryChildTv.setText(child.getCat_name());

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.id_item_category_child_img)
        SimpleDraweeView idItemCategoryChildImg;
        @Bind(R.id.id_item_category_child_tv)
        TextView idItemCategoryChildTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
