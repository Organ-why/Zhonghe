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
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.activity.LikeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/27
 * desc:
 */

public class LikeListAdapter extends AbsAdapter<GoodsInfo> {
    cancelLikeListener cancelListListener;

    public LikeListAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_like, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final GoodsInfo info = mList.get(i);
        ProjectApplication.mImageLoader.loadImage(holder.idHomeCategoryItemIv, info.getGoods_img());
        holder.idHomeCategoryItemTitleTv.setText(UtilString.nullToEmpty(info.getGoods_name()));
        holder.idHomeCategoryItemPriceTv.setText(UtilString.nullToEmpty(Util.formatPrice(info.getShop_price())));
        holder.idItemMoneyTypeTv.setText(mContext.getString(R.string.symbol_money));
        holder.idItemBtv.setText(mContext.getString(R.string.prodetail_collection_cancel));
        holder.idItemBtv.setBackgroundResource(R.drawable.circle_graylight_hollow_bg);
        holder.idItemBtv.setTextColor(mContext.getResources().getColor(R.color.common_font_deep));
        holder.idItemBtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelListListener.onCancel(info.getGoods_id(), i);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProDispatcher.goGoodsDetailActivity(mContext, UtilString.nullToEmpty(info.getGoods_id()));
            }
        });
        return view;
    }

    public void setCancelListListener(LikeActivity cancelListListener) {
        this.cancelListListener = cancelListListener;
    }


    static class ViewHolder {
        @BindView(R.id.id_home_category_item_iv)
        SimpleDraweeView idHomeCategoryItemIv;
        @BindView(R.id.id_home_category_item_title_tv)
        TextView idHomeCategoryItemTitleTv;
        @BindView(R.id.id_item_money_type_tv)
        TextView idItemMoneyTypeTv;
        @BindView(R.id.id_home_category_item_price_tv)
        TextView idHomeCategoryItemPriceTv;
        @BindView(R.id.id_item_btv)
        TextView idItemBtv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface cancelLikeListener {
        void onCancel(String goodsId, int position);
    }
}
