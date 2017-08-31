package com.zhonghe.shiangou.ui.listener;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.CustomScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.ui.adapter.HomeCategoryTitleAdapter;
import com.zhonghe.shiangou.ui.widget.HorizontalListView;

import java.util.List;


/**
 * Date: 2017/8/7.
 * Author: whyang
 */
public class HomeScrollListener implements View.OnTouchListener, CustomScrollView.CustomOverScroll {
    List<HomeCategoryInfo> itemListData;
    PullToRefreshScrollView mScrollView;
    Context context;
    HorizontalListView titleListView;
    int ScrollY;

    public HomeScrollListener(PullToRefreshScrollView mScrollView, Context context, HorizontalListView titleList, List<HomeCategoryInfo> itemList, int scrollY) {
        this.mScrollView = mScrollView;
        this.context = context;
        this.titleListView = titleList;
        this.itemListData = itemList;
        ScrollY = scrollY;
    }

    public void ListenScroll() {
        mScrollView.getRefreshableView().setCustomOverScroll(this);
        mScrollView.getRefreshableView().setOnTouchListener(this);
//        mScrollView.onscroll/(this);
        HomeCategoryTitleAdapter adapter = new HomeCategoryTitleAdapter(context, itemListData);
        titleListView.setAdapter(adapter);


        titleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mScrollView.scrollTo(0, Util.dip2px(context, 300)+ScrollY*position);

                ObjectAnimator xTranslate = ObjectAnimator.ofInt(mScrollView, "scrollX", 0);
                ObjectAnimator yTranslate = ObjectAnimator.ofInt(mScrollView, "scrollY", Util.dip2px(context, 300) + ScrollY * position);

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
//                Log.d("HomeScrollListener", "getY..." + v.getY() + "...getScrollY..." + v.getScrollY());
                //175+50+75
                if (v.getScrollY() > Util.dip2px(context, 300)) {
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

    @Override
    public void onCustomScroll(int deltaX, int deltaY, int scrollX, int scrollY) {
        UtilLog.i("deltaX.." + deltaX + ".deltaY.." + deltaY + ".scrollX.." + scrollX + ".scrollY.." + scrollY + "...");
    }

//    @Override
//    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//        UtilLog.i(i + "..." + i1 + "..." + i2 + "..." + i3 + "...");
//    }
}
