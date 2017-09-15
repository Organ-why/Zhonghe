package com.zhonghe.shiangou.system.global;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.zhonghe.shiangou.data.bean.AddressInfo;
import com.zhonghe.shiangou.data.bean.RefundsDetailInfo;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.constant.CstProject.BROADCAST_ACTION;
import com.zhonghe.shiangou.ui.activity.AboutActivity;
import com.zhonghe.shiangou.ui.activity.AddressManageActivity;
import com.zhonghe.shiangou.ui.activity.ChangeAddressActivity;
import com.zhonghe.shiangou.ui.activity.ChangeNameActivity;
import com.zhonghe.shiangou.ui.activity.ChangePwdActivity;
import com.zhonghe.shiangou.ui.activity.ConfirmOrderActivity;
import com.zhonghe.shiangou.ui.activity.ForgetPwdActivity;
import com.zhonghe.shiangou.ui.activity.GoodsDetailActivity;
import com.zhonghe.shiangou.ui.activity.GoodsListActivity;
import com.zhonghe.shiangou.ui.activity.LikeActivity;
import com.zhonghe.shiangou.ui.activity.LocationActivity;
import com.zhonghe.shiangou.ui.activity.LoginActivity;
import com.zhonghe.shiangou.ui.activity.LogisticsActivity;
import com.zhonghe.shiangou.ui.activity.MainActivity;
import com.zhonghe.shiangou.ui.activity.OrderManageActivity;
import com.zhonghe.shiangou.ui.activity.PointActivity;
import com.zhonghe.shiangou.ui.activity.PointDetailActivity;
import com.zhonghe.shiangou.ui.activity.PointExcangeListActivity;
import com.zhonghe.shiangou.ui.activity.PointExcangeRecordActivity;
import com.zhonghe.shiangou.ui.activity.PointExchangeResultActivity;
import com.zhonghe.shiangou.ui.activity.PointUnlineActivity;
import com.zhonghe.shiangou.ui.activity.PointUnlineDetailActivity;
import com.zhonghe.shiangou.ui.activity.PointUnlineListActivity;
import com.zhonghe.shiangou.ui.activity.RefundsActivity;
import com.zhonghe.shiangou.ui.activity.RefundsBeginActivity;
import com.zhonghe.shiangou.ui.activity.RefundsDetailActivity;
import com.zhonghe.shiangou.ui.activity.RegisterActivity;
import com.zhonghe.shiangou.ui.activity.RemarkActivity;
import com.zhonghe.shiangou.ui.activity.RemarkListActivity;
import com.zhonghe.shiangou.ui.activity.SearchActivity;
import com.zhonghe.shiangou.ui.activity.SetupActivity;
import com.zhonghe.shiangou.ui.activity.UserActivity;
import com.zhonghe.shiangou.ui.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2017/8/12.
 * Author: whyang
 */
public class ProDispatcher {


    //////////////////////////////////////////Activity跳转////////////////////////////////////////////////

    /**
     * 打开界面
     *
     * @param context
     */
    public static void goMainActivity(Context context) {
        goMainActivity(context, -1);
    }

