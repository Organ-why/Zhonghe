package com.zhonghe.shiangou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CategoryChild;
import com.zhonghe.shiangou.data.bean.CategoryParent;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.adapter.CategoryChildAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Date: 2017/7/4.
 * Author: whyang
 */
public class CategoryFragment extends BaseTopFragment {
    @Bind(R.id.rl_title)
    RelativeLayout rl_bg;
    @Bind(R.id.title_user_ivb)
    ImageButton titleUserIvb;
    @Bind(R.id.title_msg_ivb)
    ImageButton titleMsgIvb;
    @Bind(R.id.id_category_title_tv)
    TextView idCategoryTitleTv;
    @Bind(R.id.id_category_radiogroup)
    RadioGroup idCategoryRadiogroup;
    @Bind(R.id.id_category_child_title_tv)
    TextView idCategoryChildTitleTv;
    @Bind(R.id.id_category_child_gv)
    GridView idCategoryChildGv;

    List<CategoryParent> categoryParents;
    List<CategoryChild> categoryChild;
    Map<String, List<CategoryChild>> childMap;
    CategoryChildAdapter childAdapter;

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_category);
        ButterKnife.bind(this, getView());

    }

    @Override
    protected void initViews() {
        rl_bg.setBackgroundResource(R.color.res_color_white);
        titleUserIvb.setImageResource(R.mipmap.icon_user_black);
        titleMsgIvb.setImageResource(R.mipmap.icon_msg_black);
        idCategoryTitleTv.setBackgroundResource(R.drawable.circle_search_gray_bg);

        categoryParents = new ArrayList<>();
        categoryChild = new ArrayList<>();
        childMap = new HashMap<>();
        childAdapter = new CategoryChildAdapter(mActivity);
        idCategoryChildGv.setAdapter(childAdapter);
        idCategoryChildGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProDispatcher.goGoodsListActivity(mActivity, childAdapter.getData().get(i).getCat_id(), "");
            }
        });

        getCategoryParent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void initTop() {
    }

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_custom_top);
    }

    //获取父级分类
    private void getCategoryParent() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getCategoryParent(mActivity, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Utilm.toast(mActivity, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                categoryParents = (List<CategoryParent>) obj;
                setCategoryParent();
            }
        });
        addRequest(request);
    }

    //设置父级分类
    void setCategoryParent() {
//        idCategoryRadiogroup.addView();
//        getCategoryChild(categoryParents.get(0).getCat_id());
        for (int i = 0; i < categoryParents.size(); i++) {
            RadioButton tempButton = (RadioButton) LayoutInflater.from(mActivity).inflate(R.layout.layout_radiobt_category, null);
            tempButton.setText(categoryParents.get(i).getCat_name());
            final int finalI = i;
            tempButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) getCategoryChild(categoryParents.get(finalI).getCat_id());
                }
            });
            idCategoryRadiogroup.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        idCategoryRadiogroup.check(idCategoryRadiogroup.getChildAt(0).getId());
    }

    //获取子级分类
    void getCategoryChild(final String childId) {
        List<CategoryChild> list = childMap.get(childId);
        categoryChild.clear();
        if (list != null) {
            categoryChild.addAll(list);
            childAdapter.setList(categoryChild);
        } else {
            Request<?> request = HttpUtil.getCategoryChild(mActivity, childId, new ResultListener() {
                @Override
                public void onFail(String error) {
                    Utilm.toast(mActivity, error);
                    childAdapter.setList(categoryChild);
                }

                @Override
                public void onSuccess(Object obj) {
                    List<CategoryChild> chillist = (List<CategoryChild>) obj;
                    categoryChild.addAll(chillist);
                    childMap.put(childId, chillist);
                    childAdapter.setList(chillist);
                }
            });
            addRequest(request);
        }
    }

}
