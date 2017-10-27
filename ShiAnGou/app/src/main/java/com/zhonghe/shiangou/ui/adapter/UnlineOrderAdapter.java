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
import com.zhonghe.shiangou.data.bean.OrderUnline;
import com.zhonghe.shiangou.data.bean.ShopInfo;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.utile.UtilPro;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/27
 * desc: 线下商品
 */

public class UnlineOrderAdapter extends AbsAdapter<OrderUnline> {
    public UnlineOrderAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_order_unline, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final OrderUnline info = mList.get(i);

        ProjectApplication.mImageLoader.loadRoundedImage(holder.idHomeCategoryItemIv, UtilPro.getImgHttpUrl(info.getMerchant_thumb()));
        holder.idTotalMoneyTv.setText(String.format(mContext.getString(R.string.order_unline_consume), info.getTotal_money()));
        holder.idShopNameTv.setText(info.getMerchant_name());
        holder.idDataTv.setText(Util.formatDate(info.getDatetime(),Util.ALLDATETIME));
        String point = info.getActual_points()==0?"":info.getActual_points()+"积分";
        holder.idActualMoneyTv.setText(info.getActual_money()+"元  "+point);
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
        @BindView(R.id.id_goods_rigthtbt_bt)
        TextView idGoodsRigthtbtBt;
        @BindView(R.id.id_shop_name_tv)
        TextView idShopNameTv;
        @BindView(R.id.id_total_money_tv)
        TextView idTotalMoneyTv;
        @BindView(R.id.id_actual_money_tv)
        TextView idActualMoneyTv;
        @BindView(R.id.id_data_tv)
        TextView idDataTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
