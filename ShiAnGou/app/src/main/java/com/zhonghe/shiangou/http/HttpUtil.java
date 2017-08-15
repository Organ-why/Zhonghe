package com.zhonghe.shiangou.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lib_httpok.OkHttp3Stack;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by a on 2017/8/10.
 */

public class HttpUtil {
    public static String URL_HomeData = "http://test.shiangou.com.cn/Android/index.php";

    public static Request<?> volleyPost(Context context, String url, final Map<String, String> map, final ResultListener listener) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    listener.onSuccess(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onFial(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFial(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        return request;
    }

    public static Request<?> getHomeData(Context context, final ResultListener listener) {
        Request<?> request = volleyPost(context, URL_HomeData, null, listener);
        return request;
    }
}
