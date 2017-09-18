package com.zhonghe.shiangou.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.ui.adapter.RecyAdapter;
import com.zhonghe.shiangou.ui.adapter.RecyHeaderAdapter;
import com.zhonghe.shiangou.ui.adapter.ViewHolder;
import com.zhonghe.shiangou.ui.widget.xlistview.XListViewFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/18
 * desc:
 */

public class RecyclerPresenter {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSFL;
    private RecyAdapter dataAdapter;
    private RecyHeaderAdapter headerAdapter;
    private OnRecyRefreshListener listener;
    private RecyAdapter.addCartListener addCartListener;
    private XListViewFooter footer;
    private View header;
    private Context mContext;
    private boolean isLoading;//当前是否是加载ing
    //当前滚动的position下面最小的items的临界值
    private int visibleThreshold = 0;

    public RecyclerPresenter(RecyclerPresenterBuilder builder) {
        this.mRecyclerView = builder.mRecyclerView;
        this.mSFL = builder.mSFL;
        this.listener = builder.listener;
        this.header = builder.header;
        this.mContext = builder.mContext;
        this.addCartListener = builder.addCartListener;

        //添加 header 与加载更多 footer
        List<HomeCategoryInfo> mData = new ArrayList<>();
        dataAdapter = new RecyAdapter(mContext,mData,addCartListener);
        headerAdapter = new RecyHeaderAdapter<>(dataAdapter);
        if (header != null)
            headerAdapter.addHeaderView(header);
        // init footer view
        footer = new XListViewFooter(mContext);
        headerAdapter.addFootView(footer);
        mRecyclerView.setAdapter(headerAdapter);


        if (listener != null) {
            //刷新数据操作
            mSFL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    isLoading = true;
                    listener.OnRefresh();
                }
            });
            //加载更多
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int itemCount = dataAdapter.getItemCount();
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    Log.d("test", "totalItemCount =" + itemCount + "-----" + "lastVisibleItemPosition =" + lastVisibleItemPosition);
                    if (itemCount > 0 && !isLoading && lastVisibleItemPosition+1 == itemCount) {
//                    if (itemCount > 0 && !isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                        //此时是刷新状态
                        LoadMore();
                    }
                }
            });
        } else {
            mSFL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mSFL.setRefreshing(false);
                }
            });
        }
    }

    //加载更多
    public void LoadMore() {
        listener.OnLoadMore();
        isLoading = true;
        footer.show();
        footer.setState(XListViewFooter.STATE_READY);
    }

    //刷新数据完成
    public void RefreshComplet() {
        if (mSFL != null) {
            mSFL.setRefreshing(false);
            isLoading = false;
        }
    }

    //加载更多完成
    public void LoadMoreComplet() {
        isLoading = false;
        footer.hide();
    }

    public void setData( List<HomeCategoryInfo> data){
        dataAdapter.setData(data);
        headerAdapter.notifyDataSetChanged();
    }
    public void addData( List<HomeCategoryInfo> data){
        dataAdapter.setData(data);
        headerAdapter.notifyDataSetChanged();
    }

    /**
     * 数据加载listener
     */
    public static interface OnRecyRefreshListener {
        void OnRefresh();

        void OnLoadMore();
    }

    /**
     * builder
     */
    public static class RecyclerPresenterBuilder {
        private RecyclerView mRecyclerView;
        private SwipeRefreshLayout mSFL;
//        private RecyclerView.Adapter dataAdapter;
        private OnRecyRefreshListener listener;
        private RecyAdapter.addCartListener addCartListener;
        private View header;
        private Context mContext;

        public RecyclerPresenterBuilder(Context context, RecyclerView mRecyclerView,RecyAdapter.addCartListener addCartListener) {
            this.mRecyclerView = mRecyclerView;
            this.addCartListener = addCartListener;
            this.mContext = context;
        }

        public RecyclerPresenterBuilder setHeader(View header) {
            this.header = header;
            return this;
        }

        public RecyclerPresenterBuilder setmSFL(SwipeRefreshLayout mSFL) {
            this.mSFL = mSFL;
            return this;
        }

        public RecyclerPresenterBuilder setListener(OnRecyRefreshListener listener) {
            this.listener = listener;
            return this;
        }

        public RecyclerPresenter build() {
            return new RecyclerPresenter(this);
        }
    }
}
