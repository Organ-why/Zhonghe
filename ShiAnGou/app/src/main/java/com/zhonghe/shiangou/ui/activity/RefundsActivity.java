package com.zhonghe.shiangou.ui.activity;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.RefundsItemInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.ui.adapter.RefundsAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefundsActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2 {

    @BindView(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;
    private RefundsAdapter adapter;
    List<RefundsItemInfo> mList;
    int curpage = 1;
    int cursize = 10;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_default_listview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        idDefaultListview.setOnRefreshListener(this);
        adapter = new RefundsAdapter(mContext, mList);
        idDefaultListview.setAdapter(adapter);
        getRefundsList();
    }

    @Override
    protected void initTop() {
        setTitle(R.string.common_user_order_return);
        setNavigation(R.mipmap.common_nav_back);
    }

    void getRefundsList() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getRefundsList(mContext, curpage, cursize, new ResultListener() {
            @Override
            public void onFail(String error) {
                idDefaultListview.onRefreshComplete();
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                idDefaultListview.onRefreshComplete();
                setWaitingDialog(false);
                List<RefundsItemInfo> list = (List<RefundsItemInfo>) obj;

                if (curpage == 1) {
                    adapter.setList(list);
                } else {
                    adapter.addList(list);
                }
                if (list.size() > 0) {
                    curpage++;
                }
            }
        });
        addRequest(request);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        curpage = 1;
        getRefundsList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        getRefundsList();
    }
}
