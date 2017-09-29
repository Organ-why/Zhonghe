package com.zhonghe.shiangou.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Date: 2017/8/7.
 * Author: whyang
 * banner 控件器
 */

public class BannerPresenter implements ViewPager.OnPageChangeListener {
    private ViewPager vpAdvertise;
    private Context context;
    private LayoutInflater inflater;
    private int timeDratioin;//多长时间切换一次pager
    private boolean isPlay = true;//是否自动播放
    private boolean isPosition = true;//页码是否显示
    OnItemVpClick listener;
    List<? extends BaseBannerInfo> advertiseArray;
    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    Timer timer;
    TimerTask task;
    int count;
    private TextView mPointTv;//页码

    String strPoint = "%1$d/%2$d";

    public BannerPresenter(Context context, int timeDratioin, OnItemVpClick listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.timeDratioin = timeDratioin;
        this.listener = listener;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public BannerPresenter setPosition(boolean position) {
        isPosition = position;
        return this;
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
        View view = inflater.inflate(R.layout.advertisement_board, null);
        vpAdvertise = (ViewPager) view.findViewById(R.id.vpAdvertise);
        mPointTv = (TextView) view.findViewById(R.id.point_tv);
        if (isPosition) {
            mPointTv.setVisibility(View.VISIBLE);
        }
        vpAdvertise.setOnPageChangeListener(this);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);//获取轮播图片的点的parent，用于动态添加要显示的点
        for (int i = 0; i < advertiseArray.size(); i++) {
//            views.add(inflater.inflate(R.layout.advertisement_item_fitxy, null));
            if (advertiseArray.size() > 1) {
                ll.addView(inflater.inflate(R.layout.advertisement_board_dot, null));
            }
        }
        initDots(view, ll);

        BannerAdapter adapter = new BannerAdapter(context, advertiseArray);
        vpAdvertise.setAdapter(adapter);
        vpAdvertise.setCurrentItem(adapter.getCount() / 2);
        vpAdvertise.setOffscreenPageLimit(5);

        if (advertiseArray.size() > 1 && isPlay) {
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
        mPointTv.setText(String.format(strPoint, position+1, advertiseArray.size()));
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
//                runHandler.sendEmptyMessage(runHandler.MSG_KEEP_SILENT);
                break;
            case ViewPager.SCROLL_STATE_IDLE:
//                handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                break;
            default:
                break;
        }
    }

    public interface OnItemVpClick {
        void OnVpClick(int position);
    }

    public class BannerAdapter extends PagerAdapter {

        private Context context;
        private List<View> views;
        List<? extends BaseBannerInfo> advertiseArray;


        public BannerAdapter(Context context, List<? extends BaseBannerInfo> advertiseArray) {
            this.context = context;
            this.views = new ArrayList<>();
            this.advertiseArray = advertiseArray;
        }

        public BannerAdapter(Context context, ArrayList<? extends BaseBannerInfo> advertiseArray) {
            this.context = context;
            this.views = new ArrayList<>();
            this.advertiseArray = advertiseArray;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
//        return views.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            views.add((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int mPOSITION = position % advertiseArray.size();
            if (mPOSITION < 0) {
                mPOSITION = advertiseArray.size() + position;
            }

            View itemView;
            if (views.size() == 0) {
                itemView = LayoutInflater.from(context).inflate(R.layout.advertisement_item_fitxy, null);
            } else {
                itemView = views.remove(views.size() - 1);
            }
            SimpleDraweeView ivAdvertise = (SimpleDraweeView) itemView.findViewById(R.id.ivAdvertise);
            String images = advertiseArray.get(mPOSITION).getBanner_images();
            ProjectApplication.mImageLoader.loadImage(ivAdvertise, images);
            final int finalMPOSITION = mPOSITION;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnVpClick(finalMPOSITION);
                }
            });
            container.addView(itemView);
            return itemView;
        }

    }

}
