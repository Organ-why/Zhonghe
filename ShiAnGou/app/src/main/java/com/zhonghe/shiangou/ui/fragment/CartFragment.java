package com.zhonghe.shiangou.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.shiangou.ui.baseui.BaseTopFragment;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.ui.adapter.CartProdcutAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wang
 * @date 16/1/28 上午11:33
 * @desc 购物车页面
 */
public class CartFragment extends BaseTopFragment implements PullToRefreshBase.OnRefreshListener2<ListView> {
    @Bind(R.id.cart_id_lv)
    PullToRefreshListView cartIdLv;
    //全选按钮
    @Bind(R.id.cart_id_all_cb)
    CheckBox cartIdAllCb;
    //合计
    @Bind(R.id.cart_id_totalpay_tv)
    TextView cartIdTotalpayTv;
    //满减价格
//    @Bind(R.id.cart_id_total_tv)
//    TextView cartIdTotalTv;
    //确认下单
    @Bind(R.id.cart_id_tobuy_bt)
    Button cartIdTobuyBt;
    //确认订单与选择----layout
    @Bind(R.id.cart_id_pay_rl)
    RelativeLayout cartIdPayRl;
    //    title
    @Bind(R.id.custom_top_id_title)
    TextView customTopIdTitle;
    //编辑
    @Bind(R.id.custom_top_id_right_tv)
    TextView customTopIdRightTv;
    @Bind(R.id.cart_id_total_ll)
    LinearLayout cartIdTotalLl;
    @Bind(R.id.cart_id_del_bt)
    Button cartIdDelBt;

    //无商品提示drawable
    Drawable mEmptyDraw;

    private int mPage = 1;
    private int mSize = 10;
    private CartProdcutAdapter adapter;
    //删除
//    CartDeleteDialog cartDeleteDialog;

    //是否编辑状态
    private Boolean isEdit = false;

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_cart);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, getView());
//        //注册关联门店广播
//        registerAction(CstOuer.BROADCAST_ACTION.CART_SELECT_CHANGE);
//        //登录变化
//        registerAction(CstOuer.BROADCAST_ACTION.LOGINED_ACTION);
//        registerAction(CstOuer.BROADCAST_ACTION.UNLOGINED_ACTION);
//        //购物车内商品变化
//        registerAction(CstOuer.BROADCAST_ACTION.CART_CHANGE_CHANGE);
//        //生成订单后重新加载数据
//        registerAction(CstOuer.BROADCAST_ACTION.CREATE_ORDER_ACTION);

//        mEmptyDraw = ContextCompat.getDrawable(mActivity, R.mipmap.common_empty_cart);
//        mEmptyDraw.setBounds(0, 0, mEmptyDraw.getMinimumWidth(), mEmptyDraw.getMinimumHeight());

        adapter = new CartProdcutAdapter(mActivity);
        cartIdLv.setAdapter(adapter);
        cartIdLv.setOnRefreshListener(this);
        customTopIdTitle.setText(R.string.common_cart_title);
        customTopIdRightTv.setText(R.string.common_cart_edit);
        customTopIdRightTv.setVisibility(View.GONE);

        cartIdAllCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                adapter.selectAll(cb.isChecked());
            }
        });

        getCartProList(mPage);
