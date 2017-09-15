package com.zhonghe.shiangou.ui.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.adapter.UnlineGoodsAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.UnlineTypePop;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhonghe.shiangou.R.id.view;

public class PointUnlineListActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {
    @Bind(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;
    @Bind(R.id.float_ll)
    LinearLayout floatLl;
    @Bind(R.id.title_user_ivb)
    ImageButton titleUserIvb;
    @Bind(R.id.title_msg_ivb)
    ImageButton titleMsgIvb;
    @Bind(R.id.id_category_title_tv)
    TextView idCategoryTitleTv;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    ImageView typeIv;
    ImageView orderbyIv;

    private int lastIndex;
    float mRotation = 180f;

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_custom_top);
    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_SYSTEMBAR | UIOptions.UI_OPTIONS_APPBAR_CUSTIOM | UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
    }

    @Override
    protected void initTop() {

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        rlTitle.setBackgroundResource(R.color.res_color_white);
        titleUserIvb.setImageResource(R.mipmap.common_nav_back);
        titleMsgIvb.setImageResource(R.mipmap.icon_zxing_black);
        idCategoryTitleTv.setText(R.string.search_unline_tip);
        idCategoryTitleTv.setBackgroundResource(R.drawable.circle_search_gray_bg);

        View floatView = LayoutInflater.from(mContext).inflate(R.layout.layout_unline_list_float, null);
        typeIv = (ImageView) floatView.findViewById(R.id.id_type_iv);
        orderbyIv = (ImageView) floatView.findViewById(R.id.id_orderby_iv);
        floatLl.addView(floatView);
        idDefaultListview.setOnRefreshListener(this);
        UnlineGoodsAdapter adapter = new UnlineGoodsAdapter(mContext, null);
        idDefaultListview.setAdapter(adapter);
        ListView listView = idDefaultListview.getRefreshableView();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                // TODO 自动生成的方法存根
                switch (scrollState) {

                    // 滚动之前,手还在屏幕上 记录滚动前的下标
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        // view.getLastVisiblePosition()得到当前屏幕可见的第一个item在整个listview中的下标
                        lastIndex = absListView.getLastVisiblePosition();
                        break;

                    // 滚动停止
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 记录滚动停止后 记录当前item的位置
                        int scrolled = absListView.getLastVisiblePosition();
                        // 滚动后下标大于滚动前 向下滚动了
                        if (scrolled > lastIndex) {
                            // scroll = false;
                            // UIHelper.ToastMessage(VideoMain.this,"菜单收起");
                            floatLl.setVisibility(View.GONE);
                        }
                        // 向上滚动了
                        else {
                            floatLl.setVisibility(View.VISIBLE);
                            // UIHelper.ToastMessage(VideoMain.this,"菜单弹出");
                            // scroll = true;
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int i1, int i2) {
            }
        });
    }

    @OnClick({R.id.title_user_ivb, R.id.title_msg_ivb, R.id.id_category_title_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_user_ivb:
                break;
            case R.id.title_msg_ivb:
                break;
            case R.id.id_category_title_tv:
                break;
            case R.id.id_default_tv:

                break;
            case R.id.id_type_ll:
                TypeIconRotate();
                UnlineTypePop pop = new UnlineTypePop(mContext, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TypeIconRotate();
                    }
                });
                pop.showAsDropDown(floatLl);
//                pop.showAsDropDown(floatLl, 0, 0);
//                pop.showAtLocation(view, Gravity.TOP, 0, 0);
                break;
            case R.id.id_orderby_ll:
                break;
        }
    }

    void TypeIconRotate() {
//                ObjectAnimator anim = ObjectAnimator.ofFloat(typeIv, "rotation",  typeIv.getRotation(),typeIv.getRotation()+180f);
        ObjectAnimator anim = ObjectAnimator.ofFloat(typeIv, "rotation", typeIv.getRotation(), typeIv.getRotation() + mRotation);
        anim.setDuration(500);
        anim.start();
        mRotation = -mRotation;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
