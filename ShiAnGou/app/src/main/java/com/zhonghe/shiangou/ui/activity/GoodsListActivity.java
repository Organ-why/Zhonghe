package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.baseui.BaseTabActivity;
import com.zhonghe.shiangou.ui.fragment.GridViewFragment;

/**
 * auther: whyang
 * date: 2017/8/27
 * desc: 商品列表
 */

public class GoodsListActivity extends BaseTabActivity {
    @Override
    protected void initTop() {
        setTitle(R.string.pro_list_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initTabs() {
        Intent intent = getIntent();
        String keyWord = intent.getStringExtra(CstProject.KEY.KEY);
        String cat_id = intent.getStringExtra(CstProject.KEY.ID);

        Bundle bundle1 = new Bundle();
        bundle1.putString(CstProject.KEY.ID, cat_id);
        bundle1.putString(CstProject.KEY.KEY, keyWord);
        bundle1.putString(CstProject.KEY.ORDERBY, "0");
        addAppTab(R.string.pro_list_tag_default, GridViewFragment.class, bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putString(CstProject.KEY.ID, cat_id);
        bundle2.putString(CstProject.KEY.KEY, keyWord);
        bundle2.putString(CstProject.KEY.ORDERBY, "1");
        addAppTab(R.string.pro_list_tag_sales, GridViewFragment.class, bundle2);
        Bundle bundle3 = new Bundle();
        bundle3.putString(CstProject.KEY.ID, cat_id);
        bundle3.putString(CstProject.KEY.KEY, keyWord);
        bundle3.putString(CstProject.KEY.ORDERBY, "2");
        addAppTab(R.string.pro_list_tag_comment, GridViewFragment.class, bundle3);
        setAppTabIndex(0);
        setTabTextColors(ContextCompat.getColor(this, R.color.common_font_deep), ContextCompat.getColor(this, R.color.common_font_normal));
        setAppTabSelectedColor(ContextCompat.getColor(this, R.color.res_color_apptheme));
    }
}
