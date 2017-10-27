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
import com.zhonghe.shiangou.data.bean.ShopInfo;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.widget.RatingBar;
import com.zhonghe.shiangou.utile.UtilPro;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/27
 * desc: 线下商品
 */

public class UnlineShopAdapter extends AbsAdapter<ShopInfo> {
    public UnlineShopAdapter(Context context, List datas) {
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

        final ShopInfo info = mList.get(i);

        ProjectApplication.mImageLoader.loadRoundedImage(holder.idHomeCategoryItemIv, UtilPro.getImgHttpUrl(info.getMerchant_thumb()));
        holder.idGoodsAddressTv.setText(String.format(mContext.getString(R.string.confirmorder_addre), info.getAddress()));
        holder.idGoodsTitleTv.setText(UtilString.nullToEmpty(info.getMerchant_name()));
        holder.ratingBar.setStar(info.getGrade());
        holder.idGoodsRigthtopTv.setText(Util.formatMetre(info.getBetween()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProDispatcher.goPointUnlineDetailActivity(mContext, info.getMerchant_id());
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.id_home_category_item_iv)
        SimpleDraweeView idHomeCategoryItemIv;
        @BindView(R.id.id_goods_rigthtop_tv)
        TextView idGoodsRigthtopTv;
        @BindView(R.id.id_goods_title_tv)
        TextView idGoodsTitleTv;
        @BindView(R.id.id_goods_address_tv)
        TextView idGoodsAddressTv;
        @BindView(R.id.id_goods_popular_tv)
        TextView idGoodsPopularTv;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
