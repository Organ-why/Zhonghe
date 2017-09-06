package com.zhonghe.shiangou.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.AddressInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.ui.adapter.AddressListAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManageActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2<ListView>, AddressListAdapter.AddressContrListener {


    @Bind(R.id.id_address_listview)
    PullToRefreshListView idAddressListview;
    @Bind(R.id.id_addressmng_add_bt)
    Button idAddressmngAddBt;

    List<AddressInfo> mData;
    AddressListAdapter adapter;
    int curSize = 10;
    int curPage = 1;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_address_manage);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setNavigation(R.mipmap.common_nav_back);
        setTitle(R.string.address_titlemng_text);
    }

    @Override
    protected void initViews() {
        mData = new ArrayList<>();
        adapter = new AddressListAdapter(this, mData);
        idAddressListview.setAdapter(adapter);
        adapter.setListener(this);
        idAddressListview.setOnRefreshListener(this);
        idAddressListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent();
//                intent.putExtra(CstProject.KEY.DATA,adapter.getItem(i));
//                setResult(RESULT_OK,intent);
//                finish();
            }
        });
        getAddress();
    }

    @OnClick(R.id.id_addressmng_add_bt)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_addressmng_add_bt:
                ProDispatcher.goChangeAddressActivity(this,null);
                break;
        }
    }

    @OnClick(R.id.id_addressmng_add_bt)
    public void onViewClicked() {
    }


    void getAddress() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getAddressList(mContext, curPage, curSize, new ResultListener() {
            @Override
            public void onFail(String error) {
                Util.toast(mContext, error);
                setWaitingDialog(false);
                idAddressListview.onRefreshComplete();
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                idAddressListview.onRefreshComplete();
                List<AddressInfo> list = (List<AddressInfo>) obj;
                if (curPage == 1) {
                    adapter.setList(list);
                } else {
                    adapter.addList(list);
                }
                curPage++;
            }
        });
        addRequest(request);

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        curPage = 1;
        getAddress();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        getAddress();
    }

    @Override
    public void onDelete(final int point, String addId) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getAddressDelete(mContext, addId, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                adapter.removeItem(point);
            }
        });
        addRequest(request);
    }

    @Override
    public void onDefault(int point, final String addId) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getAddressSetDefault(mContext, addId, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                adapter.setDefault(addId);
            }
        });
        addRequest(request);
    }
}
