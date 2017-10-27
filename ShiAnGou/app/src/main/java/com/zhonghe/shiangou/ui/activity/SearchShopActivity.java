package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.baidu.location.BDLocation;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.ShopInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.adapter.UnlineShopAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchShopActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2 {

    @BindView(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;
    @BindView(R.id.id_search_cancel)
    TextView idSearchCancel;
    @BindView(R.id.id_category_title_tv)
    EditText idCategoryTitleTv;
    private String keywords;
    private int curpage = 1;
    private List<ShopInfo> shopList;
    private AbsAdapter<ShopInfo> adapter;
    private BDLocation mLocation;

    @Override
    protected void initTop() {

    }

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_search_top);
    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_SYSTEMBAR | UIOptions.UI_OPTIONS_APPBAR_CUSTIOM | UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        mLocation = ProjectApplication.mLocation;
        shopList = new ArrayList<>();
        adapter = new UnlineShopAdapter(mContext, shopList);
        idDefaultListview.setAdapter(adapter);
        idDefaultListview.setOnRefreshListener(this);
        idCategoryTitleTv.setHint(getString(R.string.search_search_shop_tip));
        idCategoryTitleTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = idCategoryTitleTv.getText().toString().trim();
                    if (UtilString.isBlank(s)) {
                        Util.toast(mContext, R.string.search_search_shop_tip);
                        return true;
                    }
                    keywords = s;
                    getShopList(mLocation.getLongitude(), mLocation.getLatitude(), s);
                    return false;
                }
                return true;
            }
        });


    }

    void getShopList(double lon, double lat, String keywords) {
        if (UtilString.isEmpty(keywords)) {
            idDefaultListview.onRefreshComplete();
            return;
        }
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUnlineShopSearch(mContext, keywords, lat, lon, curpage, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }
            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                idDefaultListview.onRefreshComplete();
                shopList = (List<ShopInfo>) obj;
                if (curpage == 1) {
                    adapter.clearAll();
                    adapter.setList(shopList);
                } else {
                    adapter.addList(shopList);
                }
                if (shopList.size() > 0) {
                    curpage++;
                }
            }
        });
        addRequest(request);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        curpage = 1;
        getShopList(mLocation.getLongitude(), mLocation.getLatitude(), keywords);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getShopList(mLocation.getLongitude(), mLocation.getLatitude(), keywords);
    }


    @OnClick(R.id.id_search_cancel)
    public void onViewClicked() {
        finish();
    }
}
