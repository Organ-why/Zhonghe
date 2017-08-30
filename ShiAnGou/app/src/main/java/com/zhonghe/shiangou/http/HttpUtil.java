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
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.data.baseres.BaseRes;
import com.zhonghe.shiangou.data.bean.AddressInfo;
import com.zhonghe.shiangou.data.bean.AddressSelectInfo;
import com.zhonghe.shiangou.data.bean.CartGoods;
import com.zhonghe.shiangou.data.bean.CartInfo;
import com.zhonghe.shiangou.data.bean.CategoryChild;
import com.zhonghe.shiangou.data.bean.CategoryParent;
import com.zhonghe.shiangou.data.bean.ConfirmRspInfo;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.data.bean.GoodsdetailInfo;
import com.zhonghe.shiangou.data.bean.HomeData;
import com.zhonghe.shiangou.data.bean.StringInfo;
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;
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
 * Created by whyang on 2017/8/10.
 */

public class HttpUtil {
    // 首页信息
    public static String URL_HomeData = URL_PRO + "app/index.php";

    //分类
    public static String URL_CategoryParent = URL_PRO + "app/type/ding.php";
    public static String URL_CategoryChild = URL_PRO + "app/type/children.php";
    //注册
    public static String URL_Register = URL_PRO + "app/user/register.php";
    //手机验证码
    public static String URL_GetPhoneCode = URL_PRO + "app/user/phone.php";
    //登录
    public static String URL_Login = URL_PRO + "app/user/login.php";
    //商品详情
    public static String URL_GoodsDetail = URL_PRO + "app/goods/goods.php";
    //搜索搜索
    public static String URL_SearchHot = URL_PRO + "app/goods/goods_read.php";
    //搜索
    public static String URL_Search = URL_PRO + "app/goods/goods_select.php";
    //添加购物车
    public static String URL_AddCart = URL_PRO + "app/cart/add.php";
    //删除购物车
    public static String URL_DeleteCart = URL_PRO + "app/cart/delete.php";
    //修改购物车
    public static String URL_ChangeCart = URL_PRO + "app/cart/update.php";
    //购物车列表
    public static String URL_CartList = URL_PRO + "app/cart/show.php";
    //确认商品
    public static String URL_ConfirmGoods = URL_PRO + "app/goods/commodity.php";
    //订单生成
    public static String URL_Order = URL_PRO + "app/orderindex/generate.php";
    //订单列表
    public static String URL_OrderList = URL_PRO + "orderindex.php";
    //地址列表
    public static String URL_Address = URL_PRO + "app/address/show.php";
    //地址 省市县选择
    public static String URL_AddressSelect = URL_PRO + "app/address/address.php";
    //地址设置默认
    public static String URL_AddressSetDefault = URL_PRO + "app/address/default.php";
    //地址删除
    public static String URL_AddressDelete = URL_PRO + "app/address/deleteaddress.php";
    //添加地址
    public static String URL_AddAddress = URL_PRO + "app/address/add.php";
    //编辑地址
    public static String URL_UpdataAddress = URL_PRO + "app/address/update.php";
    //密码
    public static String URL_Password = URL_PRO + "password.php";
    //头像
    public static String URL_HeaderUp = URL_PRO + "app/private/private.php";

