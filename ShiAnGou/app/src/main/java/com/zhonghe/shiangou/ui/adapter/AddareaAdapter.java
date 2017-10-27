package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.AddressArea;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/29
 * desc:地区选择
 */

public class AddareaAdapter extends AbsAdapter<AddressArea> {

    public AddareaAdapter(Context context, List<AddressArea> addlist) {
        super(context, addlist);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_addaread_select, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        AddressArea area = mList.get(i);
        holder.idAddareaTv.setText(area.getAreaname().trim());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.id_addarea_tv)
        TextView idAddareaTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
