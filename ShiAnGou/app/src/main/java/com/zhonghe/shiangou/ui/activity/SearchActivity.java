package com.zhonghe.shiangou.ui.activity;

import android.widget.EditText;
import android.widget.TextView;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.baseui.BaseSystemActivity;
import com.zhonghe.shiangou.ui.widget.FlowLayout1;

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

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_search);
        setTitleView(R.layout.layout_search_top);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

    }

    @OnClick(R.id.id_search_cancel)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void initSystem() {

    }
}
