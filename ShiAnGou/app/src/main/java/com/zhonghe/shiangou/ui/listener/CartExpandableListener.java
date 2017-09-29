package com.zhonghe.shiangou.ui.listener;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.zhonghe.shiangou.data.bean.CartGoods;
import com.zhonghe.shiangou.data.bean.CartItemGroupBO;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.adapter.CartExpandableAdapter;
import com.zhonghe.shiangou.ui.fragment.CartExpandableFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2017/8/10.
 */

public class CartExpandableListener implements CartExpandableAdapter.CheckInterface, CartExpandableAdapter.ModifyCountInterface {
    Context mContext;
    ExpandableListView mListView;
    List<CartItemGroupBO> mData;
    List<CartItemGroupBO> mSelectData;
    CartExpandableAdapter adapter;

    public CartExpandableListener(Context mContext, ExpandableListView mListView) {
        this.mContext = mContext;
        this.mData = new ArrayList<>();
        this.mSelectData = new ArrayList<>();
        this.mListView = mListView;


        adapter = new CartExpandableAdapter(this.mData, this.mContext);
        adapter.setCheckInterface(this);
        adapter.setModifyCountInterface(this);
        this.mListView.setAdapter(adapter);
        this.mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }


    public void setmListView(ExpandableListView mListView) {
        this.mListView = mListView;
    }

    public void setmData(List<CartItemGroupBO> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        reFreshData();
    }

    public void addmData(List<CartItemGroupBO> mData) {
        this.mData.get(0).getChildPro().addAll(mData.get(0).getChildPro());
        reFreshData();
    }

    public void removeAll() {
        adapter.removeAll();
    }

    //刷新数据
    private void reFreshData() {
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            this.mListView.expandGroup(i);
        }
        adapter.notifyDataSetChanged();
        mListView.expandGroup(0);
    }

    public void selectAll(boolean isAllSelect) {
        for (int i = 0; i < mData.size(); i++) {
            checkGroup(i, isAllSelect);
        }
    }

    public void onSeletAll() {

    }

    int selectGroupCount = 0;

    //选中一组商品
    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        mData.get(groupPosition).setCheck(isChecked);
        for (int i = 0; i < mData.get(groupPosition).getChildPro().size(); i++) {
            mData.get(groupPosition).getChildPro().get(i).setCheck(isChecked);

        }
        if (isChecked) {
            selectGroupCount++;
            if (selectGroupCount == mData.size()) onSeletAll();
        } else {
            selectGroupCount--;
        }

        reFreshData();
    }

    //选中一件
    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        mData.get(groupPosition).getChildPro().get(childPosition).setCheck(isChecked);
        int childSelectCount = 0;
        for (CartGoods itemBo : mData.get(groupPosition).getChildPro()) {
            if (!itemBo.isCheck()) {
                break;
            }
            childSelectCount++;
        }
        if (childSelectCount == mData.get(groupPosition).getChildPro().size()) {
//            mData.get(groupPosition).setCheck(true);
            checkGroup(groupPosition, true);
        } else {
            mData.get(groupPosition).setCheck(false);
        }
        reFreshData();
    }

    //商品数量减少
    @Override
    public void doIncrease(final int groupPosition, final int childPosition, View showCountView, boolean isChecked) {
        Integer amount = Integer.valueOf(mData.get(groupPosition).getChildPro().get(childPosition).getGoods_count());
        amount--;
        if (amount == 0)
            return;
        final Integer finalAmount = amount;
        Request<?> request = HttpUtil.getChangeCart(mContext, mData.get(groupPosition).getChildPro().get(childPosition).getGoods_id(), amount, new ResultListener() {
            @Override
            public void onFail(String error) {

            }

            @Override
            public void onSuccess(Object obj) {
                mData.get(groupPosition).getChildPro().get(childPosition).setGoods_count(String.valueOf(finalAmount));
                reFreshData();
                setTotal(getSelectGoodsList());
            }
        });
        ProjectApplication.proReqestQueue.addRequest(request, mContext);

    }

    //商品数量增加
    @Override
    public void doDecrease(final int groupPosition, final int childPosition, View showCountView, boolean isChecked) {
        Integer amount = Integer.valueOf(mData.get(groupPosition).getChildPro().get(childPosition).getGoods_count());
        amount++;
        final Integer finalAmount = amount;
        Request<?> request = HttpUtil.getChangeCart(mContext, mData.get(groupPosition).getChildPro().get(childPosition).getGoods_id(), amount, new ResultListener() {
            @Override
            public void onFail(String error) {

            }

            @Override
            public void onSuccess(Object obj) {
                mData.get(groupPosition).getChildPro().get(childPosition).setGoods_count(String.valueOf(finalAmount));
                reFreshData();
                setTotal(getSelectGoodsList());
            }
        });
        ProjectApplication.proReqestQueue.addRequest(request, mContext);

    }

//    //本地删除
//    @Override
//    public void childDelete(int groupPosition, int childPosition) {
//
//    }

    //编辑
    public void groupEdit(Boolean IsEdtor) {

    }

    ;

    //删除
    public void deleteGoods() {
        if (mSelectData.size() > 0) {
            mData.removeAll(mSelectData);
//            List<CartGoods> childPro = mData.get(0).getChildPro();
//            childPro.removeAll(mSelectData.get(0).getChildPro());
            adapter.notifyDataSetChanged();
        }
    }

    ;
    private ArrayList<String> selectlist;
    private ArrayList<String> confirmlist;

    //选中商品购物车id
    public ArrayList<String> getConfirmGoods() {
        if (confirmlist == null) confirmlist = new ArrayList<>();
        confirmlist.clear();
        for (CartItemGroupBO parentinfo : mData) {
            for (CartGoods childinfo : parentinfo.getChildPro()) {
                if (childinfo.isCheck()) {
                    confirmlist.add(childinfo.getRec_id());
                }
            }
        }
        return confirmlist;
    }

    //获取选中商品id
    public ArrayList<String> getSelectGoods() {
        if (selectlist == null) selectlist = new ArrayList<>();
        selectlist.clear();
        mSelectData.clear();
        for (CartItemGroupBO parentinfo : mData) {
            List<CartGoods> childlist = new ArrayList<>();
            for (CartGoods childinfo : parentinfo.getChildPro()) {
                if (childinfo.isCheck()) {
                    childlist.add(childinfo);
                    selectlist.add(childinfo.getGoods_id());
                }
            }
            if (childlist.size() > 0) {
                CartItemGroupBO selectinfo = new CartItemGroupBO();
                selectinfo = parentinfo;
                selectinfo.setChildPro(childlist);
                mSelectData.add(selectinfo);
            }

        }

        return selectlist;
    }
    //获取选中商品
    public  double  getSelectGoodsList() {
        if (selectlist == null) selectlist = new ArrayList<>();
        selectlist.clear();
        mSelectData.clear();
        List<CartGoods> childlist = new ArrayList<>();
        double total = 0;
        for (CartItemGroupBO parentinfo : mData) {
            for (CartGoods childinfo : parentinfo.getChildPro()) {
                if (childinfo.isCheck()) {
                    childlist.add(childinfo);
                    selectlist.add(childinfo.getGoods_id());
                    total+=childinfo.getShop_price()*Integer.valueOf(childinfo.getGoods_count());
                }
            }

        }

        return total;
    }

    public void setTotal(double total){

    };

}
