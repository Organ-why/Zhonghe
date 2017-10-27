package com.zhonghe.shiangou.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.utile.UtilPay;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by a on 2017/8/17.
 */

public class PayDialog extends PopupWindow implements View.OnClickListener {
    private final View mMenuView;
    @BindView(R.id.id_pay_wechat_ll)
    LinearLayout idPayWechatLl;
    @BindView(R.id.pay_ll)
    LinearLayout payLl;
    @BindView(R.id.id_pay_dialog_cancel)
    ImageView idPayDialogCancel;
    @BindView(R.id.root_view)
    RelativeLayout rootView;
    @BindView(R.id.id_pay_alipay_ll)
    LinearLayout idPayAlipayLl;

    payClickListener payListener;

    public PayDialog(Context context, payClickListener payListener) {
        super(context);
        this.payListener = payListener;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_pay, null);
        rootView = ButterKnife.findById(mMenuView, R.id.root_view);
        rootView.setOnClickListener(this);
        idPayDialogCancel = ButterKnife.findById(mMenuView, R.id.id_pay_dialog_cancel);
        idPayDialogCancel.setOnClickListener(this);
        idPayAlipayLl = ButterKnife.findById(mMenuView, R.id.id_pay_alipay_ll);
        idPayAlipayLl.setOnClickListener(this);
        payLl = ButterKnife.findById(mMenuView, R.id.pay_ll);
        idPayWechatLl = ButterKnife.findById(mMenuView, R.id.id_pay_wechat_ll);
        idPayWechatLl.setOnClickListener(this);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        //设置弹出窗体动画效果
        setAnimationStyle(R.style.commom_popup_fade_anim_style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x7f000000);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        rootView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }

                return true;
            }
        });

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.common_push_up_in);
        mMenuView.startAnimation(anim);
        setContentView(mMenuView);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_pay_wechat_ll:
                payListener.OnClickPay(UtilPay.PAY_TYPE_WECHAT);
                dismiss();
                break;
            case R.id.id_pay_alipay_ll:
                payListener.OnClickPay(UtilPay.PAY_TYPE_ALIPAY);
                dismiss();
                break;
            case R.id.id_pay_dialog_cancel:
                dismiss();
                break;
            case R.id.root_view:
                dismiss();
                break;
        }
    }


    public interface payClickListener {
        /**
         * @param payType
         */
        void OnClickPay(int payType);
    }
}
