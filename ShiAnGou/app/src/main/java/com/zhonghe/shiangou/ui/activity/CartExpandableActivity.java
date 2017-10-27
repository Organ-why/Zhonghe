package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CartGoods;
import com.zhonghe.shiangou.data.bean.CartItemGroupBO;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.CartExpandableListener;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/8/8.
 * Author: whyang
 * 购物车
 */
public class CartExpandableActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2<ExpandableListView> {


    int curpage = 1;
    int cursize = 10;
    @BindView(R.id.cart_id_expandlv)
    PullToRefreshExpandableListView cartIdLv;
    @BindView(R.id.cart_id_all_cb)
    CheckBox cartIdAllCb;
    @BindView(R.id.cart_id_totalpay_tv)
    TextView cartIdTotalpayTv;
    @BindView(R.id.cart_id_total_tv)
    TextView cartIdTotalTv;
    @BindView(R.id.cart_id_total_ll)
    LinearLayout cartIdTotalLl;
    @BindView(R.id.cart_id_tobuy_bt)
    Button cartIdTobuyBt;
    @BindView(R.id.cart_id_del_bt)
    Button cartIdDelBt;
    @BindView(R.id.cart_id_pay_rl)
    RelativeLayout cartIdPayRl;
    @BindView(R.id.custom_top_id_back)
    ImageView customTopIdBack;
    @BindView(R.id.custom_top_id_title)
    TextView customTopIdTitle;
    @BindView(R.id.custom_top_id_right_tv)
    TextView customTopIdRightTv;


    List<CartItemGroupBO> data;
    CartExpandableListener listener;

    @Override
    protected void initTop() {
    }

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_cart_top);
    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_SYSTEMBAR | UIOptions.UI_OPTIONS_APPBAR_CUSTIOM | UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_cart_expandable);
        ButterKnife.bind(this);
        registerAction(CstProject.BROADCAST_ACTION.LOGOUT_ACTION);
        registerAction(CstProject.BROADCAST_ACTION.CART_ADD_ACTION);
        registerAction(CstProject.BROADCAST_ACTION.CART_DEL_ACTION);
    }

    @Override
    protected void initViews() {


        data = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            CartItemGroupBO cartItemGroupBO = new CartItemGroupBO();
//            ArrayList<CartItemBO> itemBOs = new ArrayList<>();
//            for (int j = 0; j < 3; j++) {
//                CartItemBO itemBO = new CartItemBO();
//                itemBOs.add(itemBO);
//            }
//            cartItemGroupBO.setChildPro(itemBOs);
//            data.add(cartItemGroupBO);
//        }
        listener = new CartExpandableListener(mContext, cartIdLv.getRefreshableView()) {
            @Override
            public void checkGroup(int groupPosition, boolean isChecked) {
                super.checkGroup(groupPosition, isChecked);
                if (!isChecked)
                    cartIdAllCb.setChecked(false);
                setTotal(getSelectGoodsList());
            }

            @Override
            public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
                super.checkChild(groupPosition, childPosition, isChecked);
                if (!isChecked)
                    cartIdAllCb.setChecked(false);
                setTotal(getSelectGoodsList());
            }

            @Override
            public void onSeletAll() {
                cartIdAllCb.setChecked(true);
            }

            @Override
            public void setTotal(double total) {
                setTotalNum(total);
            }
        };
//        listener.addmData(data);

        customTopIdTitle.setText(R.string.common_cart_title);
        customTopIdRightTv.setText(R.string.common_cart_edit);

        cartIdLv.setOnRefreshListener(this);
        getCartData();
    }


    int isEditFlag = 0;

    void getCartData() {
        if (ProjectApplication.mUser == null) {
            ProDispatcher.goLoginActivity(mContext);
            cartIdLv.onRefreshComplete();
            return;
        }
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getCartList(mContext, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
                cartIdLv.onRefreshComplete();
            }

            @Override
            public void onSuccess(Object obj) {
                cartIdLv.onRefreshComplete();
                setWaitingDialog(false);
                List<CartGoods> info = (List<CartGoods>) obj;


                if (info.size() > 0) {
                    CartItemGroupBO cartItemGroupBO = new CartItemGroupBO();
                    cartItemGroupBO.setChildPro(info);
//                    data.add(cartItemGroupBO);
                    List<CartItemGroupBO> newData = new ArrayList<CartItemGroupBO>();
                    newData.add(cartItemGroupBO);
                    if (curpage == 1) {
                        listener.setmData(newData);
                    } else {
                        listener.addmData(newData);
                    }
                    curpage++;
                }
            }
        });
        addRequest(request);
    }


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

    void setTotalNum(double total) {
        cartIdTotalpayTv.setText(Util.formatPrice(total));
//        = listener.getSelectGoods();
//        for
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.cart_id_tobuy_bt, R.id.cart_id_del_bt})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.cart_id_tobuy_bt:
                ArrayList<String> list = listener.getConfirmGoods();
                if (UtilList.isEmpty(list)) {
                    Util.toast(mContext, R.string.common_cart_noselect);
                    break;
                }
                ProDispatcher.goConfirmOrderActivity(mContext, list, 0, "", 0);
                break;
            case R.id.cart_id_del_bt:
                List<String> delist = listener.getSelectGoods();
                if (UtilList.isEmpty(delist)) {
                    Util.toast(mContext, R.string.common_cart_noselect);
                } else {
                    setWaitingDialog(true);
                    Request<?> request = HttpUtil.getDeleteCart(mContext, delist, new ResultListener() {
                        @Override
                        public void onFail(String error) {
                            setWaitingDialog(false);
                        }

                        @Override
                        public void onSuccess(Object obj) {
                            setWaitingDialog(false);
                            listener.deleteGoods();
                            curpage = 1;
                            getCartData();
                            setTotalNum(0);
                        }
                    });
                    addRequest(request);
                }
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        curpage = 1;
        getCartData();
        setTotalNum(0);
        cartIdAllCb.setChecked(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        getCartData();
    }

    @Override
    protected void onReceive(Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case CstProject.BROADCAST_ACTION.LOGOUT_ACTION:
                listener.removeAll();
                break;
            case CstProject.BROADCAST_ACTION.CART_ADD_ACTION:
                curpage = 1;
                getCartData();
                break;
            case CstProject.BROADCAST_ACTION.CART_DEL_ACTION:
                curpage = 1;
                getCartData();
                break;
        }
    }

}
