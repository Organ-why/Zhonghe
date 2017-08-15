package com.zhonghe.shiangou.ui.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zhonghe.lib_base.baseui.BaseActivity;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CartItemBO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by puti on 2016/3/2.
 */
public class CartProdcutAdapter extends AbsAdapter<CartItemBO> {
    BaseActivity mContext;
    List<CartItemBO> mSelectDatas;//选中的商品列表
    List<CartItemBO> mAvailableDatas;//正常数据
    ArrayList<String> mSelectIds;
    //是否选中所有
    Boolean isSelectAll = false;
    //是否编辑
    Boolean isEdit = false;
    List<Integer> checkPosition = new ArrayList<Integer>();


    public CartProdcutAdapter(BaseActivity context) {
        super(context);
        this.mContext = context;
        mSelectDatas = new ArrayList<CartItemBO>();
        mAvailableDatas = new ArrayList<CartItemBO>();
        mSelectIds = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final CartItemBO item = getItem(position);
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_cart_sku_content, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//
//        holder.itemCartIdTitleTv.setText(item.getProduct().getName());
//        holder.itemCartIdOldpriceTv.setText(mContext.getString(R.string.common_price, UtilOuer.formatPrice(item.getSku().getPrice())));
//        holder.itemCartIdOldpriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        holder.itemCartIdTotalpayTv.setText(UtilOuer.formatPrice(item.getSku().getPrice()));
//
//        //营销活动
//        holder.itemCartIdDiscountll.setVisibility(View.GONE);
//        List<PurchaseActivitys> activitys = item.getActivitys();
//
//        if (item.getSku().getDiscountPrice() != null && item.getSku().getDiscountPrice().doubleValue() < item.getSku().getPrice().doubleValue())
//            holder.itemCartIdTotalpayTv.setText(UtilOuer.formatPrice(item.getSku().getDiscountPrice()));
//
//        if (UtilList.isNotEmpty(activitys)) {
//            for (PurchaseActivitys actitem : activitys) {
//                if (actitem.getType() == EActivitysType.FULLCUT) {
//                    holder.itemCartIdDiscountTv.setText(actitem.getActionDesc());
//                    holder.itemCartIdDiscountll.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//
//        holder.itemCartIdSkuTv.setText(item.getSku().getSpec());
//        holder.layoutIdNumberTv.setText(String.valueOf(item.getAmount()));
//        OuerApplication.mImageLoader.loadImage(holder.itemCartIdIv, item.getSku().getImgUrl() == null ? item.getProduct().getImgUrl() : item.getSku().getImgUrl());
//
//
//        //CheckBox-----点击选中时保存到mSelectDatas中
//        holder.itemCartIdCb.setTag(new Integer(item.getId()));
//        holder.itemCartIdCb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Boolean isChecked = holder.itemCartIdCb.isChecked();
//                if (isChecked) {
//                    //将选中的放入list中
//                    if (!checkPosition.contains(v.getTag())) {
//                        checkPosition.add((Integer) v.getTag());
//                        addSelect(item, isChecked);
//                    }
//                } else {
//                    //取消选中的则剔除
//                    if (checkPosition.contains(v.getTag())) {
//                        checkPosition.remove(v.getTag());
//                        addSelect(item, isChecked);
//                    }
//                }
//                //当手动更改选中，如果选中的少于可用，团置为非全选
//                if (mSelectDatas.size() < mAvailableDatas.size()) {
//                    isSelectAll = false;
//                }
//            }
//        });
//        //   //库存不足----被选中时提示
//        holder.itemCartIdShortageTv.setVisibility(View.GONE);
//        if (holder.itemCartIdCb.isChecked()) {
//            if (!isEdit && item.getSku().getAmount() < item.getAmount() && item.getSku().getAmount() != 0) {
//                holder.itemCartIdShortageTv.setVisibility(View.VISIBLE);
//            }
//        }
//        holder.itemCartIdCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (!isEdit && item.getSku().getAmount() < item.getAmount() && item.getSku().getAmount() != 0) {
//                        //库存不足-----
//                        holder.itemCartIdShortageTv.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    holder.itemCartIdShortageTv.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        //编辑下不响应进详情
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isEdit) {
//                    holder.itemCartIdCb.setChecked(!holder.itemCartIdCb.isChecked());
//                } else {
//                    OuerDispatcher.goProDetailActivity(mContext, item.getProductId());
//                }
//            }
//        };
//
//        holder.itemCartIdIv.setOnClickListener(listener);
//        holder.itemCartIdTitleTv.setOnClickListener(listener);
//        holder.itemCartIdOldpriceTv.setOnClickListener(listener);
//        holder.itemCartIdSkuTv.setOnClickListener(listener);
////        convertView.setOnClickListener(listener);
//
//
//        //正常情况下----
//        holder.layoutLayoutIdChangenum.setVisibility(View.VISIBLE);
//        holder.itemCartIdStatus.setVisibility(View.GONE);
//        holder.itemCartIdStatusTv.setVisibility(View.GONE);
//        holder.itemCartIdCb.setVisibility(View.VISIBLE);
//        convertView.setBackgroundResource(R.color.common_white);
//
//        //商品销售状态
//        String ProStatus = item.getProduct().getStatus();
//        EProStatus eProStatus = EProStatus.valueOf(EProStatus.class, ProStatus);
//        //(4,"草稿");
//        if (eProStatus.equals(EProStatus.DRAFT)) {
//        }
//        //(3,"预售"),
//        if (eProStatus.equals(EProStatus.FORSALE)) {
//        }
//        //(2,"下架"),
//        if (eProStatus.equals(EProStatus.INSTOCK)) {
//            holder.itemCartIdStatusTv.setVisibility(View.VISIBLE);
//            holder.itemCartIdStatusTv.setText(R.string.common_cart_status_instock);
//            holder.layoutLayoutIdChangenum.setVisibility(View.GONE);
//            holder.itemCartIdStatus.setVisibility(View.VISIBLE);
//            holder.itemCartIdCb.setVisibility(View.INVISIBLE);
//            convertView.setBackgroundResource(R.color.common_gray);
//
//            if (isEdit) {
//                holder.itemCartIdStatus.setVisibility(View.GONE);
//                holder.itemCartIdCb.setVisibility(View.VISIBLE);
//                convertView.setBackgroundResource(R.color.common_white);
//            }
//        } else
//            //(1,"上架"),
//            if (eProStatus.equals(EProStatus.ONSALE)) {
//                //当商品amount为0
//                if (item.getSku().getAmount() <= 0) {
//                    holder.itemCartIdStatusTv.setVisibility(View.VISIBLE);
//                    holder.itemCartIdStatusTv.setText(R.string.common_card_status_soldout);
//                    holder.layoutLayoutIdChangenum.setVisibility(View.GONE);
//                    holder.itemCartIdStatus.setVisibility(View.VISIBLE);
//                    holder.itemCartIdCb.setVisibility(View.INVISIBLE);
//
//                    convertView.setBackgroundResource(R.color.common_gray);
//                    if (isEdit) {
//                        holder.itemCartIdStatus.setVisibility(View.GONE);
//                        holder.itemCartIdCb.setVisibility(View.VISIBLE);
//                        convertView.setBackgroundResource(R.color.common_white);
//                    }
//                }
//
//            }
//        //全选-----
//        if (!isSelectAll) {
//            //如果不是全选状态-------根据list里的position显示
//            if (checkPosition != null) {
//                holder.itemCartIdCb.setChecked((checkPosition.contains(new Integer(item.getId())) ? true : false));
//            } else {
//                holder.itemCartIdCb.setChecked(false);
//            }
//        } else {
//            //如果是全选
//            if (isEdit) {//如果是编辑下全选中
//                holder.itemCartIdCb.setChecked(true);
//            } else {
//                //否则根据状态选中
//                if (eProStatus.equals(EProStatus.INSTOCK)) {
//                    holder.itemCartIdCb.setChecked(false);
//                } else if (item.getSku().getAmount() <= 0) {
//                    holder.itemCartIdCb.setChecked(false);
//                } else {
//                    holder.itemCartIdCb.setChecked(true);
//                }
//            }
//        }
//        //对商品数量修改
//        holder.layoutIdReduceIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Integer amount = item.getAmount();
//                if (amount > 1) {
//                    amount--;
//                    mEditCount(item.getId(), amount, position, holder.itemCartIdCb.isChecked());
//                }
//            }
//        });
//        holder.layoutIdAddIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Integer amount = item.getAmount();
//                amount++;
//                mEditCount(item.getId(), amount, position, holder.itemCartIdCb.isChecked());
//
//            }
//        });
//
//
//        //最后一件底部距离
//        if (position == getCount() - 1) {
//            holder.itemBottomView.setVisibility(View.VISIBLE);
//        } else {
//            holder.itemBottomView.setVisibility(View.GONE);
//        }


        return convertView;
    }


