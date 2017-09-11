package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.data.bean.ItemStatus;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/8
 * desc:
 */

public class RecyAdapter extends RecyclerView.Adapter {
    Context context;
    List<HomeCategoryInfo> mData;
    addCartListener addCartListener;

    public RecyAdapter(Context context, List<HomeCategoryInfo> mData, addCartListener addCartListener) {
        this.context = context;
        this.mData = mData;
        this.addCartListener = addCartListener;
    }

    public void setData(List<HomeCategoryInfo> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    //    onCreateViewHolder() 为每个项目创建 ViewHolder    通过type使用不同的holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == ItemStatus.VIEW_TYPE_GROUPITEM) {

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .layout_home_category_item, parent, false);

            viewHolder = new GroupItemViewHolder(v);

        } else if (viewType == ItemStatus.VIEW_TYPE_SUBITEM) {

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .item_home_category_item, parent, false);
            viewHolder = new SubItemViewHolder(v);
        }

        return viewHolder;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    //    onBindViewHolder() 处理每个 item
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemStatus itemStatus = getItemStatusByPosition(position);

//        final DataTree<Album, Track> dt = dataTrees.get(itemStatus.getGroupItemIndex());
        HomeCategoryInfo childIndo = mData.get(itemStatus.getGroupItemIndex());
        if (itemStatus.getViewType() == ItemStatus.VIEW_TYPE_GROUPITEM) {
            final GroupItemViewHolder groupItemVh = (GroupItemViewHolder) holder;
            //加载groupItem，处理groupItem控件
            ProjectApplication.mImageLoader.loadImage(groupItemVh.idHomeCategoryItemTitleIv, childIndo.getCat_images());
//            groupItemVh.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int groupItemIndex = itemStatus.getGroupItemIndex();
//                }
//            });

        } else if (itemStatus.getViewType() == ItemStatus.VIEW_TYPE_SUBITEM) {
            final GoodsInfo goodsInfo = childIndo.getList().get(itemStatus.getSubItemIndex());
            SubItemViewHolder subItemVh = (SubItemViewHolder) holder;

            //加载subItem，处理subItem控件
            subItemVh.idHomeCategoryItemCartIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addCartListener.OnAddCart(goodsInfo.getGoods_id());
                }
            });
            ProjectApplication.mImageLoader.loadImage(subItemVh.idHomeCategoryItemIv, goodsInfo.getGoods_img());
            subItemVh.idHomeCategoryItemTitleTv.setText(UtilString.nullToEmpty(goodsInfo.getGoods_name()));
            subItemVh.idHomeCategoryItemPriceTv.setText(UtilString.nullToEmpty(Util.formatPrice(goodsInfo.getShop_price())));
            subItemVh.idHomeCategoryItemOldpriceTv.setText(UtilString.nullToEmpty(Util.formatPrice(goodsInfo.getMarket_price())));
            subItemVh.idHomeCategoryItemOldpriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
            subItemVh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击subItem处理
                    ProDispatcher.goGoodsDetailActivity(context, goodsInfo.getGoods_id());
                }
            });
        }
    }

    //    getItemCount() 获取 item 总数
    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (UtilList.getCount(mData) == 0)
            return itemCount;
        for (int i = 0; i < mData.size(); i++) {
            itemCount += UtilList.getCount(mData.get(i).getList()) + 1;
        }
        return itemCount;
    }

    //    getItemViewType() 在 onCreateViewHolder 前调用，返回 item 类型
    @Override
    public int getItemViewType(int position) {
        return getItemStatusByPosition(position).getViewType();
//        return super.getItemViewType(position);
    }


    public static class GroupItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.id_home_category_item_title_iv)
        SimpleDraweeView idHomeCategoryItemTitleIv;

        public GroupItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.id_home_category_item_iv)
        SimpleDraweeView idHomeCategoryItemIv;
        @Bind(R.id.id_home_category_item_title_tv)
        TextView idHomeCategoryItemTitleTv;
        @Bind(R.id.id_home_category_item_price_tv)
        TextView idHomeCategoryItemPriceTv;
        @Bind(R.id.id_home_category_item_oldprice_tv)
        TextView idHomeCategoryItemOldpriceTv;
        @Bind(R.id.id_home_category_item_cart_iv)
        ImageView idHomeCategoryItemCartIv;

        public SubItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        String name = holder.getClass().getName();
        UtilLog.d("复用。。。。。" + name);
    }

    /**
     * 根据position来判断item 类型
     *
     * @param position
     * @return
     */
    private ItemStatus getItemStatusByPosition(int position) {

        ItemStatus itemStatus = new ItemStatus();

        int count = 0;    //计算groupItemIndex = i 时，position最大值
        int i = 0;

        //轮询 groupItem 的开关状态
        for (i = 0; i < mData.size(); i++) {
            //pos刚好等于计数时，item为groupItem
            if (count == position) {
                itemStatus.setViewType(ItemStatus.VIEW_TYPE_GROUPITEM);
                itemStatus.setGroupItemIndex(i);
                break;
                //pos大于计数时，item为groupItem(i - 1)中的某个subItem
            } else if (count > position) {
                itemStatus.setViewType(ItemStatus.VIEW_TYPE_SUBITEM);
                itemStatus.setGroupItemIndex(i - 1);
                itemStatus.setSubItemIndex(position - (count - UtilList.getCount(mData.get(i - 1).getList())));
                break;
            }
            //无论groupItem状态是开或者关，它在列表中都会存在，所有count++
            count++;
            //当轮询到的groupItem的状态为“开”的话，count需要加上该groupItem下面的子项目数目
//            if (groupItemStatus.get(i)) {

            count += UtilList.getCount(mData.get(i).getList());
//            }
        }
        //简单地处理当轮询到最后一项groupItem的时候
        if (i >= mData.size()) {
            itemStatus.setGroupItemIndex(i - 1);
            itemStatus.setViewType(ItemStatus.VIEW_TYPE_SUBITEM);
            itemStatus.setSubItemIndex(position - (count - UtilList.getCount(mData.get(i - 1).getList())));
        }

        return itemStatus;
    }


    public interface addCartListener {
        void OnAddCart(String goods_id);
    }


}
