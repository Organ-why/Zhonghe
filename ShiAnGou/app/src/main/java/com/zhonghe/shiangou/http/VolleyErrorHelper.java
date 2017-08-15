package com.zhonghe.shiangou.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.system.global.ProjectApplication;

/**
 * Created by wangyunfa on 16/10/8.
 */

public class VolleyErrorHelper {

    // 用于返回具体错误信息，分辨错误类别
    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.error_timeout);
        } else if (isServerProblem(error)) {
            return  ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.error_server);
        } else if (isNetworkProblem(error)) {
            return  ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.error_internet);
        }
        return  ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.error_server);
    }

    // 判断是否是网络错误
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    // 判断是否是服务端错误
    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError)|| (error instanceof AuthFailureError);
    }
}
