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
import com.zhonghe.shiangou.ui.widget.xlistview.XListViewFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/19
 * desc:
 */

public abstract class BaseRecPrensenter {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSFL;
    //    private RecyAdapter dataAdapter;
    protected RecyHeaderAdapter headerAdapter;
    private RecyclerPresenter.OnRecyRefreshListener listener;
    private XListViewFooter footer;
    private View header;
    protected Context mContext;
    private boolean isLoading;//当前是否是加载ing
    private boolean isLoadMore = true;
    private boolean isRefresh = true;
    //当前滚动的position下面最小的items的临界值
    private int visibleThreshold = 0;

    public BaseRecPrensenter(RecyclerPresenterBuilder builder) {
        this.mRecyclerView = builder.mRecyclerView;
        this.mSFL = builder.mSFL;
        this.listener = builder.listener;
        this.header = builder.header;
        this.mContext = builder.mContext;


        initViews();
        initData();
    }

    /**
     * 初始化headerAdapter,因为在 initData里要用了
     */
    protected abstract void initViews();

    private void initData() {
        //添加 header 与加载更多 footer
        if (headerAdapter != null) {
            if (header != null)
                headerAdapter.addHeaderView(header);
            // init footer view
            footer = new XListViewFooter(mContext);
            headerAdapter.addFootView(footer);
            mRecyclerView.setAdapter(headerAdapter);
        }
        if (listener != null) {
            //刷新数据操作
            mSFL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (isRefresh && !isLoading) {
                        isLoading = true;
                        listener.OnRefresh();
                    } else {
                        mSFL.setRefreshing(false);
                    }
                }
            });
            //加载更多
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    listener.OnRecScroll(recyclerView, dx, dy);
                    if (isLoadMore) {
                        int totalItemCount = linearLayoutManager.getItemCount();
                        int itemCount = headerAdapter.getRealItemCount();
                        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                        Log.d("test", "totalItemCount =" + itemCount + "-----" + "lastVisibleItemPosition =" + lastVisibleItemPosition);
                        if (itemCount > 0 && !isLoading && lastVisibleItemPosition + 1 == itemCount) {
//                    if (itemCount > 0 && !isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                            //此时是刷新状态
                            isLoading = true;
                            LoadMore();
                        }
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


    /**
     * 数据加载listener
     */
    public interface OnRecyRefreshListener {
        /**
         * refresh
         */
        void OnRefresh();

        /**
         * recyclerView 的滑动
         *
         * @param recyclerView
         * @param dx
         * @param dy
         */
        void OnRecScroll(RecyclerView recyclerView, int dx, int dy);

        /**
         * 加载更多
         */
        void OnLoadMore();
    }

    public void setIsRefresh(boolean b) {
        isRefresh = b;
    }

    public void setIsLoadMore(boolean b) {
        isLoadMore = b;
    }

    //加载更多
    public synchronized void LoadMore() {
        listener.OnLoadMore();
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

    public abstract void setData(List data);

    public abstract void addData(List data);

    /**
     * builder
     */
    public static class RecyclerPresenterBuilder {
        private RecyclerView mRecyclerView;
        private SwipeRefreshLayout mSFL;
        private OnRecyRefreshListener listener;
        private View header;
        private Context mContext;

        public RecyclerPresenterBuilder(Context context, RecyclerView mRecyclerView) {
            this.mRecyclerView = mRecyclerView;
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