    /**
     * 数据改变时不再进行选中所有
     *
     * @param items
     */
    @Override
    public void addList(List<CartItemBO> items) {
        super.addList(items);
        isSelectAll = false;
    }

    @Override
    public void setList(List<CartItemBO> items) {
        super.setList(items);
        isSelectAll = false;
//        mSelectDatas.clear();
//        checkPosition.clear();
//        mSelectIds.clear();
    }

    /**
     * 确认订单时是否有库存不足商品
     *
     * @return
     */
    public Boolean isConfirmAmount() {
        //是否有库存不足商品
        for (CartItemBO cartitem : mSelectDatas) {
//            if (cartitem.getSku().getAmount() < cartitem.getAmount()) {
//                return false;
//            }
        }
        return true;
    }

    /**
     * 全选
     *
     * @param isSelect
     */
    public void selectAll(Boolean isSelect) {
        isSelectAll = isSelect;
        mSelectDatas.clear();
        checkPosition.clear();
        mSelectIds.clear();
        if (isSelect) {
            mSelectDatas.clear();
            if (isEdit) {
                mSelectDatas.addAll(getData());
            } else {
                mSelectDatas.addAll(getmAvailableDatas());
            }
            for (CartItemBO itemBO : mSelectDatas) {
                mSelectIds.add(String.valueOf(itemBO.getId()));
                checkPosition.add(itemBO.getId());
            }
        }
//        OuerDispatcher.sendCartSelectBroadcast(mContext);
        notifyDataSetChanged();

    }

