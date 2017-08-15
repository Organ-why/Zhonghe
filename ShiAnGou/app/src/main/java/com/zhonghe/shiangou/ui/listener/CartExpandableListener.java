package com.zhonghe.shiangou.ui.listener;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;

import com.zhonghe.shiangou.data.bean.CartItemBO;
import com.zhonghe.shiangou.data.bean.CartItemGroupBO;
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
        this.mData = mData;
        reFreshData();
    }

    public void addmData(List<CartItemGroupBO> mData) {
        this.mData.addAll(mData);
        reFreshData();
    }

    //刷新数据
    private void reFreshData() {
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            this.mListView.expandGroup(i);
        }
        adapter.notifyDataSetChanged();
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
        for (CartItemBO itemBo : mData.get(groupPosition).getChildPro()
                ) {
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
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        Integer amount = mData.get(groupPosition).getChildPro().get(childPosition).getAmount();
        amount--;
        mData.get(groupPosition).getChildPro().get(childPosition).setAmount(amount);
        reFreshData();
    }

    //商品数量增加
    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        Integer amount = mData.get(groupPosition).getChildPro().get(childPosition).getAmount();
        amount++;
        mData.get(groupPosition).getChildPro().get(childPosition).setAmount(amount);
        reFreshData();
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
    public void deleteData() {

    }

    ;


}
