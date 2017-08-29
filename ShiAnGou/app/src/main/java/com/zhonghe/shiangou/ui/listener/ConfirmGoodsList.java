package com.zhonghe.shiangou.ui.listener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CartGoods;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/29
 * desc: 确认商品列表
 */

public class ConfirmGoodsList {
    List<CartGoods> mList;
    Context mContext;

    public ConfirmGoodsList(List<CartGoods> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void initView(ViewGroup viewGroup) {
        for (CartGoods info : mList) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_prodetail_sku_content, null);
            ViewHolder holder = new ViewHolder(view);
            holder.skuSelectTitleTv.setText(UtilString.nullToEmpty(info.getGoods_name()));
            ProjectApplication.mImageLoader.loadImage(holder.skuSelectImg, info.getGoods_thumb());
            holder.itemSkuSelectPriceTv.setText(UtilString.nullToEmpty(info.getShop_price()));
            viewGroup.addView(view);
        }
    }

    static class ViewHolder {
        @Bind(R.id.sku_select_img)
        SimpleDraweeView skuSelectImg;
        @Bind(R.id.sku_select_title_tv)
        TextView skuSelectTitleTv;
        @Bind(R.id.item_sku_select_symbol_tv)
        TextView itemSkuSelectSymbolTv;
        @Bind(R.id.item_sku_select_price_tv)
        TextView itemSkuSelectPriceTv;
        @Bind(R.id.item_sku_select_oldprice_tv)
        TextView itemSkuSelectOldpriceTv;
        @Bind(R.id.item_sku_select_amounttv)
        TextView itemSkuSelectAmounttv;
        @Bind(R.id.order_sku_rl)
        RelativeLayout orderSkuRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
