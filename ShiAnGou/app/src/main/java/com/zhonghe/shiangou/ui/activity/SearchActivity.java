package com.zhonghe.shiangou.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.baseui.BaseSystemActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.FlowLayout1;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/8/25
 * desc:搜索
 */

public class SearchActivity extends BaseSystemActivity {
    @Bind(R.id.id_search_hot_fl)
    FlowLayout1 idSearchHotFl;
    @Bind(R.id.id_search_history_fl)
    FlowLayout1 idSearchHistoryFl;
    @Bind(R.id.id_search_cancel)
    TextView idSearchCancel;
    @Bind(R.id.id_category_title_tv)
    EditText idCategoryTitleTv;
    @Bind(R.id.id_serach_hot_ll)
    LinearLayout idSerachHotLl;
    @Bind(R.id.id_serach_history_ll)
    LinearLayout idSerachHistoryLl;

    List<GoodsInfo> searchHot;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_search);
        setTitleView(R.layout.layout_search_top);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        searchHot = new ArrayList<>();
        getSearchHot();

        idCategoryTitleTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = idCategoryTitleTv.getText().toString().trim();
                    if (UtilString.isBlank(s)) {
                        Utilm.toast(mContext, R.string.search_search_tip);
                        return true;
                    }
                    //如果actionId是搜索的id，则进行下一步的操作
                    ProDispatcher.goGoodsListActivity(mContext, "", s);
                    return false;
                }
                return true;
            }
        });
    }

    @OnClick(R.id.id_search_cancel)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void initSystem() {

    }

    void getSearchHot() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getSearchHost(this, new ResultListener() {
            @Override
            public void onFail(String error) {
                Utilm.toast(mContext, error);
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                searchHot = (List<GoodsInfo>) obj;
                setWaitingDialog(false);
                setSearchHot(searchHot);
            }
        });
        addRequest(request);
    }

    void setSearchHot(List<GoodsInfo> data) {
        if (data != null && data.size() > 0) {
            for (final GoodsInfo info : data) {
                TextView button = new TextView(this);
                button.setPadding(25, 10, 25, 10);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(this, null);
                params.setMargins(10, 0, 10, 0);
                button.setLayoutParams(params);
                button.setBackgroundResource(R.drawable.common_bg_gray);
                button.setText(info.getGoods_name());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProDispatcher.goGoodsListActivity(mContext, info.getCat_id(), "");
                    }
                });
                idSearchHotFl.addView(button);
            }
        } else {
            idSerachHotLl.setVisibility(View.GONE);
        }
    }


}
