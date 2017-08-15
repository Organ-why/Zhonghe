package com.zhonghe.shiangou.ui.listener;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.ui.adapter.HomeCategoryTitleAdapter;
import com.zhonghe.shiangou.ui.widget.HorizontalListView;

import java.util.List;


/**
 * Date: 2017/8/7.
 * Author: whyang
 */
public class HomeScrollListener implements View.OnTouchListener {
    List<HomeCategoryInfo> itemListData;
    ScrollView mScrollView;
    Context context;
    HorizontalListView titleListView;
    int ScrollY;

    public HomeScrollListener(ScrollView mScrollView, Context context, HorizontalListView titleList, List<HomeCategoryInfo> itemList, int scrollY) {
        this.mScrollView = mScrollView;
        this.context = context;
        this.titleListView = titleList;
        this.itemListData = itemList;
        ScrollY = scrollY;
    }

    public void ListenScroll() {
        mScrollView.setOnTouchListener(this);
        HomeCategoryTitleAdapter adapter = new HomeCategoryTitleAdapter(context, itemListData);
        titleListView.setAdapter(adapter);


        titleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mScrollView.scrollTo(0, Utilm.dip2px(context, 300)+ScrollY*position);

                ObjectAnimator xTranslate = ObjectAnimator.ofInt(mScrollView, "scrollX", 0);
                ObjectAnimator yTranslate = ObjectAnimator.ofInt(mScrollView, "scrollY", Utilm.dip2px(context, 300) + ScrollY * position);

                AnimatorSet animators = new AnimatorSet();
                animators.setDuration(1000L);
                animators.playTogether(xTranslate, yTranslate);
                animators.start();
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d("HomeScrollListener", "getY..." + v.getY() + "...getScrollY..." + v.getScrollY());
                //175+50+75
                if (v.getScrollY() > Utilm.dip2px(context, 300)) {
                    titleListView.setVisibility(View.VISIBLE);
                } else {
                    titleListView.setVisibility(View.GONE);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
