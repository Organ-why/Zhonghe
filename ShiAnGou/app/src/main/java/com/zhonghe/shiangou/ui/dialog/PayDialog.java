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

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhonghe.lib_base.baseui.BaseDialog;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.CharPay;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import org.json.JSONException;

/**
 * Created by a on 2017/8/17.
 */

public class PayDialog extends PopupWindow {
    private final View mMenuView;
    private final LinearLayout mLlPay;
    CharPay.DataBean payData;

    public PayDialog(Context context, final CharPay.DataBean payData) {
        super(context);
        this.payData = payData;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_pay, null);
        mLlPay = (LinearLayout) mMenuView.findViewById(R.id.id_pay_wechat_ll);

        mLlPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dismiss();
                if (ProjectApplication.api != null) {
                    PayReq req = new PayReq();

                    req.appId = CstProject.WEIXIN.WEIXIN_APP_ID;// 微信开放平台审核通过的应用APPID
                    req.partnerId = payData.getPartnerid();// 微信支付分配的商户号
                    req.prepayId =  payData.getPrepayid();// 预支付订单号，app服务器调用“统一下单”接口获取
                    req.nonceStr =  payData.getNoncestr();// 随机字符串，不长于32位，服务器小哥会给咱生成
                    req.timeStamp =  payData.getTimestamp();// 时间戳，app服务器小哥给出
                    req.packageValue =  payData.getPackageX();// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
                    req.sign =  payData.getSign();// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
//                        req.partnerId = jsonObject.getString("partnerid");// 微信支付分配的商户号
//                        req.prepayId = jsonObject.getString("prepayid");// 预支付订单号，app服务器调用“统一下单”接口获取
//                        req.nonceStr = jsonObject.getString("noncestr");// 随机字符串，不长于32位，服务器小哥会给咱生成
//                        req.timeStamp = jsonObject.getString("timestamp");// 时间戳，app服务器小哥给出
//                        req.packageValue = jsonObject.getString("package");// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
//                        req.sign = jsonObject.getString("sign");// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
                    ProjectApplication.api.sendReq(req);
                }
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
