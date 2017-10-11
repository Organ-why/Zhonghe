package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.baseui.BaseUIFragment;
import com.zhonghe.lib_base.baseui.FragmentTab;
import com.zhonghe.lib_base.baseui.FragmentTabAdapter;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CategoryChild;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.fragment.GridViewFragment;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.PopListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/9/19
 * desc: 首页分类商品列表
 */

public class GoodsCategoryListActivity extends BaseTopActivity {
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.id_type_tv)
    TextView idTypeTv;
    @Bind(R.id.id_type_iv)
    ImageView idTypeIv;
    @Bind(R.id.id_type_ll)
    LinearLayout idTypeLl;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private List<FragmentTab> mAppTabs;
    PopListPresenter presenter;
    List<CategoryChild> chillist;

    @Override
    protected void initTop() {
        setTitle(R.string.pro_list_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_goodscategory_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        mAppTabs = new ArrayList<>();
        Intent intent = getIntent();
        String keyWord = intent.getStringExtra(CstProject.KEY.KEY);
        String cat_id = intent.getStringExtra(CstProject.KEY.ID);


        getCategoryChild(cat_id);
    }

    void setData(String catid) {
        if (viewpager.getChildCount() > 0) return;
        Bundle bundle1 = new Bundle();
        bundle1.putString(CstProject.KEY.ID, catid);
        bundle1.putString(CstProject.KEY.KEY, "");
        bundle1.putString(CstProject.KEY.ORDERBY, "0");
        addTab(R.string.pro_list_tag_default, GridViewFragment.class, bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putString(CstProject.KEY.ID, catid);
        bundle2.putString(CstProject.KEY.KEY, "");
        bundle2.putString(CstProject.KEY.ORDERBY, "1");
        addTab(R.string.pro_list_tag_sales, GridViewFragment.class, bundle2);
        Bundle bundle3 = new Bundle();
        bundle3.putString(CstProject.KEY.ID, catid);
        bundle3.putString(CstProject.KEY.KEY, "");
        bundle3.putString(CstProject.KEY.ORDERBY, "2");
        addTab(R.string.pro_list_tag_comment, GridViewFragment.class, bundle3);

        viewpager.setAdapter(new FragmentTabAdapter(getSupportFragmentManager(),
                mAppTabs));
        tablayout.setupWithViewPager(viewpager);
        viewpager.setCurrentItem(0);
    }

    void addTab(int title, Class<? extends BaseUIFragment> cls, Bundle args) {
        BaseUIFragment fragment = (BaseUIFragment) Fragment.instantiate(this, GridViewFragment.class.getName(), args);
        FragmentTab tab = new FragmentTab();
        tab.setTitle(getString(title));
        tab.setFragment(fragment);
        mAppTabs.add(tab);
    }

    List<String> getList(List<CategoryChild> childList) {
        ArrayList<String> list = new ArrayList<>();
        for (CategoryChild childItem :
                childList) {
            list.add(childItem.getCat_name());
        }
        return list;
    }

    @OnClick(R.id.id_type_ll)
    public void onViewClicked() {
        if (presenter != null)
            presenter.show();
    }

    //获取子级分类
    void getCategoryChild(final String childId) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getCategoryChild(mContext, childId, new ResultListener() {
            @Override
            public void onFail(String error) {
                Util.toast(mContext, error);
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                final List<CategoryChild> chillist = (List<CategoryChild>) obj;
                if (UtilList.getCount(chillist) > 0) {
                    idTypeTv.setText(getList(chillist).get(0));
//                    ProDispatcher.sendChangeCategoryBroadcast(mContext, chillist.get(0).getCat_id());
                    setData(chillist.get(0).getCat_id());
                    presenter = new PopListPresenter(mContext, getList(chillist), idTypeLl, idTypeIv, new PopListPresenter.OnPopItemClickListener() {
                        @Override
                        public void OnClickItem(String t, int position) {
                            idTypeTv.setText(t);
                            ProDispatcher.sendChangeCategoryBroadcast(mContext, chillist.get(position).getCat_id());
                        }
                    });
                }
            }
        });
        addRequest(request);
    }
}