//        setOnRetryListener(new OnRetryListener() {
//            @Override
//            public void onRetry() {
//                getCartProList(mPage);
//            }
//        });
    }

    @Override
    protected void initTop() {

    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        setStatusBarColor(mActivity.getResources().getColor(R.color.common_white));
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        setStatusBarColor(mActivity.getResources().getColor(R.color.common_white));
//    }

//    @Override
//    protected long initOptions() {
//        return UIOptions.UI_OPTIONS_SYSTEMBAR | UIOptions.UI_OPTIONS_APPBAR_CUSTIOM | UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
//    }

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_cart_top);
    }

    /**
     * 购物车商品列表
     *
     * @param page
     */
    private void getCartProList(final Integer page) {
//        if (OuerApplication.mUser == null) {
//            cartIdLv.onRefreshComplete();
//            return;
//        }
//        AgnettyFuture future = XTrade.Buyer.cartGetList(OuerApplication.mUser.getUserId(), page, mSize, new OuerFutureListener(getActivity()) {
//            @Override
//            public void onComplete(AgnettyResult result) {
//                super.onComplete(result);
//                //获取成功
//                setLoading(false);
//                cartIdLv.onRefreshComplete();
//                CartPO datas = (CartPO) result.getAttach();
//                if (datas != null && UtilList.isNotEmpty(datas.getData())) {
//                    if (page == 1) {
//                        //如果刷新数据--置0
////                        cartIdTotalpayTv.setText("0");
////                        cartIdTotalTv.setText("0");
//                        adapter.setList(datas.getData());
//                    } else {
//                        adapter.addList(datas.getData());
//                    }
//                    isSelectAll();
//                    cartIdPayRl.setVisibility(View.VISIBLE);
//                    customTopIdRightTv.setVisibility(View.VISIBLE);
//                    mPage++;
////                    if (UtilList.getCount(datas.getData()) < CstOuer.PAGE.DEFAULT_SIZE) {
////                        cartIdLv.setMode(PullToRefreshBase.Mode.DISABLED);
////                    }
//                } else {
//                    if (mPage == 1) {
//                        //无商品
//                        cartIdPayRl.setVisibility(View.GONE);
//                        customTopIdRightTv.setVisibility(View.GONE);
//                        setEmptyText(R.string.common_card_empty, mEmptyDraw);
//                    }
////                    cartIdLv.setMode(PullToRefreshBase.Mode.DISABLED);
//                }
//            }
//
//            @Override
//            public void onException(AgnettyResult result) {
//                super.onException(result);
//                //接口失败重试
//                cartIdLv.onRefreshComplete();
//                setRetry(true);
//                if (result != null && result.getException() != null
//                        && UtilString.isNotBlank(result.getException().getMessage())) {
//                    UtilOuer.toast(mActivity, result.getException().getMessage());
//                    return;
//                }
//            }
//
//            @Override
//            public void onNetUnavaiable(AgnettyResult result) {
//                super.onNetUnavaiable(result);
//                //网络异常重试
//                setRetry(true);
//                cartIdLv.onRefreshComplete();
//            }
//
//            @Override
//            public void onStart(AgnettyResult result) {
//                super.onStart(result);
//                //开始
//                if (mPage == 1)
//                    setLoading(true);
//
//            }
//        });
//        attachDestroyFutures(future);
    }


    /**
     * 删除选择商品
     */
    public void deleteCartPro(ArrayList<String> mSelectIds) {
//        AgnettyFuture future = XTrade.Buyer.cartDelete(mSelectIds, new OuerFutureListener(mActivity) {
//            @Override
//            public void onNetUnavaiable(AgnettyResult result) {
//                super.onNetUnavaiable(result);
//            }
//
//            @Override
//            public void onComplete(AgnettyResult result) {
//                super.onComplete(result);
//                Boolean isDel = (Boolean) result.getAttach();
//                if (isDel) {
//                    adapter.delSelect();
//                    cartIdAllCb.setChecked(false);
//                    if (adapter.getCount() == 0) {
//                        setEmptyText(R.string.common_card_empty, mEmptyDraw);
//                    }
//                } else {
//                    UtilOuer.toast(mContext, "删除失败");
//                }
//            }
//
//            @Override
//            public void onStart(AgnettyResult result) {
//                super.onStart(result);
//            }
//
//            @Override
//            public void onException(AgnettyResult result) {
//                super.onException(result);
//                //接口失败重试
//                if (result != null && result.getException() != null
//                        && UtilString.isNotBlank(result.getException().getMessage())) {
//                    UtilOuer.toast(mContext, result.getException().getMessage());
//                    return;
//                } else {
//                    UtilOuer.toast(mContext, "删除失败");
//                }
//            }
//        });
//        attachDestroyFutures(future);
    }

    /**
     * 下单
     */
    @OnClick(R.id.cart_id_tobuy_bt)
    void goDetail() {

        if (adapter.getSelectIds().size() > 0) {
//            if (adapter.isConfirmAmount())
//                OuerDispatcher.goConfirmOrderActivity(mActivity, adapter.getSelectIds(), 0, 0);
        } else {
//            UtilOuer.toast(mActivity, R.string.common_cart_noselect);
        }
    }

    /**
     * 编辑购物车
     */
    @OnClick(R.id.custom_top_id_right_tv)
    protected void editCart() {
        //编辑时取消所有选中
        adapter.selectAll(false);
        cartIdAllCb.setChecked(false);
        if (!isEdit) {
            isEdit = true;
            customTopIdRightTv.setText(R.string.common_cart_editover);
            cartIdTotalLl.setVisibility(View.INVISIBLE);
            cartIdDelBt.setVisibility(View.VISIBLE);
            cartIdTobuyBt.setVisibility(View.GONE);
        } else {
            isEdit = false;
            customTopIdRightTv.setText(R.string.common_cart_edit);
            cartIdTotalLl.setVisibility(View.VISIBLE);
            cartIdDelBt.setVisibility(View.GONE);
            cartIdTobuyBt.setVisibility(View.VISIBLE);
        }
        adapter.setIsEdit(isEdit);
    }

    /**
     * 删除选择商品
     */
    @OnClick(R.id.cart_id_del_bt)
    protected void delCart() {
        if (adapter.getSelectIds().size() > 0) {
//            if (cartDeleteDialog == null) {
//                cartDeleteDialog = new CartDeleteDialog(mActivity, new BaseDialog.OnLeftListener() {
//                    @Override
//                    public void onLeft() {
//                        deleteCartPro(adapter.getSelectIds());
//                        cartDeleteDialog.cancel();
//
//                    }
//                });
//            }
//            cartDeleteDialog.show();
        } else {
//            UtilOuer.toast(mActivity, R.string.common_cart_noselect);
        }

    }

    /*
    * 计算选择商品价格
    * */
    private void chooseCalc(List<String> ids) {
//        AgnettyFuture future = XTrade.Buyer.cartChooseCalc(ids, new OuerFutureListener(mActivity) {
//            @Override
//            public void onNetUnavaiable(AgnettyResult result) {
//                super.onNetUnavaiable(result);
//            }
//
//            @Override
//            public void onStart(AgnettyResult result) {
//                super.onStart(result);
//            }
//
//            @Override
//            public void onComplete(AgnettyResult result) {
//                super.onComplete(result);
//                CartFee cartFee = (CartFee) result.getAttach();
//                if (cartFee != null) {
//                    cartIdTotalpayTv.setText(String.valueOf(cartFee.getFinalFee()));
//                }else{
//                    cartIdTotalpayTv.setText("0");
//                }
//                if (adapter.getSelectIds().size() <= 0 && !cartIdAllCb.isChecked()) {
//                    cartIdTotalpayTv.setText("0");
//                }
//            }
//
//            @Override
//            public void onException(AgnettyResult result) {
//                super.onException(result);
//            }
//        });
//        attachDestroyFutures(future);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        mPage = 1;
        getCartProList(mPage);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (adapter.getCount() < mSize) {
            mPage = 1;
        }
        getCartProList(mPage);
    }

    @Override
    protected void onReceive(Intent intent) {
        super.onReceive(intent);
        String action = intent.getAction();
//        //选中的商品有变化
//        if (CstOuer.BROADCAST_ACTION.CART_SELECT_CHANGE.equals(action)) {
//            //接口计算
//            if (adapter.getSelectIds().size() > 0 ) {
//                chooseCalc(adapter.getSelectIds());
//            } else {
//                cartIdTotalpayTv.setText("0");
////                cartIdTotalTv.setText("0");
//            }
//            isSelectAll();
//            //本地计算
////            List<CartItemBO> selectDatas = adapter.getSelectDatas();
////            BigDecimal totalpay = BigDecimal.ZERO;
////            for (CartItemBO item : selectDatas) {
////                totalpay = totalpay.add(item.getSku().getPrice().multiply(BigDecimal.valueOf(item.getAmount())));
////            }
////            cartIdTotalpayTv.setText(String.valueOf(totalpay));
////            cartIdTotalTv.setText(String.valueOf(totalpay));
//        }
//        if (CstOuer.BROADCAST_ACTION.LOGINED_ACTION.equals(action)) {
//            mPage = 1;
//            getCartProList(mPage);
//        }
//        //购物车内商品变化
//        if (CstOuer.BROADCAST_ACTION.CART_CHANGE_CHANGE.equals(action)) {
//            adapter.clearAll();
//            adapter.selectAll(false);
//            hideEmptyText();
//            mPage = 1;
//            getCartProList(mPage);
//
//        }
//        if (CstOuer.BROADCAST_ACTION.UNLOGINED_ACTION.equals(action)) {
//            adapter.clearAll();
//            adapter.selectAll(false);
//            cartIdTotalpayTv.setText("0");
////            cartIdTotalTv.setText("0");
//            cartIdAllCb.setChecked(false);
//            customTopIdRightTv.setVisibility(View.GONE);
//            cartIdPayRl.setVisibility(View.GONE);
//            mPage = 1;
//        }
//        //生成订单后
//        if (CstOuer.BROADCAST_ACTION.CREATE_ORDER_ACTION.equals(action)) {
//            cartIdAllCb.setChecked(false);
//            adapter.clearAll();
//            adapter.selectAll(false);
//            hideEmptyText();
//            mPage = 1;
//            getCartProList(mPage);
//        }

    }

    /**
     * 当前是否选中了所有可用商品
     */
    private void isSelectAll() {
        //            选中商品改变--取消全选
        if (!isEdit) {
            if (adapter.getSelectDatas().size() != adapter.getmAvailableDatas().size()) {
                cartIdAllCb.setChecked(false);
            } else if (adapter.getSelectDatas().size() > 0) {
                cartIdAllCb.setChecked(true);
            }
        } else {
            if (adapter.getSelectDatas().size() != adapter.getCount()) {
                cartIdAllCb.setChecked(false);
            } else if (adapter.getSelectDatas().size() > 0) {
                cartIdAllCb.setChecked(true);
            }
        }
    }

}
