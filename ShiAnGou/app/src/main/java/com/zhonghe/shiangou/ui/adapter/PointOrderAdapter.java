package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.PointOrderInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/23
 * desc:积分兑换记录 adapter
 */

public class PointOrderAdapter extends AbsAdapter<PointOrderInfo> {
    private pointOrderDelListener ondelListener;

    public PointOrderAdapter(Context context, List<PointOrderInfo> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_point_order, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final PointOrderInfo item = mList.get(i);
        ProjectApplication.mImageLoader.loadImage(holder.idHomeCategoryItemIv, item.getGoods_img());
        holder.idPointRecordDelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ondelListener.OnDel(i);
            }
        });
        holder.idPointRecordNumTv.setText(String.format(mContext.getString(R.string.point_record_num_format), item.getTotal_price()));
        holder.idHomeCategoryItemTitleTv.setText(UtilString.nullToEmpty(item.getGoods_name()));
        holder.idPointRecordTimeTv.setText(String.format(mContext.getString(R.string.point_record_time), Util.formatDate(item.getAdd_time(), Util.ALLDATE)));
        return view;
    }

    public void setOndelListener(pointOrderDelListener ondelListener) {
        this.ondelListener = ondelListener;
    }


    static class ViewHolder {
        @BindView(R.id.id_home_category_item_iv)
        SimpleDraweeView idHomeCategoryItemIv;
        @BindView(R.id.id_point_record_del_iv)
        ImageButton idPointRecordDelIv;
        @BindView(R.id.id_point_record_num_tv)
        TextView idPointRecordNumTv;
        @BindView(R.id.ids_point_order_msg_ll)
        LinearLayout idsPointOrderMsgLl;
        @BindView(R.id.id_home_category_item_title_tv)
        TextView idHomeCategoryItemTitleTv;
        @BindView(R.id.id_point_record_time_tv)
        TextView idPointRecordTimeTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface pointOrderDelListener {
        void OnDel(int position);
    }
}
