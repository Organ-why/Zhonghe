package com.zhonghe.shiangou.ui.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CharPay;
import com.zhonghe.shiangou.data.bean.OrderInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.adapter.OrderAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseFullFragment;
import com.zhonghe.shiangou.ui.dialog.PayDialog;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.utile.UtilPay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public class OrderUnpayFragment extends BaseFullFragment implements PullToRefreshBase.OnRefreshListener2<ListView>, OrderAdapter.orderOperateListener, PayDialog.payClickListener {
    @BindView(R.id.id_unpay_listview)
    PullToRefreshListView idDefaultListview;
    String orderBy;
    OrderAdapter adapter;
    int curpage = 1;
    int cursize = 10;
    List<OrderInfo> newData;
    private PayDialog payDialog;

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_unpay);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void initTop() {

    }

    @Override
    protected void initViews() {
        idDefaultListview.setOnRefreshListener(this);
        adapter = new OrderAdapter(mActivity, newData);
        adapter.setOrderOpreatListener(this);
        idDefaultListview.setAdapter(adapter);
        Bundle bundle = getArguments();
        orderBy = bundle.getString(CstProject.KEY.ORDERBY);
        getGoodsList();
    }

    void getGoodsList() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getOrderList(mActivity, orderBy, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                Util.toast(mActivity, error);
                setWaitingDialog(false);
                idDefaultListview.onRefreshComplete();
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                List<OrderInfo> list = (List<OrderInfo>) obj;
                if (curpage == 1) {
                    adapter.setList(list);
                } else {
                    adapter.addList(list);
                }
                idDefaultListview.onRefreshComplete();
                curpage++;
            }
        });
        addRequest(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        curpage = 1;
        getGoodsList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        getGoodsList();
    }

    @Override
    public void OnNegative1(int position, String orderType) {
        //退货
    }

    @Override
    public void OnNegative2(int position, String orderType) {
//        ,1(代付款),2(代发货),3(待收货)，4（待评论）
        OrderInfo item = adapter.getItem(position);
        if (orderType.equals("1")) {
            CancelOrder(position, item.getOrder_sn(), orderType);
        } else if (orderType.equals("2") || orderType.equals("3")) {
            //查看物流
        } else {
            //删除订单
            DelOrder(position, item.getOrder_sn(), orderType);
        }
    }

    String Order_sn = "";

    @Override
    public void OnPositive(int position, String orderType) {
        OrderInfo item = adapter.getItem(position);
        if (orderType.equals("1")) {
            //支付
            Order_sn = item.getOrder_sn();
            if (payDialog == null) {
                payDialog = new PayDialog(mActivity, this);
            }
            payDialog.showAtLocation(idDefaultListview, Gravity.BOTTOM, 0, 0);
        } else if (orderType.equals("2")) {
            //退货
        } else if (orderType.equals("3")) {
            //确认订单
        }
    }

    //取消订单
    void CancelOrder(final int position, String orderId, String orderType) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getCancelOrder(mActivity, orderId, new ResultListener() {
            @Override
            public void onFail(String error) {
                Util.toast(mActivity, error);
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                adapter.removeItem(position);
                setWaitingDialog(false);
            }
        });
        addRequest(request);
    }

    //删除订单
    void DelOrder(final int position, String orderId, String orderType) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getDelOrder(mActivity, orderId, new ResultListener() {
            @Override
            public void onFail(String error) {
                Util.toast(mActivity, error);
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                adapter.removeItem(position);
                setWaitingDialog(false);
            }
        });
        addRequest(request);
    }

    //支付凭证
    void goPay(String orderCode, final int payType) {
        setWaitingDialog(true);

        Request<?> request = HttpUtil.getPay(mActivity, orderCode, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mActivity, error);
            }

            @Override
            public void onSuccess(Object obj) {
                CharPay charPay = (CharPay) obj;
                CharPay.DataBean orderMsg = charPay.getData();
                setWaitingDialog(false);
                UtilPay.sartPay(mActivity, payType, orderMsg);
            }
        });
        addRequest(request);
    }

    @Override
    public void OnClickPay(int payType) {
        goPay(Order_sn, payType);
    }
}
