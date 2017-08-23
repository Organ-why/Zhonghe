package com.zhonghe.shiangou.http;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.zhonghe.shiangou.data.baseres.BaseRes;
import com.zhonghe.shiangou.data.bean.GoodsdetailInfo;
import com.zhonghe.shiangou.data.bean.HomeData;
import com.zhonghe.shiangou.data.bean.StringInfo;
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.utile.Device;
import com.zhonghe.shiangou.utile.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.zhonghe.shiangou.system.constant.CstProject.URL_PRO;

/**
 * Created by a on 2017/8/10.
 */

public class HttpUtil {
    // 首页信息
    public static String URL_HomeData = URL_PRO + "app/index.php";
    //注册
    public static String URL_Register = URL_PRO + "user/register.php";
    //手机验证码
    public static String URL_GetPhoneCode = URL_PRO + "phone.php";
    //登录
    public static String URL_Login = URL_PRO + "app/user/login.php";
    //商品详情
    public static String URL_GoodsDetail = URL_PRO + "app/goods/goods.php";
    //添加购物车
    public static String URL_AddCart = URL_PRO + "cart.php";
    //购物车列表
    public static String URL_CartList = URL_PRO + "app/cart/show.php";
    //订单
    public static String URL_Order = URL_PRO + "order.php";
    //订单列表
    public static String URL_OrderList = URL_PRO + "orderindex.php";
    //地址
    public static String URL_Address = URL_PRO + "address.php";
    //密码
    public static String URL_Password = URL_PRO + "password.php";