    /**
     * 打开界面
     *
     * @param context
     */
    public static void goMainActivity(Context context, int flag) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, MainActivity.class);
        if (flag > 0) {
            intent.addFlags(flag);
        }
        context.startActivity(intent);
    }

    /**
     * 打开注册
     *
     * @param context
     */
    public static void goRegisterActivity(Context context, String title) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(CstProject.KEY.DATA, title);
        context.startActivity(intent);
    }

    /**
     * 忘记密码
     *
     * @param context
     */
    public static void goForgetPwdActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ForgetPwdActivity.class);
        context.startActivity(intent);
    }

    /**
     * 修改密码
     *
     * @param context
     */
    public static void goChangePwdActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ChangePwdActivity.class);
        context.startActivity(intent);
    }

    /**
     * 用户
     *
     * @param context
     */
    public static void goUserActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }

    /**
     * 登录
     *
     * @param context
     */
    public static void goLoginActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 设置
     *
     * @param context
     */
    public static void goSetupActivity(Context context) {
        if (context == null) {
            return;
        }
        if (ProjectApplication.mUser == null) {
            goLoginActivity(context);
            return;
        }
        Intent intent = new Intent(context, SetupActivity.class);
        context.startActivity(intent);
    }

    /**
     * 关于我们
     *
     * @param context
     */
    public static void goAboutActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    /**
     * 选择地址
     *
     * @param context
     */
    public static void goSelectAddressActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AddressManageActivity.class);
        context.startActivity(intent);
    }

    public static void goSelectAddressActivity(Activity context, Integer requestCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, AddressManageActivity.class);
        if (requestCode == null) {
            context.startActivity(intent);
        } else {
//            intent.putExtra(CstProject.KEY.ID, addressId);
            context.startActivityForResult(intent, requestCode);
        }

    }

    /**
     * 添加 修改  地址
     *
     * @param context
     */
    public static void goChangeAddressActivity(Context context, AddressInfo addressInfo) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ChangeAddressActivity.class);
        intent.putExtra(CstProject.KEY.DATA, addressInfo);
        context.startActivity(intent);
    }

    /**
     * 修改昵称
     *
     * @param context
     */
    public static void goChangeNameActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ChangeNameActivity.class);
        context.startActivity(intent);
    }

    /**
     * 确认订单
     *
     * @param context
     */
    public static void goConfirmOrderActivity(Context context, ArrayList<String> ids, int typeCode, String goods_attr_id, int goods_count) {
        if (context == null) {
            return;
        }
        if (ProjectApplication.mUser == null) {
            goLoginActivity(context);
            return;
        }
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putStringArrayListExtra(CstProject.KEY.ID, ids);
        intent.putExtra(CstProject.KEY.TYPE, typeCode);
        intent.putExtra(CstProject.KEY.COUNT, goods_count);
        intent.putExtra(CstProject.KEY.ATTRID, goods_attr_id);
        context.startActivity(intent);
    }

    /**
     * 订单管理
     *
     * @param context
     */
    public static void goOrderManageActivity(Context context, int indexId) {
        if (context == null) {
            return;
        }
        if (ProjectApplication.mUser == null) {
            goLoginActivity(context);
            return;
        }
        Intent intent = new Intent(context, OrderManageActivity.class);
        intent.putExtra(CstProject.KEY.INDEX, indexId);
        context.startActivity(intent);
    }

    /**
     * 物流
     *
     * @param context
     */
    public static void goLogisticsActivity(Context context, String num, String type, String express) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LogisticsActivity.class);
        intent.putExtra(CstProject.KEY.VALUES1, num);
        intent.putExtra(CstProject.KEY.VALUES2, type);
        intent.putExtra(CstProject.KEY.VALUES3, express);
        context.startActivity(intent);
    }

    /**
     * 退货退款
     *
     * @param context
     */
    public static void goRefundsActivity(Context context) {
        if (context == null) {
            return;
        }
        if (ProjectApplication.mUser == null) {
            goLoginActivity(context);
            return;
        }
        Intent intent = new Intent(context, RefundsActivity.class);
        context.startActivity(intent);
    }

    /**
     * 退货退款详情
     *
     * @param context
     */
    public static void goRefundsDetailInfo(Context context, String refundsId) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RefundsDetailActivity.class);
        intent.putExtra(CstProject.KEY.ID, refundsId);
        context.startActivity(intent);
    }

    /**
     * 我喜欢的
     *
     * @param context
     */
    public static void goLikeActivity(Context context) {
        if (context == null) {
            return;
        }
        if (ProjectApplication.mUser == null) {
            goLoginActivity(context);
            return;
        }
        Intent intent = new Intent(context, LikeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 评论
     *
     * @param context
     */
    public static void goRemarkActivity(Context context, String goodsId, String goodsImg) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RemarkActivity.class);
        intent.putExtra(CstProject.KEY.ID, goodsId);
        intent.putExtra(CstProject.KEY.DATA, goodsImg);
        context.startActivity(intent);
    }

    /**
     * 评论列表
     *
     * @param context
     */
    public static void goRemarkListActivity(Context context, String goodsId) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RemarkListActivity.class);
        intent.putExtra(CstProject.KEY.ID, goodsId);
        context.startActivity(intent);
    }

    /**
     * 退货退款
     *
     * @param context
     */
    public static void goRefundsBeginActivity(Context context, String orderId, String price) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RefundsBeginActivity.class);
        intent.putExtra(CstProject.KEY.ID, orderId);
        intent.putExtra(CstProject.KEY.DATA, price);
        context.startActivity(intent);
    }

    /**
     * 商品详情
     *
     * @param context
     */
    public static void goGoodsDetailActivity(Context context, String goods_id) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(CstProject.KEY.ID, goods_id);
        context.startActivity(intent);
    }

    /**
     * 积分商品详情
     *
     * @param context
     */
    public static void goPointDetailActivity(Context context, String goods_id) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PointDetailActivity.class);
        intent.putExtra(CstProject.KEY.ID, goods_id);
        context.startActivity(intent);
    }

    /**
     * 积分线下
     *
     * @param context
     */
    public static void goPointUnlineActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PointUnlineActivity.class);
