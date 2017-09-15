package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.widget.RatingBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/27
 * desc: 线下商品
 */

public class UnlineGoodsAdapter extends AbsAdapter<GoodsInfo> {
    public UnlineGoodsAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_goods_unline, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

//        GoodsInfo info = mList.get(i);
//        ProjectApplication.mImageLoader.loadImage(holder.idHomeCategoryItemIv, info.getGoods_img());
//        holder.idHomeCategoryItemTitleTv.setText(UtilString.nullToEmpty(info.getGoods_name()));
//        holder.idHomeCategoryItemPriceTv.setText(UtilString.nullToEmpty(Util.formatPrice(info.getShop_price())));
        return view;
    }

    @Override
    public int getCount() {
        return 20;
    }

    static class ViewHolder {
        @Bind(R.id.id_home_category_item_iv)
        SimpleDraweeView idHomeCategoryItemIv;
        @Bind(R.id.id_goods_rigthtop_tv)
        TextView idGoodsRigthtopTv;
        @Bind(R.id.id_goods_title_tv)
        TextView idGoodsTitleTv;
        @Bind(R.id.id_goods_address_tv)
        TextView idGoodsAddressTv;
        @Bind(R.id.id_goods_popular_tv)
        TextView idGoodsPopularTv;
        @Bind(R.id.ratingBar)
        RatingBar ratingBar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
