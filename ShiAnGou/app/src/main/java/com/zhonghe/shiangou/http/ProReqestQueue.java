package com.zhonghe.shiangou.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.lib_httpok.OkHttp3Stack;
import com.zhonghe.lib_base.utils.UtilImage;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import okhttp3.OkHttpClient;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public class ProReqestQueue {
    public static ProReqestQueue proReqestQueue;
    public static RequestQueue requestQueue;
    public static OkHttpClient client;


    public ProReqestQueue(Context context) {
        if (client == null) {
            client = new OkHttpClient();
        }

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context, new OkHttp3Stack(client));
        }
    }


    public void addRequest(Request request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

    public void cancleRequest(Object tag) {
//        if (tag != null) {
//            request.setTag(tag);
//        }
        requestQueue.cancelAll(tag);
    }

    public synchronized static ProReqestQueue getInstance(Context ctx) {
        if (null == proReqestQueue) {
            proReqestQueue = new ProReqestQueue(ctx);
        }

        return proReqestQueue;
    }
}
