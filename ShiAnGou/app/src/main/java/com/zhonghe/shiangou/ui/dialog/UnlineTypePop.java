package com.zhonghe.shiangou.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zhonghe.shiangou.R;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/15
 * desc:线下商城分类选择
 */

public class UnlineTypePop extends PopupWindow {
    private ListView listView;
    Context mContext;
    View view;
    AdapterView.OnItemClickListener listener;

    public UnlineTypePop(Context context, List<String> mData, AdapterView.OnItemClickListener listener) {
        super(context);
        this.listener = listener;
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.layout_unline_type, null);
        listView = (ListView) view.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        int color = ContextCompat.getColor(context, R.color.common_transparent);
        ColorDrawable drawable = new ColorDrawable(color);
        super.setBackgroundDrawable(drawable);
        //设置弹出窗体动画效果
        setAnimationStyle(R.style.commom_popup_fade_anim_style);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.common_push_up_in);
        view.startAnimation(anim);
    }

    @Override
    public void dismiss() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.common_push_down_out);
        view.startAnimation(anim);
        listener.onItemClick(listView, listView.getRootView(), -1, -1);
        super.dismiss();
    }


}
