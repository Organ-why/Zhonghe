package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CartItemGroupBO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by a on 2017/8/9.
 */

public class CartExpandableAdapter extends BaseExpandableListAdapter {
    List<CartItemGroupBO> data;
    Context mContext;
    LayoutInflater inflater;
    ModifyCountInterface modifyCountInterface;
    CheckInterface mCheckInterface;

    public CartExpandableAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    public CartExpandableAdapter(List<CartItemGroupBO> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public void setCheckInterface(CheckInterface mCheckInterface) {
        this.mCheckInterface = mCheckInterface;
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         * @param isChecked     组元素选中与否
         */
        public void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param isChecked     子元素选中与否
         */
        public void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param groupPosition 组元素位置
         * @param childPosition 子元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

//        /**
//         * 删除子item
//         *
//         * @param groupPosition
//         * @param childPosition
//         */
//        public void childDelete(int groupPosition, int childPosition);
    }


    //组个数
    @Override
    public int getGroupCount() {
        return data.size();
    }

    // 子view个数
    @Override
    public int getChildrenCount(int i) {
        return data.get(i).getChildPro().size();
    }


    @Override
    public Object getGroup(int i) {
        return data.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return data.get(i).getChildPro().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return data.get(i).getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int i, final boolean b, View view, ViewGroup viewGroup) {
        final GroupViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_cart_pro_group, null);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }
//        mCheckInterface
        holder.isGroupCheck.setChecked(data.get(i).isCheck());
        holder.isGroupCheck.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCheckInterface.checkGroup(i, holder.isGroupCheck.isChecked());
                    }
                }
        );
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, final boolean b, View view, ViewGroup viewGroup) {
        final ChildViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.layout_cart_sku_content, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        holder.itemCartIdCb.setChecked(data.get(i).getChildPro().get(i1).isCheck());
        holder.itemCartIdCb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCheckInterface.checkChild(i, i1, holder.itemCartIdCb.isChecked());
                    }
                }

        );
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    //Group
    static class GroupViewHolder {
        //        @Bind(R.id.id_cart_pro_group_ck)
//        CheckBox idCartProGroupCk;
        @Bind(R.id.id_cart_pro_group_rl)
        RelativeLayout idCartProGroupRl;
        @Bind(R.id.id_cart_pro_group_ck)
        CheckBox isGroupCheck;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @Bind(R.id.cart_id_bottom)
        View cartIdBottom;
        @Bind(R.id.item_cart_id_cb)
        CheckBox itemCartIdCb;
        @Bind(R.id.item_cart_id_status)
        TextView itemCartIdStatus;
        @Bind(R.id.item_cart_id_iv)
        SimpleDraweeView itemCartIdIv;
        @Bind(R.id.item_cart_id_shortage_tv)
        TextView itemCartIdShortageTv;
        @Bind(R.id.item_cart_id_rl)
        RelativeLayout itemCartIdRl;
        @Bind(R.id.item_cart_id_title_tv)
        TextView itemCartIdTitleTv;
        @Bind(R.id.item_cart_id_sku_tv)
        TextView itemCartIdSkuTv;
        @Bind(R.id.layout_id_reduce_iv)
        ImageView layoutIdReduceIv;
        @Bind(R.id.layout_id_number_tv)
        TextView layoutIdNumberTv;
        @Bind(R.id.layout_id_add_iv)
        ImageView layoutIdAddIv;
        @Bind(R.id.layout_layout_id_changenum)
        LinearLayout layoutLayoutIdChangenum;
        @Bind(R.id.item_cart_id_status_tv)
        TextView itemCartIdStatusTv;
        @Bind(R.id.item_cart_id_totalpay_tv)
        TextView itemCartIdTotalpayTv;
        @Bind(R.id.item_cart_id_oldprice_tv)
        TextView itemCartIdOldpriceTv;
        @Bind(R.id.item_cart_id_price_ll)
        LinearLayout itemCartIdPriceLl;
        @Bind(R.id.order_sku_rl)
        RelativeLayout orderSkuRl;
        @Bind(R.id.order_sku_fullcut_tv)
        TextView orderSkuFullcutTv;
        @Bind(R.id.order_sku_ll_fullcut)
        LinearLayout orderSkuLlFullcut;
        @Bind(R.id.order_sku_all_fullcut)
        LinearLayout orderSkuAllFullcut;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
