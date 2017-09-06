package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.data.bean.RefundImgInfo;
import com.zhonghe.shiangou.data.bean.RefundsItemInfo;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/29
 * desc: 评论列表
 */

public class RefundsAdapter extends AbsAdapter<RefundsItemInfo> {
    RefundSubmitAdapter adapter;

    public RefundsAdapter(Context context, List<RefundsItemInfo> addlist) {
        super(context, addlist);
        adapter = new RefundSubmitAdapter(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_refunds_parent, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        RefundsItemInfo info = mList.get(i);
        holder.idsTotalMoney.setText(String.format(mContext.getString(R.string.prodetail_refunds_code_format), info.getOrder_sn()));
        holder.idRefundPriceTv.setText(UtilString.nullToEmpty(info.getTotal_price()));
        holder.idRefundDescTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.idRefundGoodsLl.removeAllViews();
        if (UtilList.isNotEmpty(info.getGoods_list())) {
            for (final RefundsItemInfo.GoodsListBean goodsInfo : info.getGoods_list()) {
                View orderItem = mInflater.inflate(R.layout.item_order_child, null);
                SimpleDraweeView img = (SimpleDraweeView) orderItem.findViewById(R.id.id_order_goods_img);
                TextView goodsname = (TextView) orderItem.findViewById(R.id.id_order_goods_name_tv);
                ProjectApplication.mImageLoader.loadImage(img, goodsInfo.getGoods_thumb());
                goodsname.setText(UtilString.nullToEmpty(goodsInfo.getGoods_name()));
                holder.idRefundGoodsLl.addView(orderItem);
            }
        }
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.id_refund_goods_ll)
        LinearLayout idRefundGoodsLl;
        @Bind(R.id.id_refund_code_tv)
        TextView idsTotalMoney;
        @Bind(R.id.id_refund_price_tv)
        TextView idRefundPriceTv;
        @Bind(R.id.id_refund_desc_tv)
        TextView idRefundDescTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
