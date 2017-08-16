package com.zhonghe.lib_base.baseui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.zhonghe.lib_base.utils.UtilList;

import java.util.ArrayList;
import java.util.List;


/**
 * @desc   : 适配器抽象类
 */
public abstract class AbsAdapter<T> extends BaseAdapter {
    protected List<T> mList;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public AbsAdapter(Context context) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mList = new ArrayList<T>();
    }
    
    public AbsAdapter(Context context, List<T> datas) {
    	 this.mContext = context;
         this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         this.mList = datas != null ? datas : new ArrayList<T>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int pos) {
        return mList.size() > pos ? mList.get(pos) : null;
    }
    
    @Override
    public long getItemId(int pos) {
        return pos;
    }

    public List<T> getData() {
        return mList;
    }

    public void setList(List<T> items) {
        this.mList = items != null ? items : new ArrayList<T>();
        notifyDataSetChanged();
    }


    public void addItem(int position, T item) {
        if (item != null) {
            mList.add(position, item);
            notifyDataSetChanged();
        }
    }
    
    public void removeItem(T item) {
    	if (item != null) {
            mList.remove(item);
            notifyDataSetChanged();
        }
    }

    public void addItem(T item) {
        if (item != null) {
            mList.add(item);
            notifyDataSetChanged();
        }
    }

    public void updateItem(int pos, T item) {
        if (mList.size() > pos && item != null) {
            mList.set(pos, item);
            notifyDataSetChanged();
        }
    }
   
    public void addList(int position, List<T> items) {
        if (UtilList.isNotEmpty(items)) {
            mList.addAll(position, items);
            notifyDataSetChanged();
        }
    }

    public void addList(List<T> items) {
        if (UtilList.isNotEmpty(items)) {
            mList.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void clearAll() {
        if (UtilList.isNotEmpty(mList)){
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