    //商品收藏
    public static String URL_GoodsUnFollow = URL_PRO + "app/collection/delete.php";
    public static String URL_GoodsFollow = URL_PRO + "app/collection/add.php";


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
        Request<?> request = volleyPost(context, URL_GetPhoneCode, map, listener, null);
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
        map.put("upassword", pwd);
        map.put("ident", code);
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_Register, map, listener, null);
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
     * 商品列表
     *
     * @param context
     * @param cat_id
     * @param keywords
     * @param cursize
     * @param curpage
     * @param orderby
     * @param listener
     * @return
     */
    public static Request<?> getSearch(Context context, String cat_id,
                                       String keywords, int cursize, int curpage, String orderby, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("cat_id", cat_id);
        map.put("keywords", keywords);
        map.put("cursize", cursize + "");
        map.put("curpage", curpage + "");
        map.put("orderby", orderby);
//        BaseRes<HomeData> res = new BaseRes<>();
        Type bean = new TypeToken<List<GoodsInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_Search, map, listener, bean);
        return request;
    }

    /**
     * 热门
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getSearchHost(Context context, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        BaseRes<HomeData> res = new BaseRes<>();
        Type bean = new TypeToken<List<GoodsInfo>>() {
        }.getType();
        Request<?> request = volleyGet(context, URL_SearchHot, listener, bean);
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
     * 商品收藏操作
     *
     * @param context
     * @param goods_id
     * @param listener
     * @return
     */
    public static Request<?> getFollowGoods(Context context, String goods_id, boolean isFollow, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("goods_id", goods_id);
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        String url = isFollow ? URL_GoodsFollow : URL_GoodsUnFollow;
        Request<?> request = volleyPost(context, url, map, listener, null);
        return request;
    }

    /**
     * 地址列表
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getAddressList(Context context, int curpage, int cursize, ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
        map.put("curpage", String.valueOf(curpage));
        map.put("cursize", String.valueOf(cursize));
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
        Type bean = new TypeToken<List<AddressInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_Address, map, listener, bean);
        return request;
    }

    /**
     * 三级地址列表
     *
     * @param context
     * @param areaid
     * @param listener
     * @return
     */
    public static Request<?> getAddressSelect(Context context, String areaid, ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
        map.put("areaid", areaid);

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<AddressInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_AddressSelect, map, listener, AddressSelectInfo.class);
        return request;
    }

    /**
     * 设置默认地址
     *
     * @param context
     * @param address_id
     * @param listener
     * @return
     */
    public static Request<?> getAddressSetDefault(Context context, String address_id, ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", ProjectApplication.mUser.getUser_id());
        map.put("address_id", address_id);

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<AddressInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_AddressSetDefault, map, listener, null);
        return request;
    }

    /**
     * 删除地址
     *
     * @param context
     * @param address_id
     * @param listener
     * @return
     */
    public static Request<?> getAddressDelete(Context context, String address_id, ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", ProjectApplication.mUser.getUser_id());
        map.put("address_id", address_id);

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<AddressInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_AddressDelete, map, listener, null);
        return request;
    }


    /**
     * 添加地址
     *
     * @param context
     * @param area_address
     * @param consignee    收货人
     * @param mobile
     * @param province
     * @param city
     * @param district
     * @param address
     * @param listener
     * @return
     */
    public static Request<?> getAddressAdd(Context context, String area_address,
                                           String consignee, String mobile, String province,
                                           String city, String district, String address,
                                           ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", ProjectApplication.mUser.getUser_id());
        map.put("area_address", area_address);
        map.put("consignee", consignee);
        map.put("mobile", mobile);
        map.put("province", province);
        map.put("city", city);
        map.put("district", district);
        map.put("address", address);

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<AddressInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_AddAddress, map, listener, null);
        return request;
    }

    /**
     * 购物车列表
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getCartList(Context context, int curpage, int cursize, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", ProjectApplication.mUser.getUser_id());
        map.put("curpage", String.valueOf(curpage));
        map.put("cursize", String.valueOf(cursize));
//        BaseRes<HomeData> res = new BaseRes<>();
        Type bean = new TypeToken<List<CartGoods>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_CartList, map, listener, bean);
        return request;
    }

    /**
     * 添加购物车
     *
     * @param context
     * @param goodsId
     * @param attr_id
     * @param goodsCount
     * @param listener
     * @return
     */
    public static Request<?> getAddCart(Context context, String goodsId, String attr_id, String goodsCount, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("user_id", ProjectApplication.mUser.getUser_id());
        map.put("goods_number", goodsCount);
        map.put("goods_attr_id", attr_id);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Request<?> request = volleyPost(context, URL_AddCart, map, listener, null);
        return request;
    }

    /**
     * 删除购物车
     *
     * @param context
     * @param goodsId
     * @param listener
     * @return
     */
    public static Request<?> getDeleteCart(Context context, List<String> goodsId, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("goods_id", Utilm.strArrayToStr(goodsId));
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        map.put("goods_number", goodsCount);
//        map.put("goods_attr_id", attr_id);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Request<?> request = volleyPost(context, URL_DeleteCart, map, listener, null);
        return request;
    }

    /**
     * 修改购物车数量
     *
     * @param context
     * @param goodsId
     * @param listener
     * @return
     */
    public static Request<?> getChangeCart(Context context, String goodsId, int count, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId.toString());
        map.put("goods_count", count + "");
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        map.put("goods_number", goodsCount);
//        map.put("goods_attr_id", attr_id);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Request<?> request = volleyPost(context, URL_ChangeCart, map, listener, null);
        return request;
    }

    /**
     * 分类 父级
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getCategoryParent(Context context, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("user_id", "22");
//        BaseRes<HomeData> res = new BaseRes<>();
        Type bean = new TypeToken<List<CategoryParent>>() {
        }.getType();
        Request<?> request = volleyGet(context, URL_CategoryParent, listener, bean);
        return request;
    }

    /**
     * 分类 子级
     *
     * @param context
     * @param childId
     * @param listener
     * @return
     */
    public static Request<?> getCategoryChild(Context context, String childId, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("cat_id", childId);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Type bean = new TypeToken<List<CategoryChild>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_CategoryChild, map, listener, bean);
        return request;
    }

    /**
     * 确认商品
     *
     * @param context
     * @param cat_id
     * @param listener
     * @return
     */
    public static Request<?> getConfirmGoods(Context context, List<String> cat_id, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("cart_id", Utilm.strArrayToStr(cat_id));
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_ConfirmGoods, map, listener, ConfirmRspInfo.class);
        return request;
    }

    /**
     * 提交订单
     *
     * @param context
     * @param cat_id
     * @param listener
     * @return
     */
    public static Request<?> getSubmitOrder(Context context, List<String> cat_id, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("cart_id", Utilm.strArrayToStr(cat_id));
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_Order, map, listener, String.class);
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
//                    JSONObject json = new JSONObject(s);
//                    Object obj = JSONParser.toObject(json.toString(), bean);
//                    listener.onSuccess(obj);

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
