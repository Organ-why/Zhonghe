package com.zhonghe.shiangou.utile;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.shiangou.data.bean.CharPay;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.Map;

/**
 * auther: whyang
 * date: 2017/9/1
 * desc: 支付处理
 */

public class UtilPay {
    public final static int PAY_TYPE_WECHAT = 101;
    public final static int PAY_TYPE_ALIPAY = 102;


    public static void sartPay(Context context, int payType, CharPay.DataBean payData) {
        switch (payType) {
            case PAY_TYPE_ALIPAY:

                AliPayTask aliPayTask = new AliPayTask((Activity) context, payData.getSign()) {
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        UtilLog.d("alipay...gotit" + s);
//                        9000	订单支付成功
//                        8000	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//                        4000	订单支付失败
//                        5000	重复请求
//                        6001	用户中途取消
//                        6002	网络连接出错
//                        6004	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//                        其它	其它支付错误
                        if (s.equals("9000")) {
                            ProDispatcher.sendPayResultBroadcast(context, 0);
                        } else {
                            ProDispatcher.sendPayResultBroadcast(context, -1);
                        }
                    }
                };
                aliPayTask.execute();
//                PayTask alipay = new PayTask(DemoActivity.this);
//                String result = alipay.payV2(orderInfo,true);
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
                break;
            case PAY_TYPE_WECHAT:
                if (ProjectApplication.WXapi != null) {
                    PayReq req = new PayReq();
                    req.appId = CstProject.WEIXIN.WEIXIN_APP_ID;// 微信开放平台审核通过的应用APPID
                    req.partnerId = payData.getPartnerid();// 微信支付分配的商户号
                    req.prepayId = payData.getPrepayid();// 预支付订单号，app服务器调用“统一下单”接口获取
                    req.nonceStr = payData.getNoncestr();// 随机字符串，不长于32位，服务器小哥会给咱生成
                    req.timeStamp = String.valueOf(payData.getTimestamp());// 时间戳，app服务器小哥给出
                    req.packageValue = "Sign=WXPay";// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
                    req.sign = payData.getSign();// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
//                        req.partnerId = jsonObject.getString("partnerid");// 微信支付分配的商户号
//                        req.prepayId = jsonObject.getString("prepayid");// 预支付订单号，app服务器调用“统一下单”接口获取
//                        req.nonceStr = jsonObject.getString("noncestr");// 随机字符串，不长于32位，服务器小哥会给咱生成
//                        req.timeStamp = jsonObject.getString("timestamp");// 时间戳，app服务器小哥给出
//                        req.packageValue = jsonObject.getString("package");// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
//                        req.sign = jsonObject.getString("sign");// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
                    ProjectApplication.WXapi.sendReq(req);
                }
                break;
        }
    }

    static class AliPayTask extends AsyncTask<Object, Integer, String> {
        Activity context;
        String orderMsg;

        public AliPayTask(Activity context, String orderMsg) {
            this.context = context;
            this.orderMsg = orderMsg;
//            this.orderMsg = "app_id=2017080408031021&biz_content=%7B%22body%22%3A%22%E5%95%86%E5%93%81%E4%BF%A1%E6%81%AF%E5%95%86%E5%93%81%E4%BF%A1%E6%81%AF%E6%B5%8B%E8%AF%95%22%2C%22subject%22%3A+%22%E6%9C%89%E6%9C%BA%E5%A4%A7%E8%B1%86%E6%B2%B9%E6%9C%89%E6%9C%BA%E5%A4%A7%E8%B1%86%E6%B2%B9%22%2C%22out_trade_no%22%3A+%22%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest.shiangou.com.cn%2Fapp%2Falipay%2Fnotifycheck.php&sign_type=RSA2&timestamp=2017-09-04+17%3A56%3A49&version=1.0&sign=jXPt%2BvtVOlfYRXuHFK%2Bnl7NYCcytqw4Zrgfuv2g6E1dNR%2FVXkw%2BzqfYEGmracF21OJuhQ9%2B8xgAG9qoKK%2BXs5rfd7ya75%2FYmrTPbVrkAUENA4sBKhg87rzQFl%2FhQbmdWgaAFWRUXmm2UMtzEmda2oxV9sYIaKIzwAWaihyDOobk%2B8TZUL%2BLCIknpdovb6syhuJGxR%2F6p0XuxeYCit%2FMz%2B9CSPrTgdAZCQ%2Ft44ldf7vf2oByzq96zWe1XIq3R3P3IU3eTGNPwm%2BsteF5gWxUR3yd7xicBM3GUs5YaphtNCVnu5lwzCC0ooipLOuVhHo%2Fp1iCdclaNWTR9oTANtnYVeQ%3D%3D";

        }

        @Override
        protected String doInBackground(Object... objects) {
            PayTask alipay = new PayTask(context);
            Map<String, String> result = alipay.payV2(orderMsg, true);
            String resultStatus = result.get("resultStatus");
            UtilLog.d("alipay..." + resultStatus);
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
            return resultStatus;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

    }
}
