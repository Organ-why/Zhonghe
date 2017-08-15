package com.zhonghe.shiangou.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CartItemBO;
import com.zhonghe.shiangou.data.bean.CartItemGroupBO;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.listener.CartExpandableListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/8/8.
 * Author: whyang
 */
public class CartExpandableFragment extends BaseTopFragment {
    @Bind(R.id.cart_id_expandlv)
    PullToRefreshExpandableListView cartIdLv;
    @Bind(R.id.cart_id_all_cb)
    CheckBox cartIdAllCb;
    @Bind(R.id.cart_id_totalpay_tv)
    TextView cartIdTotalpayTv;
    @Bind(R.id.cart_id_total_tv)
    TextView cartIdTotalTv;
    @Bind(R.id.cart_id_total_ll)
    LinearLayout cartIdTotalLl;
    @Bind(R.id.cart_id_tobuy_bt)
    Button cartIdTobuyBt;
    @Bind(R.id.cart_id_del_bt)
    Button cartIdDelBt;
    @Bind(R.id.cart_id_pay_rl)
    RelativeLayout cartIdPayRl;


    List<CartItemGroupBO> data;
    @Bind(R.id.custom_top_id_back)
    ImageView customTopIdBack;
    @Bind(R.id.custom_top_id_title)
    TextView customTopIdTitle;
    @Bind(R.id.custom_top_id_right_tv)
    TextView customTopIdRightTv;


    CartExpandableListener listener;

    @Override
    protected void initTop() {
    }

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_cart_top);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_cart_expandable);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, getView());

        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CartItemGroupBO cartItemGroupBO = new CartItemGroupBO();
            ArrayList<CartItemBO> itemBOs = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                CartItemBO itemBO = new CartItemBO();
                itemBOs.add(itemBO);
            }
            cartItemGroupBO.setChildPro(itemBOs);
            data.add(cartItemGroupBO);
        }
        listener = new CartExpandableListener(mActivity, cartIdLv.getRefreshableView()) {
            @Override
            public void checkGroup(int groupPosition, boolean isChecked) {
                super.checkGroup(groupPosition, isChecked);
                if (!isChecked)
                    cartIdAllCb.setChecked(false);
            }

            @Override
            public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
                super.checkChild(groupPosition, childPosition, isChecked);
                if (!isChecked)
                    cartIdAllCb.setChecked(false);
            }

            @Override
            public void onSeletAll() {
                cartIdAllCb.setChecked(true);
            }
        };
        listener.addmData(data);


        customTopIdTitle.setText(R.string.common_cart_title);
        customTopIdRightTv.setText(R.string.common_cart_edit);

    }


    int isEditFlag = 0;

    /**
     * 编辑购物车
     */
    @OnClick(R.id.custom_top_id_right_tv)
    protected void editCart() {
        //编辑时取消所有选中
//        cartIdAllCb.setChecked(false);
        if (isEditFlag == 0) {
            customTopIdRightTv.setText(R.string.common_cart_editover);
            cartIdTotalLl.setVisibility(View.INVISIBLE);
            cartIdDelBt.setVisibility(View.VISIBLE);
            cartIdTobuyBt.setVisibility(View.GONE);
        } else {
            customTopIdRightTv.setText(R.string.common_cart_edit);
            cartIdTotalLl.setVisibility(View.VISIBLE);
            cartIdDelBt.setVisibility(View.GONE);
            cartIdTobuyBt.setVisibility(View.VISIBLE);
        }
        isEditFlag = (isEditFlag + 1) % 2;
    }

    @OnClick(R.id.cart_id_all_cb)
    protected void selectAll() {
        listener.selectAll(cartIdAllCb.isChecked());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.cart_id_tobuy_bt, R.id.cart_id_del_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cart_id_tobuy_bt:
                ProDispatcher.goConfirmOrderActivity(mActivity);
                break;
            case R.id.cart_id_del_bt:
                break;
        }
    }
}
