package com.zhonghe.shiangou.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.ui.adapter.DynamicBannerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by puti on 2017/5/11.
 */

public class DynamicBanner implements ViewPager.OnPageChangeListener {
    private ViewPager vpAdvertise;
    private Context context;
    private LayoutInflater inflater;
    private int timeDratioin;//多长时间切换一次pager

    List<? extends BaseBannerInfo> advertiseArray;
    List<View> views;
    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    Timer timer;
    TimerTask task;
    int count;

    public DynamicBanner(Context context, LayoutInflater inflater, int timeDratioin) {
        this.context = context;
        this.inflater = inflater;
        this.timeDratioin = timeDratioin;
    }

    private Handler runHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    int currentPage = (Integer) msg.obj;
                    setCurrentDot(currentPage);
                    vpAdvertise.setCurrentItem(currentPage);
                    break;
            }
        }

        ;
    };

    public View initView(final List<? extends BaseBannerInfo> advertiseArray) {
        this.advertiseArray = advertiseArray;
//        return super.initView(advertiseArray);
        View view = inflater.inflate(R.layout.advertisement_board, null);
        vpAdvertise = (ViewPager) view.findViewById(R.id.vpAdvertise);
        vpAdvertise.setOnPageChangeListener(this);
        views = new ArrayList<View>();
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);//获取轮播图片的点的parent，用于动态添加要显示的点
        for (int i = 0; i < advertiseArray.size(); i++) {
//            views.add(inflater.inflate(R.layout.advertisement_item_fitxy, null));
            if (advertiseArray.size() > 1) {
                ll.addView(inflater.inflate(R.layout.advertisement_board_dot, null));
            }
        }
        initDots(view, ll);

        DynamicBannerAdapter adapter = new DynamicBannerAdapter(context, views, advertiseArray);
        vpAdvertise.setAdapter(adapter);
//        vpAdvertise.setCurrentItem(Integer.MAX_VALUE / 2);
        vpAdvertise.setOffscreenPageLimit(5);
//        vpAdvertise.setCurrentItem(10000*advertiseArray.size());
        if (advertiseArray.size() > 1) {
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    int currentPage = count;
                    count++;
                    Message msg = Message.obtain();
                    msg.what = 0x01;
                    msg.obj = currentPage;
                    runHandler.sendMessage(msg);
                }
            };
            timer.schedule(task, 0, timeDratioin);
        }
        return view;
    }

    public void initDots(View view, LinearLayout ll) {
        if (advertiseArray.size() > 1) {
            dots = new ImageView[advertiseArray.size()];
            // 循环取得小点图片
            for (int i = 0; i < advertiseArray.size(); i++) {
                dots[i] = (ImageView) ll.getChildAt(i);
                dots[i].setEnabled(true);// 都设为灰色
            }

            currentIndex = 0;
            dots[currentIndex].setEnabled(false);// 设置为黄色，即选中状态
        }
    }

    private void setCurrentDot(int position) {
        position %= advertiseArray.size();
        if (position < 0 || position > advertiseArray.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        count = position;
        setCurrentDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
//                handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                break;
            case ViewPager.SCROLL_STATE_IDLE:
//                handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                break;
            default:
                break;
        }
    }
}
