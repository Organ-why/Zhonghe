package com.zhonghe.shiangou.system.global;

import android.content.Context;
import android.content.Intent;

import com.zhonghe.shiangou.ui.activity.AddressManageActivity;
import com.zhonghe.shiangou.ui.activity.ChangeAddressActivity;
import com.zhonghe.shiangou.ui.activity.ConfirmOrderActivity;
import com.zhonghe.shiangou.ui.activity.ForgetPwdActivity;
import com.zhonghe.shiangou.ui.activity.LikeActivity;
import com.zhonghe.shiangou.ui.activity.LoginActivity;
import com.zhonghe.shiangou.ui.activity.MainActivity;
import com.zhonghe.shiangou.ui.activity.OrderManageActivity;
import com.zhonghe.shiangou.ui.activity.RefundsActivity;
import com.zhonghe.shiangou.ui.activity.RegisterActivity;
import com.zhonghe.shiangou.ui.activity.SetupActivity;
import com.zhonghe.shiangou.ui.activity.UserActivity;
import com.zhonghe.shiangou.ui.fragment.UserFragment;

/**
 * Date: 2017/8/12.
 * Author: whyang
 */
public class ProDispatcher {
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
    public static void goRegisterActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RegisterActivity.class);
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
        Intent intent = new Intent(context, SetupActivity.class);
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

    /**
     * 添加 修改  地址
     *
     * @param context
     */
    public static void goChangeAddressActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ChangeAddressActivity.class);
        context.startActivity(intent);
    }

    /**
     * 确认订单
     *
     * @param context
     */
    public static void goConfirmOrderActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        context.startActivity(intent);
    }

    /**
     * 订单管理
     *
     * @param context
     */
    public static void goOrderManageActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, OrderManageActivity.class);
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
        Intent intent = new Intent(context, RefundsActivity.class);
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
        Intent intent = new Intent(context, LikeActivity.class);
        context.startActivity(intent);
    }
}