    /**
     * 是否编辑状态
     *
     * @param edit
     */
    public void setIsEdit(Boolean edit) {
        isEdit = edit;
    }

    /**
     * 选择商品id列表
     *
     * @return
     */
    public ArrayList<String> getSelectIds() {
        return mSelectIds;
    }

    /**
     * 选择商品
     *
     * @return
     */
    public List<CartItemBO> getSelectDatas() {
        return mSelectDatas;
    }

    /**
     * 正常商品
     *
     * @return
     */
    public List<CartItemBO> getmAvailableDatas() {
        mAvailableDatas.clear();
        for (CartItemBO cartitem : getData()) {
            //商品销售状态
//            String ProStatus = cartitem.getProduct().getStatus();
//            EProStatus eProStatus = EProStatus.valueOf(EProStatus.class, ProStatus);
//            //不是下架商品并库存大于0
//            if (!eProStatus.equals(EProStatus.INSTOCK) && cartitem.getSku().getAmount() > 0) {
//                mAvailableDatas.add(cartitem);
//            }
        }
        return mAvailableDatas;
    }

    /**
     * 单选
     *
     * @param item
     * @param isAdd
     */
    public void addSelect(CartItemBO item, Boolean isAdd) {
        mSelectDatas.remove(item);
        if (isAdd) {
            mSelectDatas.add(item);
            mSelectIds.add(String.valueOf(item.getId()));
//            OuerDispatcher.sendCartSelectBroadcast(mContext);
        } else {
            mSelectDatas.remove(item);
            mSelectIds.remove(String.valueOf(item.getId()));
//            OuerDispatcher.sendCartSelectBroadcast(mContext);
        }
    }