    /**
     * 手机验证码
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getPhoneCode(Context context, String phone, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        Request<?> request = volleyPost(context, URL_GetPhoneCode, map, listener, StringInfo.class);
        return request;
    }

    /**
     * 注册
     *
     * @param context
     * @param phone
     * @param code
     * @param pwd
     * @param listener
     * @return
     */
    public static Request<?> getRegiste(Context context, String phone, String code, String pwd, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", pwd);
        map.put("ident", code);
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_Register, map, listener, StringInfo.class);
        return request;
    }

    /**
     * 登录
     *
     * @param context
     * @param phone
     * @param pwd
     * @param listener
     * @return
     */
    public static Request<?> getLogin(Context context, String phone, String pwd, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", pwd);
//        map.put("ident", code);
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_Login, map, listener, UserInfo.class);
        return request;
    }


    /**
     * 获取首页信息
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getHomeData(Context context, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("curpage", "0");
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Request<?> request = volleyPost(context, URL_HomeData, map, listener, HomeData.class);
        return request;
    }

    /**
     * 商品详情
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getGoodsDetail(Context context, String goods_id, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("goods_id", goods_id);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Request<?> request = volleyPost(context, URL_GoodsDetail, map, listener, GoodsdetailInfo.class);
        return request;
    }

    /**
     * 购物车列表
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getCartList(Context context, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", "22");
        map.put("curpage", "1");
        map.put("cursize", "10");
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Request<?> request = volleyPost(context, URL_CartList, map, listener, null);
        return request;
    }

/////////////////////////////////////////////////////////网络基本请求////////////////////////////////////////////////////////////////////////

    public static final String CHAR_SET = "utf-8";

    public static boolean isWiFiActive(Context inContext) {
        ConnectivityManager cm = (ConnectivityManager) inContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * post 请求
     *
     * @param context
     * @param url
     * @param map
     * @param listener
     * @param bean
     * @return
     */
    public static Request<?> volleyPost(final Context context, final String url, final Map<String, String> map, final ResultListener listener, final Object bean) {
//        map.put("pubversion", Device.getCurrentAppVersionName(context) + "");
//        map.put("pubdevice", Device.getDeviceId(context));
//        map.put("pubplatform", "android");

//        String memberKey = PrefUtils.getString(context, Const.MEMBER_KEY, "");
//        if (!TextUtils.isEmpty(memberKey)) {
//            map.put("pubMemberKey", memberKey);
//        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    if (s.indexOf("null{") >= 0) {
                        s = s.replace("null{", "{");
                    }
                    Log.d("resp-url", url);
                    Log.d("resp-map", map.toString());
                    Log.d("resp-str", s);
                    JSONObject json = new JSONObject(s);
                    BaseRes obj = (BaseRes) JSONParser.toObject(json.toString(), BaseRes.class);
                    if (obj.getState() != 1) {
                        listener.onFail(obj.getMsg());
                    } else if (bean != null) {
                        String strdata = JSONParser.toString(obj.getDatas());
                        Object data = JSONParser.toObject(strdata, bean);
                        listener.onSuccess(data);
                    } else {
                        listener.onSuccess(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onFail(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String errorMsg = VolleyErrorHelper.getMessage(volleyError, context);
                Log.d("resp-url", url);
                Log.d("resp-map", map.toString());
                Log.d("resp-errorMsg", errorMsg);
                listener.onFail(errorMsg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
                StringBuffer sb = new StringBuffer();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey().toString();
                    String val = entry.getValue().toString();
                    sb.append(key + "--" + val + ",");
                }
                return map;
            }


            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data, CHAR_SET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1, 1.0f));// 设置超时时间,取消自动重试
        return request;
    }


    /**
     * post 请求
     *
     * @param context
     * @param url
     * @param map
     * @param listener
     * @param bean
     * @return
     */
    public static Request<?> volleyPostForPay(final Context context, final String url, final Map<String, String> map, final ResultListener listener, final Object bean) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    if (s.indexOf("null{") >= 0) {
                        s = s.replace("null{", "{");
                    }
                    Log.d("resp-url", url);
                    Log.d("resp-map", map.toString());
                    Log.d("resp-str", s);
                    JSONObject json = new JSONObject(s);
                    Object obj = JSONParser.toObject(json.toString(), bean);
                    listener.onSuccess(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String errorMsg = VolleyErrorHelper.getMessage(volleyError, context);
                Log.d("resp-url", url);
                Log.d("resp-map", map.toString());
                Log.d("resp-errorMsg", errorMsg);
                listener.onFail(errorMsg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
                StringBuffer sb = new StringBuffer();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey().toString();
                    String val = entry.getValue().toString();
                    sb.append(key + "--" + val + ",");
                }
                return map;
            }


            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data, CHAR_SET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1, 1.0f));// 设置超时时间,取消自动重试
        return request;
    }


    /**
     * post 请求
     *
     * @param context
     * @param url
     * @param map
     * @param listener
     * @param bean
     * @return
     */
    public static Request<?> volleyPostStringForZFBLogin(final Context context, final String url, final Map<String, String> map, final ResultListener listener, final Object bean) {
        //此接口不加公共参数，提供支付快捷登录访问，加入后支付宝授权失败
//        map.put("pubversion", Device.getCurrentAppVersionName(context) + "");
//        map.put("pubdevice", Device.getDeviceId(context));
//        map.put("pubplatform", "android");
//
//        String memberKey = PrefUtils.getString(context, Const.MEMBER_KEY, "");
//        if (!TextUtils.isEmpty(memberKey)) {
//            map.put("pubMemberKey", memberKey);
//        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    Log.d("resp-url", url);
                    Log.d("resp-map", map.toString());
                    Log.d("resp-str", s);
                    listener.onSuccess(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String errorMsg = VolleyErrorHelper.getMessage(volleyError, context);
                Log.d("resp-url", url);
                Log.d("resp-map", map.toString());
                Log.d("resp-errorMsg", errorMsg);
                listener.onFail(errorMsg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
                StringBuffer sb = new StringBuffer();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey().toString();
                    String val = entry.getValue().toString();
                    sb.append(key + "--" + val + ",");
                }
                return map;
            }


            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data, CHAR_SET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1, 1.0f));// 设置超时时间,取消自动重试
        return request;
    }

    /**
     * post 请求
     *
     * @param context
     * @param url
     * @param map
     * @param listener
     * @param
     * @return
     */
    public static Request<?> volleyPostString(final Context context, final String url, final Map<String, String> map, final ResultListener listener) {
//        map.put("pubversion", Device.getCurrentAppVersionName(context) + "");
//        map.put("pubdevice", Device.getDeviceId(context));
//        map.put("pubplatform", "android");

//        String memberKey = PrefUtils.getString(context, "pubMemberKey", "");
//        if (!TextUtils.isEmpty(memberKey)) {
//            map.put("pubMemberKey", memberKey);
//        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    Log.d("resp-url", url);
                    Log.d("resp-map", map.toString());
                    Log.d("resp-str", s);
                    listener.onSuccess(s);
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onFail(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String errorMsg = VolleyErrorHelper.getMessage(volleyError, context);
                Log.d("resp-url", url);
                Log.d("resp-map", map.toString());
                Log.d("resp-errorMsg", errorMsg);
                listener.onFail(errorMsg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
                StringBuffer sb = new StringBuffer();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey().toString();
                    String val = entry.getValue().toString();
                    sb.append(key + "--" + val + ",");
                }
                return map;
            }


            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data, CHAR_SET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1, 1.0f));// 设置超时时间,取消自动重试
        return request;
    }


    /**
     * post 请求
     *
     * @param context
     * @param url
     * @param map
     * @param listener
     * @param bean
     * @return
     */
    public static Request<?> volleyPostForVesion(final Context context, final String url, final Map<String, String> map, final ResultListener listener, final Object bean) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    if (s.indexOf("null{") >= 0) {
                        s = s.replace("null{", "{");
                    }
                    Log.d("resp-url", url);
                    Log.d("resp-map", map.toString());
                    Log.d("resp-str", s);
                    JSONObject json = new JSONObject(s);
                    Object obj = JSONParser.toObject(json.toString(), bean);
                    listener.onSuccess(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String errorMsg = VolleyErrorHelper.getMessage(volleyError, context);
                Log.d("resp-url", url);
                Log.d("resp-map", map.toString());
                Log.d("resp-errorMsg", errorMsg);
                listener.onFail(errorMsg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
                StringBuffer sb = new StringBuffer();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey().toString();
                    String val = entry.getValue().toString();
                    sb.append(key + "--" + val + ",");
                }
                return map;
            }


            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data, CHAR_SET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1, 1.0f));// 设置超时时间,取消自动重试
        return request;
    }


    /**
     * get 请求
     *
     * @param context
     * @param url
     * @param listener
     * @param bean
     * @return
     */
    public static Request<?> volleyGet(final Context context, final String url, final ResultListener listener, final Object bean) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    Log.d("resp-url", url);
                    Log.d("resp-str", s);
                    JSONObject json = new JSONObject(s);
                    Object obj = JSONParser.toObject(json.toString(), bean);
                    listener.onSuccess(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String errorMsg = VolleyErrorHelper.getMessage(volleyError, context);
                Log.d("resp-url", url);
                Log.d("resp-errorMsg", errorMsg);
                listener.onFail(errorMsg);
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data, CHAR_SET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1, 1.0f));// 设置超时时间,取消自动重试
        return request;
    }

}
