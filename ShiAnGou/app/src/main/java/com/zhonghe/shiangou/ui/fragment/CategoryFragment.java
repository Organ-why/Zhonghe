package com.zhonghe.shiangou.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CategoryChild;
import com.zhonghe.shiangou.data.bean.CategoryParent;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.activity.CustomScanActivity;
import com.zhonghe.shiangou.ui.adapter.CategoryChildAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        titleMsgIvb.setImageResource(R.mipmap.icon_zxing_black);
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
        setOnRetryListener(new OnRetryListener() {
            @Override
            public void onRetry() {
                setRetry(false);
                getCategoryParent();
            }
        });
        getCategoryParent();
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
                setRetry(true);
                Util.toast(mActivity, error);
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
                    Util.toast(mActivity, error);
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


    @OnClick({R.id.title_user_ivb, R.id.title_msg_ivb, R.id.id_category_title_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_user_ivb:
                ProDispatcher.sendMainTabBroadcast(mActivity, 3);
                break;
            case R.id.title_msg_ivb:
                //需要以带返回结果的方式启动扫描界面
//                new IntentIntegrator(mActivity)
//                        .setOrientationLocked(false)
//                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
//                        .initiateScan(); // 初始化扫描
                int REQUEST_CODE = 0x0000c0de; // Only use bottom 16 bits
                Intent intent = new Intent(mActivity, CustomScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.id_category_title_tv:
                ProDispatcher.goSearchActivity(mActivity);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(mActivity, "内容为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mActivity, "扫描成功", Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                UtilLog.d(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
