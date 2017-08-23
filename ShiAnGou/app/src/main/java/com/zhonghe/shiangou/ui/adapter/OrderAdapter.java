package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.shiangou.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/23
 * desc:订单 adapter
 */

public class OrderAdapter extends AbsAdapter {
    public OrderAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_order, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.id_order_code_tv)
        TextView idOrderCodeTv;
        @Bind(R.id.id_order_type_tv)
        TextView idOrderTypeTv;
        @Bind(R.id.id_order_goods_img)
        SimpleDraweeView idOrderGoodsImg;
        @Bind(R.id.id_order_goods_name_tv)
        TextView idOrderGoodsNameTv;
        @Bind(R.id.id_order_num_tv)
        TextView idOrderNumTv;
        @Bind(R.id.id_order_total_tv)
        TextView idOrderTotalTv;
        @Bind(R.id.id_order_freight_tv)
        TextView idOrderFreightTv;
        @Bind(R.id.id_order_negative_tv)
        TextView idOrderNegativeTv;
        @Bind(R.id.id_order_positive_tv)
        TextView idOrderPositiveTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