//        intent.putExtra(CstProject.KEY.ID, goods_id);
        context.startActivity(intent);
    }

    /**
     * 线下商城列表
     *
     * @param context
     */
    public static void goPointUnlineListActivity(Context context, String type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PointUnlineListActivity.class);
        intent.putExtra(CstProject.KEY.TYPE, type);
        context.startActivity(intent);
    }

    /**
     * 线下积分详情
     *
     * @param context
     */
    public static void goPointUnlineDetailActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PointUnlineDetailActivity.class);
//        intent.putExtra(CstProject.KEY.ID, goods_id);
        context.startActivity(intent);
    }

    /**
     * 地图
     *
     * @param context
     */
    public static void goLocationActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LocationActivity.class);
//        intent.putExtra(CstProject.KEY.ID, goods_id);
        context.startActivity(intent);
    }

    /**
     * 兑换结果返回
     *
     * @param context
     */
    public static void goPointExchangeResutl(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PointExchangeResultActivity.class);
//        intent.putExtra(CstProject.KEY.ID, goods_id);
        context.startActivity(intent);
    }

    /**
     * 兑换列表
     *
     * @param context
     */
    public static void goPointExchangeRecord(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PointExcangeRecordActivity.class);
//        intent.putExtra(CstProject.KEY.ID, goods_id);
        context.startActivity(intent);
    }

    /**
     * 积分商品列表
     *
     * @param context
     * @param Type    type 四种值  0 ：首页列表 ，1：零元购，  2：日常家居， 3 新品  4 所有商品
     */
    public static void goPointExcangeListActivity(Context context, int Type) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PointExcangeListActivity.class);
        intent.putExtra(CstProject.KEY.TYPE, Type);
        context.startActivity(intent);
    }

    /**
     * 商品列表
     *
     * @param context
     * @param cat_id
     * @param keywords
     */
    public static void goGoodsListActivity(Context context, String cat_id, String keywords) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, GoodsListActivity.class);
        intent.putExtra(CstProject.KEY.ID, cat_id);
        intent.putExtra(CstProject.KEY.KEY, keywords);
        context.startActivity(intent);
    }

    /**
     * 商品详情
     *
     * @param context
     */
    public static void goSearchActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    /**
     * 积分首页
     *
     * @param context
     */
    public static void goPointActivity(Context context) {
        if (context == null) {
            return;
        }
        if (ProjectApplication.mUser == null) {
            goLoginActivity(context);
            return;
        }

        Intent intent = new Intent(context, PointActivity.class);
        context.startActivity(intent);
    }


    //////////////////////////////////////////Sendbroadcast////////////////////////////////////////////////

    /**
     * 登录广播
     *
     * @param context
     */
    public static void sendLoginBroadcast(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION.LOGIN_ACTION);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
    }

    /**
     * 登出广播
     *
     * @param context
     */
    public static void sendLogoutBroadcast(@NonNull Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION.LOGOUT_ACTION);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
    }

    /**
     * 位置
     *
     * @param context
     * @param resultCode
     */
    public static void sendLocationBroadcast(@NonNull Context context, int resultCode) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION.LOCATION_ACTION);
        intent.putExtra(CstProject.KEY.CODE, resultCode);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
    }

    /**
     * 兑换
     *
     * @param context
     */
    public static void sendExchangeBroadcast(@NonNull Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION.POINT_EXCHANGE_ACTION);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
    }

    /**
     * 支付返回
     *
     * @param context
     * @param resultCode
     */
    public static void sendPayResultBroadcast(Context context, int resultCode) {
        if (context == null) {
            return;
        }
        if (ProjectApplication.mUser == null) {
            goLoginActivity(context);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(CstProject.KEY.CODE, resultCode);
        intent.setAction(BROADCAST_ACTION.PAY_RESULT_ACTION);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
    }

    /**
     * tab选中
     *
     * @param context
     * @param tabIndex
     */
    public static void sendMainTabBroadcast(Context context, int tabIndex) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(CstProject.KEY.INDEX, tabIndex);
        intent.setAction(BROADCAST_ACTION.MAINTAB_CHECK_ACTION);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
    }

    /**
     * 添加商品到购物车
     *
     * @param context
     * @param goodsId
     */
    public static void sendAddCardBroadcast(Context context, String goodsId) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(CstProject.KEY.ID, goodsId);
        intent.setAction(BROADCAST_ACTION.CART_ADD_ACTION);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        manager.sendBroadcast(intent);
    }
}
