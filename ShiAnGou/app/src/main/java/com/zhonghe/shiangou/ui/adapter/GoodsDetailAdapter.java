package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.utile.UtilPro;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/27
 * desc:图文
 */

public class GoodsDetailAdapter extends AbsAdapter<String> {
    float AspectRatio = 1;

    public GoodsDetailAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    public void setAspectRatio(float aspectRatio) {
        this.AspectRatio = aspectRatio;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_goods_detail_img, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.idGoodsImg.setAspectRatio(AspectRatio);
        ProjectApplication.mImageLoader.loadImagegray(holder.idGoodsImg, UtilPro.getImgHttpUrl(getItem(i)));

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.id_goods_img)
        SimpleDraweeView idGoodsImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
