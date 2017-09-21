package com.zhonghe.shiangou.ui.activity;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
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
import com.zhonghe.shiangou.ui.adapter.UnlineShopAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.widget.PopListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private PopListPresenter typePresenter;
    private PopListPresenter orderByPresenter;
    private TextView typeTv;
    private TextView orderByTv;

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
        typeTv = (TextView) floatView.findViewById(R.id.id_type_tv);
        orderByTv = (TextView) floatView.findViewById(R.id.id_orderby_tv);
        floatLl.addView(floatView);
        idDefaultListview.setOnRefreshListener(this);
        UnlineShopAdapter adapter = new UnlineShopAdapter(mContext, null);
        idDefaultListview.setAdapter(adapter);

        final List<String> list = new ArrayList<>();
        list.add("水果1");
        list.add("水果2");
        list.add("水果3");
        list.add("水果4");
        list.add("水果5");
        typePresenter = new PopListPresenter(mContext, list, floatView, typeIv, new PopListPresenter.OnPopItemClickListener() {
            @Override
            public void OnClickItem(String t, int position) {
                typeTv.setText(t);
//                ProDispatcher.sendChangeCategoryBroadcast(mContext, list.get(position));
            }
        });
        orderByPresenter = new PopListPresenter(mContext, list, floatView, orderbyIv, new PopListPresenter.OnPopItemClickListener() {
            @Override
            public void OnClickItem(String t, int position) {
                orderByTv.setText(t);
//                ProDispatcher.sendChangeCategoryBroadcast(mContext, list.get(position));
            }
        });
        ListView listView = idDefaultListview.getRefreshableView();
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
                typePresenter.show();
                break;
            case R.id.id_orderby_ll:
                orderByPresenter.show();
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
