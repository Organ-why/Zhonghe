package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.AddressInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.support.v7.appcompat.R.id.info;

/**
 * auther: whyang
 * date: 2017/8/29
 * desc: 地址列表
 */

public class AddressListAdapter extends AbsAdapter<AddressInfo> {
    AddressContrListener listener;

    public AddressListAdapter(Context context, List<AddressInfo> data) {
        super(context, data);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_address_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final AddressInfo info = mList.get(i);
        holder.addressUserTv.setText(UtilString.nullToEmpty(info.getConsignee()));
        holder.addressPhoneTv.setText(UtilString.nullToEmpty(info.getMobile()));
        holder.addressContentTv.setText(UtilString.nullToEmpty(info.getArea_address()) + UtilString.nullToEmpty(info.getAddress()));

        holder.addressSetdefaultTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDefault(i, info.getAddress_id());
            }
        });
        holder.addressDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(i, info.getAddress_id());
            }
        });
        if (UtilString.nullToEmpty(info.getState()).equals("1")) {
            holder.addressDefaultTv.setVisibility(View.VISIBLE);
        } else {
            holder.addressDefaultTv.setVisibility(View.GONE);
        }
        return view;
    }


    public void setListener(AddressContrListener listener) {
        this.listener = listener;
    }

    public void setDefault(String addId) {
        for (int i = 0; i < getCount(); i++) {
            AddressInfo info = mList.get(i);
            if (info.getAddress_id().equals(addId)) {
                mList.get(i).setState("1");
            } else {
                mList.get(i).setState("0");
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.item_cart_id_cb)
        CheckBox itemCartIdCb;
        @Bind(R.id.address_user_tv)
        TextView addressUserTv;
        @Bind(R.id.address_default_tv)
        TextView addressDefaultTv;
        @Bind(R.id.address_phone_tv)
        TextView addressPhoneTv;
        @Bind(R.id.address_image_right)
        ImageView addressImageRight;
        @Bind(R.id.address_content_tv)
        TextView addressContentTv;
        @Bind(R.id.address_setdefault_tv)
        TextView addressSetdefaultTv;
        @Bind(R.id.address_delete_tv)
        TextView addressDeleteTv;
        @Bind(R.id.address_edit_tv)
        TextView addressEditTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface AddressContrListener {
        void onDelete(int point, String addId);

        void onDefault(int point, String addId);
    }
}
