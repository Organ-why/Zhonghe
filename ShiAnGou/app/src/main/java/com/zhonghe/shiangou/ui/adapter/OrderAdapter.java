package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.data.bean.OrderInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/23
 * desc:订单 adapter
 */

public class OrderAdapter extends AbsAdapter<OrderInfo> {
    public OrderAdapter(Context context, List<OrderInfo> datas) {
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
        OrderInfo item = getItem(i);
        holder.idOrderCodeTv.setText(UtilString.nullToString(item.getOrder_sn()));
        String pay_status = item.getPay_status();
//        ,1(代付款),2(代发货),3(待收货)，4（待评论）
        String status = "";
        if (pay_status.equals("1")) {
            status = mContext.getResources().getString(R.string.common_user_order_unpay);
        } else if (pay_status.equals("2")) {
            status = mContext.getResources().getString(R.string.common_user_order_unsend);
        } else if (pay_status.equals("3")) {
            status = mContext.getResources().getString(R.string.common_user_order_wait);
        } else if (pay_status.equals("4")) {
            status = mContext.getResources().getString(R.string.common_user_order_unremark);
        }
        holder.idOrderTypeTv.setText(status);
        holder.idOrderTotalTv.setText(UtilString.nullToEmpty(String.valueOf(item.getPrice())));
        holder.idOrderTotalTv.setText(String.format(mContext.getResources().getString(R.string.order_item_count), item.getCount()));

        holder.goodslist_ll.removeAllViews();
        for (GoodsInfo goodsInfo : item.getGoods_list()) {
            View orderItem = mInflater.inflate(R.layout.item_order_child, null);
            SimpleDraweeView img = (SimpleDraweeView) orderItem.findViewById(R.id.id_order_goods_img);
            TextView goodsname = (TextView) orderItem.findViewById(R.id.id_order_goods_name_tv);
            ProjectApplication.mImageLoader.loadImage(img, goodsInfo.getGoods_img());
            goodsname.setText(UtilString.nullToEmpty(goodsInfo.getGoods_name()));
            holder.goodslist_ll.addView(orderItem);
        }


        return view;
    }

    static class ViewHolder {
        @Bind(R.id.id_order_code_tv)
        TextView idOrderCodeTv;
        @Bind(R.id.id_order_type_tv)
        TextView idOrderTypeTv;
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
        @Bind(R.id.id_order_goodslist_ll)
        LinearLayout goodslist_ll;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
