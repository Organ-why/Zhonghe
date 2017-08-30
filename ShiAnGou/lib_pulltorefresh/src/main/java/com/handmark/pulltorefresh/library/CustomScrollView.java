package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * auther: whyang
 * date: 2017/8/30
 * desc:
 */

public class CustomScrollView extends ScrollView {
    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        customOverScroll.onCustomScroll(deltaX, deltaY, scrollX, scrollY);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    //自定义获取滑动
    public void setCustomOverScroll(CustomOverScroll customOverScroll) {
        this.customOverScroll = customOverScroll;
    }

    CustomOverScroll customOverScroll;

    public interface CustomOverScroll {
        void onCustomScroll(int deltaX, int deltaY, int scrollX, int scrollY);
    }
}
