package com.zhonghe.shiangou.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.android.volley.Request;
import com.zhonghe.lib_base.baseui.MenuPopup;
import com.zhonghe.lib_base.baseui.MenuTxt;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.data.bean.HomeData;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.ui.adapter.RecyAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.RecyclerPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/12
 * desc:
 */

public class PointUnlineDetailActivity extends BaseTopActivity implements RecyAdapter.addCartListener, RecyclerPresenter.OnRecyRefreshListener {
    @Bind(R.id.id_recyclerview)
    RecyclerView idRecyclerview;
    @Bind(R.id.sfl)
    SwipeRefreshLayout sfl;
    private LinearLayoutManager layoutManager;
    List<HomeCategoryInfo> categoryInfo;
    RecyclerPresenter presenter;

    @Override
    protected void initTop() {
        setNavigation(R.mipmap.common_nav_back);
        final MenuTxt mMeunManager = new MenuTxt.MenuTxtBuilder(this)
                .setTitle(R.string.common_submit)
                .setListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                }).build();
        addMenu(mMeunManager);
        MenuPopup popup = new MenuPopup.MenuPopupBuilder(this).setListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    Util.toast(mContext, "menu01");
                    return true;
                } else if (id == R.id.navigation_dashboard) {
                    Util.toast(mContext, "menu02");
                    return true;
                } else if (id == R.id.navigation_notifications) {
                    Util.toast(mContext, "menu03");
                    return true;
                }
                return false;
            }
        }).setIcon(R.mipmap.icon_zxing_black).setMenuRes(R.menu.navigation).build();
        addMenu(popup);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.layout_recyclerview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        categoryInfo = new ArrayList<>();
        layoutManager = new LinearLayoutManager(mContext);
        idRecyclerview.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

        presenter = new RecyclerPresenter.RecyclerPresenterBuilder(mContext, idRecyclerview, this).setmSFL(sfl)
                .setListener(this)
                .build();
//        getHomeData();
    }

    void getHomeData() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getHomeData(mContext, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                HomeData homeData = (HomeData) obj;
                categoryInfo = homeData.getCategoryX();
                presenter.setData(categoryInfo);
                presenter.LoadMoreComplet();
                presenter.RefreshComplet();
            }
        });
        addRequest(request);
    }

    @Override
    public void OnAddCart(String goods_id) {

    }

    @Override
    public void OnRefresh() {
        getHomeData();
    }

    @Override
    public void OnLoadMore() {
        getHomeData();
    }
}
