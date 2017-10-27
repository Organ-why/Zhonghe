package com.zhonghe.shiangou.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsTypeBean;
import com.zhonghe.shiangou.data.bean.GoodsdetailInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.widget.FlowLayout1;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @desc :
 */
public class SkuSelectDialog extends PopupWindow implements View.OnClickListener {
    @BindView(R.id.sku_select_img)
    SimpleDraweeView skuSelectImg;
    @BindView(R.id.sku_select_title_tv)
    TextView skuSelectTitleTv;
    @BindView(R.id.sku_select_close_iv)
    ImageView skuSelectCloseIv;
    @BindView(R.id.item_sku_select_symbol_tv)
    TextView itemSkuSelectSymbolTv;
    @BindView(R.id.item_sku_select_price_tv)
    TextView itemSkuSelectPriceTv;
    @BindView(R.id.item_sku_select_oldprice_tv)
    TextView itemSkuSelectOldpriceTv;
    @BindView(R.id.item_sku_select_amounttv)
    TextView itemSkuSelectAmounttv;
    @BindView(R.id.order_sku_rl)
    RelativeLayout orderSkuRl;
    @BindView(R.id.sku_context_msg_ll)
    LinearLayout skuContextMsgLl;
    @BindView(R.id.sku_select_id_ll)
    LinearLayout skuSelectIdLl;
    @BindView(R.id.layout_id_reduce_iv)
    ImageView layoutIdReduceIv;
    @BindView(R.id.layout_id_number_tv)
    TextView layoutIdNumberTv;
    @BindView(R.id.layout_id_add_iv)
    ImageView layoutIdAddIv;
    @BindView(R.id.layout_layout_id_changenum)
    LinearLayout layoutLayoutIdChangenum;
    @BindView(R.id.sku_context_change_ll)
    LinearLayout skuContextChangeLl;
    @BindView(R.id.sku_context_id_context)
    RelativeLayout skuContextIdContext;
    Button addbtn;
    Button buybtn;
    Button confirmbtn;
    LinearLayout addcarLl;
    @BindView(R.id.sku_select_id__flow)
    FlowLayout1 skuFlow;
    private View mMenuView;
    private View mLlRoot;
    private Context mContext;

    SkuSelectListener skuSelectListener;

    Integer mAmount = 1;

    private GoodsdetailInfo detail;
    private String mSkuId="";
    //sku list
    private List<View> viewList;
    @BindDimen(R.dimen.padding_xlsize)
    int skuPadding;

    public SkuSelectDialog(Activity context, GoodsdetailInfo data, int mCount, String skuId, SkuSelectListener skuSelectListener) {
        super(context);
        mContext = context;
        this.detail = data;
        this.mAmount = mCount;
        this.mSkuId = skuId;
        this.skuSelectListener = skuSelectListener;
        viewList = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.activity_skuselect, null);
        mLlRoot = (RelativeLayout) mMenuView.findViewById(R.id.sku_context_id_context);

        setContentView(mMenuView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setFocusable(true);
        //设置弹出窗体动画效果  
        setAnimationStyle(R.style.commom_popup_fade_anim_style);
        //实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0x7f000000);
        //设置弹出窗体的背景  
        this.setBackgroundDrawable(dw);

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mLlRoot.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }

                return true;
            }
        });

        layoutIdNumberTv = ButterKnife.findById(mMenuView, R.id.layout_id_number_tv);
        skuFlow = ButterKnife.findById(mMenuView, R.id.sku_select_id__flow);
        skuSelectTitleTv = ButterKnife.findById(mMenuView, R.id.sku_select_title_tv);
        itemSkuSelectPriceTv = ButterKnife.findById(mMenuView, R.id.item_sku_select_price_tv);
        itemSkuSelectOldpriceTv = ButterKnife.findById(mMenuView, R.id.item_sku_select_oldprice_tv);
        skuSelectImg = ButterKnife.findById(mMenuView, R.id.sku_select_img);
        layoutIdAddIv = ButterKnife.findById(mMenuView, R.id.layout_id_add_iv);
        layoutIdReduceIv = ButterKnife.findById(mMenuView, R.id.layout_id_reduce_iv);
        addbtn = ButterKnife.findById(mMenuView, R.id.id_goodsdetail_addcart_bt);
        buybtn = ButterKnife.findById(mMenuView, R.id.id_goodsdetail_buynow_bt);
        skuSelectCloseIv = ButterKnife.findById(mMenuView, R.id.sku_select_close_iv);
        confirmbtn = ButterKnife.findById(mMenuView, R.id.id_confirm_bt);
        addcarLl = ButterKnife.findById(mMenuView, R.id.id_sku_select_ll);
        layoutIdAddIv.setOnClickListener(this);
        layoutIdReduceIv.setOnClickListener(this);
        confirmbtn.setOnClickListener(this);
        skuSelectCloseIv.setOnClickListener(this);
        addbtn.setOnClickListener(this);
        buybtn.setOnClickListener(this);


        layoutIdNumberTv.setText(String.valueOf(mAmount));
        setSkuList(detail.getGoods_type());
        skuSelectTitleTv.setText(detail.getGoods().getGoods_name());
        itemSkuSelectPriceTv.setText(detail.getGoods().getShop_price());
        itemSkuSelectOldpriceTv.setText(detail.getGoods().getMarket_price());
        ProjectApplication.mImageLoader.loadImage(skuSelectImg, detail.getGoods().getGoods_img());


        Animation anim = AnimationUtils.loadAnimation(context, R.anim.common_push_up_in);
        mLlRoot.startAnimation(anim);
    }


    @Override
    public void dismiss() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.common_push_down_out);
        mLlRoot.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                SkuSelectDialog.super.dismiss();
            }

        }, 300);

    }

    // sku item 显示
    void setSkuList(List<GoodsTypeBean> goods_type) {
        for (final GoodsTypeBean sku : goods_type) {
            View btnLayout = LayoutInflater.from(mContext).inflate(R.layout.layout_prodetail_sku_bt, null);
            Button skuBt = (Button) btnLayout.findViewById(R.id.sku_select_id__button);
            skuBt.setText(sku.getAttr_value());
            skuBt.setTag(sku);
            skuBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button mBtn = (Button) v;
                    if (null != mBtn.getTag()) {
                        GoodsTypeBean item = (GoodsTypeBean) mBtn.getTag();
                        if (item != null) {
                            mSkuId = item.getAttr_id();
                            for (View btv : viewList) {
                                Button listBt = (Button) btv.findViewById(R.id.sku_select_id__button);
                                GoodsTypeBean tag = (GoodsTypeBean) listBt.getTag();
                                if (tag.getAttr_id() == mSkuId) {
                                    listBt.setBackgroundResource(R.drawable.circle_bt_orange_bg);
                                    listBt.setTextColor(mContext.getResources().getColor(R.color.common_white));
                                    skuSelectListener.onCheckSKU(tag.getAttr_id());
                                } else {
                                    if (listBt.isEnabled()) {
                                        listBt.setBackgroundResource(R.drawable.circle_orange_hollow_bg);
                                        listBt.setTextColor(mContext.getResources().getColor(R.color.res_color_apptheme));
                                    }
                                }
                            }
                        }
                    }
                }
            });
            btnLayout.setPadding(0, skuPadding, 0, skuPadding);
//            btnLayout.setPadding(skuPadding, skuPadding, skuPadding, skuPadding);
            skuFlow.addView(btnLayout);
            viewList.add(btnLayout);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_id_add_iv:
                addAmount();
                break;
            case R.id.layout_id_reduce_iv:
                reduceAmount();
                break;
            case R.id.id_goodsdetail_addcart_bt:
                skuSelectListener.onAddCart(mSkuId);
                break;
            case R.id.id_goodsdetail_buynow_bt:
                skuSelectListener.onBuyNow(mSkuId);
                break;
            case R.id.sku_select_close_iv:
                dismiss();
            case R.id.id_confirm_bt:
                skuSelectListener.onAddCart(mSkuId);
                break;
        }
    }

    /**
     * sku选择 接口
     */
    public interface SkuSelectListener {
        /**
         * 修改商品数量
         *
         * @param count
         */
        void onCountChange(int count);

        /**
         * 选择sku
         *
         * @param sku
         */
        void onCheckSKU(String sku);

        void onAddCart(String sku);

        void onBuyNow(String sku);
    }

    public void setDialogType(boolean justSku) {
        if (justSku) {
            addcarLl.setVisibility(View.GONE);
            confirmbtn.setVisibility(View.VISIBLE);
        } else {
            addcarLl.setVisibility(View.VISIBLE);
            confirmbtn.setVisibility(View.GONE);
        }
    }

    /**
     * 增加数量
     */
    @OnClick(R.id.layout_id_add_iv)
    protected void addAmount() {
//        if (mAmount < mCount) {
        mAmount++;
        layoutIdNumberTv.setText(String.valueOf(mAmount));
        skuSelectListener.onCountChange(mAmount);
//        } else {
//            UtilOuer.toast(this, R.string.prodetail_sku_select_amount);
//        }

    }

    /**
     * 减少数量
     */
    @OnClick(R.id.layout_id_reduce_iv)
    protected void reduceAmount() {
        if (mAmount > 1) {
            mAmount--;
            layoutIdNumberTv.setText(String.valueOf(mAmount));
            skuSelectListener.onCountChange(mAmount);
        }
    }
}  