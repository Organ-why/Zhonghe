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
import com.zhonghe.shiangou.data.bean.PointGoodsInfo;
import com.zhonghe.shiangou.data.bean.PointItemInfo;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/5
 * desc:
 */

public class PointAdapter extends AbsAdapter<PointItemInfo> {
    public PointAdapter(Context context, List<PointItemInfo> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_point_pro_gv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final PointItemInfo info = mList.get(i);
        ProjectApplication.mImageLoader.loadImage(holder.idGoodsImg, info.getGoods_img());
        holder.idHomeCategoryItemTitleTv.setText(UtilString.nullToEmpty(info.getGoods_name()));
        holder.idHomeCategoryItemPriceTv.setText(Util.formatPrice(info.getExchange_integral()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProDispatcher.goPointDetailActivity(mContext,info.getGoods_id());
            }
        });
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.id_home_category_item_title_tv)
        TextView idHomeCategoryItemTitleTv;
        @Bind(R.id.id_home_category_item_price_tv)
        TextView idHomeCategoryItemPriceTv;
        @Bind(R.id.id_goods_img)
        SimpleDraweeView idGoodsImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
