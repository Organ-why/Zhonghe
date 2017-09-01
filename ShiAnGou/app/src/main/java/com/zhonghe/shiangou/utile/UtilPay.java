package com.zhonghe.shiangou.utile;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhonghe.shiangou.data.bean.CharPay;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProjectApplication;

/**
 * auther: whyang
 * date: 2017/9/1
 * desc: 支付处理
 */

public class UtilPay {
    public final static int PAY_TYPE_WECHAT = 101;
    public final static int PAY_TYPE_ALIPAY = 102;


    public static void sartPay(int payType, CharPay.DataBean payData) {
        switch (payType) {
            case PAY_TYPE_ALIPAY:

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
}
