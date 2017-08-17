package com.zhonghe.shiangou.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhonghe.lib_base.baseui.BaseDialog;
import com.zhonghe.shiangou.R;

/**
 * Created by a on 2017/8/17.
 */

public class PayDialog extends PopupWindow {
    private final View mMenuView;
    private final LinearLayout mLlPay;

    public PayDialog(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_pay, null);
        mLlPay = (LinearLayout) mMenuView.findViewById(R.id.id_pay_wechat_ll);

        mLlPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //确认按钮
//        mConfirmBt = (Button) mMenuView.findViewById(R.id.pay_id_confirm_bt);
//        mConfirmBt.setTag(payType);
//        mConfirmBt.setOnClickListener(listener);
//        payIdTotalpayTv=(TextView) mMenuView.findViewById(R.id.pay_id_totalpay_tv);
//        payIdTotalpayTv.setText(UtilOuer.formatPrice(paidFee));
        //取消按钮
//        setOnDismissListener(canclelistener
//                new OnDismissListener() {
//            @Override
//            public void onDismiss() {
//
//            }
//        }
////        );
//        mMenuView.findViewById(R.id.pay_close_iv).setOnClickListener(
////                canclelistener
//                new View.OnClickListener() {
//
//                    public void onClick(View v) {
//                        //销毁弹出框
//                        dismiss();
//                    }
//                }
//        );
//        getPayView();

        setContentView(mMenuView);
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
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

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


//    @Override
//    protected void initLayout() {
//        setContentView(R.layout.dialog_pay);
//    }
//
//    @Override
//    protected void initViews() {
//    }
}