    /**
     * 删除选择商品
     */
    public void delSelect() {
        for (CartItemBO cartitem : mSelectDatas) {
            removeItem(cartitem);
        }
        mSelectDatas.clear();
        checkPosition.clear();
//        OuerDispatcher.sendCartSelectBroadcast(mContext);
        notifyDataSetChanged();
    }

    /**
     * 修改商品数量
     *
     * @param id
     * @param amount
     * @param position
     * @param isCheck  是否选中
     */
    private void mEditCount(Integer id, Integer amount, Integer position, Boolean isCheck) {
        CartItemBO itemBO = getData().get(position);
        getData().get(position).setAmount(amount);
        if (isCheck) {
            mSelectDatas.remove(itemBO);
            mSelectDatas.add(getData().get(position));
        }
        editCount(id, amount, position);
        notifyDataSetChanged();
//        OuerDispatcher.sendCartSelectBroadcast(mContext);
    }

    /**
     * 修改商品数量
     *
     * @param id
     * @param amount
     * @param position
     */
    private void editCount(Integer id, final Integer amount, final Integer position) {
//        AgnettyFuture future = XTrade.Buyer.cartEditCount(id, amount, new OuerFutureListener(mContext) {
//            @Override
//            public void onNetUnavaiable(AgnettyResult result) {
//                super.onNetUnavaiable(result);
//            }
//
//            @Override
//            public void onStart(AgnettyResult result) {
//                super.onStart(result);
//            }
//
//            @Override
//            public void onComplete(AgnettyResult result) {
//                super.onComplete(result);
//                Boolean isEdit = (Boolean) result.getAttach();
//                if (isEdit) {
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onException(AgnettyResult result) {
//                super.onException(result);
//                //接口失败重试
////                if (result != null && result.getException() != null
////                        && UtilString.isNotBlank(result.getException().getMessage())) {
////                    UtilOuer.toast(mContext, result.getException().getMessage());
////                    return;
////                }
//            }
//        });
//        mContext.attachDestroyFutures(future);
    }


    static class ViewHolder {
        //选中按钮checkbox
        @Bind(R.id.item_cart_id_cb)
        CheckBox itemCartIdCb;
//        @Bind(R.id.item_cart_id_iv)
//        SimpleDraweeView itemCartIdIv;
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
        @Bind(R.id.item_cart_id_totalpay_tv)
        TextView itemCartIdTotalpayTv;
        //库存不足textview提示
        @Bind(R.id.item_cart_id_shortage_tv)
        TextView itemCartIdShortageTv;
        @Bind(R.id.item_cart_id_oldprice_tv)
        TextView itemCartIdOldpriceTv;
        @Bind(R.id.item_cart_id_price_ll)
        LinearLayout itemCartIdPriceLl;
        //满减
        @Bind(R.id.order_sku_ll_fullcut)
        LinearLayout itemCartIdDiscountll;
        @Bind(R.id.order_sku_fullcut_tv)
        TextView itemCartIdDiscountTv;

        @Bind(R.id.order_sku_rl)
        RelativeLayout orderSkuRl;
        //改变数量view
        @Bind(R.id.layout_layout_id_changenum)
        LinearLayout layoutLayoutIdChangenum;
        //商品状态
        @Bind(R.id.item_cart_id_status_tv)
        TextView itemCartIdStatusTv;
        //失效text
        @Bind(R.id.item_cart_id_status)
        TextView itemCartIdStatus;

        //底部距离
        @Bind(R.id.cart_id_bottom)
        View itemBottomView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
