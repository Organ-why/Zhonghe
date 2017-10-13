package com.zhonghe.shiangou.http;

import android.content.Context;
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
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.baseres.BaseRes;
import com.zhonghe.shiangou.data.bean.AddressInfo;
import com.zhonghe.shiangou.data.bean.AddressSelectInfo;
import com.zhonghe.shiangou.data.bean.CartGoods;
import com.zhonghe.shiangou.data.bean.CategoryChild;
import com.zhonghe.shiangou.data.bean.CategoryParent;
import com.zhonghe.shiangou.data.bean.CharPay;
import com.zhonghe.shiangou.data.bean.ConfirmRspInfo;
import com.zhonghe.shiangou.data.bean.ExchangeResultInfo;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.data.bean.GoodsdetailInfo;
import com.zhonghe.shiangou.data.bean.HomeData;
import com.zhonghe.shiangou.data.bean.LogisticsInfo;
import com.zhonghe.shiangou.data.bean.OrderInfo;
import com.zhonghe.shiangou.data.bean.OrderUnline;
import com.zhonghe.shiangou.data.bean.PointDetailInfo;
import com.zhonghe.shiangou.data.bean.PointGoodsInfo;
import com.zhonghe.shiangou.data.bean.PointOrderInfo;
import com.zhonghe.shiangou.data.bean.RefundsDetailInfo;
import com.zhonghe.shiangou.data.bean.RefundsItemInfo;
import com.zhonghe.shiangou.data.bean.RemarkInfo;
import com.zhonghe.shiangou.data.bean.ShopImgInfo;
import com.zhonghe.shiangou.data.bean.ShopInfo;
import com.zhonghe.shiangou.data.bean.ShopRemarkInfo;
import com.zhonghe.shiangou.data.bean.ShopTypeInfo;
import com.zhonghe.shiangou.data.bean.UnlineHomeInfo;
import com.zhonghe.shiangou.data.bean.UnlineShopDetailInfo;
import com.zhonghe.shiangou.data.bean.UserInfo;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.utile.JSONParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.zhonghe.shiangou.system.constant.CstProject.URL_PRO;
import static com.zhonghe.shiangou.system.constant.CstProject.URL_PRO_UNLINE;

/**
 * Created by whyang on 2017/8/10.
 */

public class HttpUtil {
    public static String APP_DOWNLOAD_URL = "http://www.shiangou.com.cn/Android/app-release.apk";
    // 首页信息
    public static String URL_HomeData = URL_PRO + "app/index.php";
    //
    public static String URL_VersionCode = URL_PRO + "app/edition/index.php";
    //积分
    public static String URL_PointData = URL_PRO + "app/exchange/index.php";
    //积分详情
    public static String URL_PointDetail = URL_PRO + "app/exchange/goods_desc.php";
    //积分兑换
    public static String URL_PointExchange = URL_PRO + "app/exchange/exchange.php";
    //积分兑换记录
    public static String URL_PointRecordList = URL_PRO + "app/exchange/read.php";
    //删除兑换记录
    public static String URL_PointDel = URL_PRO + "app/exchange/delete.php";
    //线下商城首页
    public static String URL_UnlineHome = URL_PRO_UNLINE + "Home/index";
    //商户详情
    public static String URL_UnlineShopDetail = URL_PRO_UNLINE + "Details/show/";
    //商户所有评论列表
    public static String URL_UnlineShopRemarkList = URL_PRO_UNLINE + "Comment/getlist";
    //商户提交评论
    public static String URL_UnlineShopSubmitRemark = URL_PRO_UNLINE + "Comment/postinfo";
    //商户列表
    public static String URL_UnlineShopList = URL_PRO_UNLINE + "Home/fication";
    //商户搜索
    public static String URL_UnlineShopSearch = URL_PRO_UNLINE + "Home/searchShop";
    //商户列表 -子分类
    public static String URL_UnlineShopListCategory = URL_PRO_UNLINE + "Home/category";
    //商户图片
    public static String URL_UnlineShopImg = URL_PRO_UNLINE + "Details/photolist";
    //用户信息---积分
    public static String URL_UserMSGPoint = URL_PRO_UNLINE + "UserInfo/getbase";
    //线下支付---alipay
    public static String URL_UNLINEPAY_ALI = URL_PRO_UNLINE + "Payment/index";
    //线下订单
    public static String URL_UnlineOrder = URL_PRO_UNLINE + "OfflineOrder/userorder";

    //分类
    public static String URL_CategoryParent = URL_PRO + "app/type/ding.php";
    public static String URL_CategoryChild = URL_PRO + "app/type/children.php";
    //注册
    public static String URL_Register = URL_PRO + "app/user/register.php";
    //忘记密码
    public static String URL_Forget = URL_PRO + "app/password/forget.php";
    //修改密码
    public static String URL_ChangePWD = URL_PRO + "app/password/update.php";
    //手机验证码
    public static String URL_GetPhoneCode = URL_PRO + "app/user/phone.php";
    //登录
    public static String URL_Login = URL_PRO + "app/user/login.php";
    //用户信息
    public static String URL_UserMSG = URL_PRO + "app/private/index.php";
    //修改昵称
    public static String URL_ChangNickName = URL_PRO + "app/private/nickname.php";
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
    //支付
    public static String URL_Pay = URL_PRO + "app/goods/Zhifu.php";
    //支付
    public static String URL_PayAli = URL_PRO + "app/alipay/index.php";
    //订单列表
    public static String URL_OrderList = URL_PRO + "app/orderindex/index.php";
    //订单取消
    public static String URL_OrderCancel = URL_PRO + "app/orderindex/cancel.php";
    //订单删除
    public static String URL_OrderDel = URL_PRO + "app/orderindex/delete.php";
    //提醒发货
    public static String URL_OrderRemind = URL_PRO + "app/aftersale/remind.php";
    //物流
    public static String URL_LogisticsDetail = URL_PRO + "app/logistics/alideliver.php";
    //退货
    public static String URL_RefundsGoods = URL_PRO + "app/aftersale/salesreturn.php";
    //退货列表
    public static String URL_RefundsList = URL_PRO + "app/aftersale/index.php";
    //退货商品详情
    public static String URL_RefundsDetail = URL_PRO + "app/aftersale/details.php";
    //评价商品
    public static String URL_CommentGoods = URL_PRO + "app/evaluate/comment.php";
    //评价列表
    public static String URL_CommentList = URL_PRO + "app/evaluate/comment_list.php";
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
    //图片上传
    public static String URL_IMGUp = URL_PRO + "app/evaluate/upload.php";

    //商品收藏
    public static String URL_GoodsUnFollow = URL_PRO + "app/collection/delete.php";
    public static String URL_GoodsFollow = URL_PRO + "app/collection/add.php";
    public static String URL_GoodsFollowList = URL_PRO + "app/collection/link.php";


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
        Request<?> request = volleyPost(context, URL_GetPhoneCode, map, listener, String.class);
        return request;
    }

    /**
     * getVersionCode
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getVersionCode(Context context, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        Request<?> request = volleyPost(context, URL_VersionCode, map, listener, String.class);
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
     * 修改密码
     *
     * @param context
     * @param oldpwd
     * @param newpwd
     * @param listener
     * @return
     */
    public static Request<?> getChangePWD(Context context, String oldpwd, String newpwd, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("password", newpwd);
        map.put("upassword", newpwd);
        map.put("oldpassword", oldpwd);
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_ChangePWD, map, listener, null);
        return request;
    }

    /**
     * 忘记密码
     *
     * @param context
     * @param phone
     * @param code
     * @param pwd
     * @param listener
     * @return
     */
    public static Request<?> getForgetPWD(Context context, String phone, String code, String pwd, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", pwd);
        map.put("upassword", pwd);
        map.put("ident", code);
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_Forget, map, listener, null);
        return request;
    }

    /**
     * @param context
     * @param url
     * @param listener
     * @return
     */
    public static Request<?> getHeaderUp(Context context, String url, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("pic", url);
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_HeaderUp, map, listener, null);
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
     * 修改昵称
     *
     * @param context
     * @param nickName
     * @param listener
     * @return
     */
    public static Request<?> getChangeNickName(Context context, String nickName, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", nickName);
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        map.put("ident", code);
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_ChangNickName, map, listener, String.class);
        return request;
    }

    /**
     * 用户信息
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getUserMSG(Context context, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        map.put("ident", code);
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_UserMSG, map, listener, UserInfo.class);
        return request;
    }

    /**
     * 用户信息---积分
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getUserMSGPoint(Context context, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        map.put("ident", code);
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_UserMSGPoint, map, listener, UserInfo.class);
        return request;
    }

    /**
     * 线下支付 ali
     *
     * @param context
     * @param total_money
     * @param actual_money
     * @param actual_points
     * @param merchant_id
     * @param pay_type      付款类型 1为支付宝，2为微信，3为现金(即只消费积分)
     * @param listener
     * @return
     */
    public static Request<?> getUnlinePay_Ali(Context context, double total_money, double actual_money, double actual_points,
                                              String merchant_id, int pay_type, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("total_money", Util.formatPrice(total_money));
        map.put("actual_money", Util.formatPrice(actual_money));
        map.put("actual_points", Util.formatPrice(actual_points));
        map.put("merchant_id", merchant_id);
        map.put("pay_type", String.valueOf(pay_type));

//        map.put("ident", code);
//        BaseRes<String> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_UNLINEPAY_ALI, map, listener, CharPay.DataBean.class);
        return request;
    }

    /**
     * 线下订单
     *
     * @param context
     * @param cursize
     * @param curpage
     * @param listener
     * @return
     */
    public static Request<?> getUnlineOrder(Context context, int cursize, int curpage, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("curpage ", curpage + "");
        map.put("cursize", cursize + "");
//        map.put("ident", code);
//        BaseRes<String> res = new BaseRes<>();
        Type bean = new TypeToken<List<OrderUnline>>() {

        }.getType();
        Request<?> request = volleyPost(context, URL_UnlineOrder, map, listener, bean);
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
        Request<?> request = volleyPost(context, URL_SearchHot, map, listener, bean);
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
        if (ProjectApplication.mUser != null) {
            map.put("user_id", ProjectApplication.mUser.getUser_id());
        } else {
            map.put("user_id", "0");
        }
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
     * 收藏列表
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getFollowGoodsList(Context context, int curpage, int cursize, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", ProjectApplication.mUser.getUser_id());
        map.put("curpage", String.valueOf(curpage));
        map.put("cursize", String.valueOf(cursize));

//        BaseRes<HomeData> res = new BaseRes<>();
        Type bean = new TypeToken<List<GoodsInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_GoodsFollowList, map, listener, bean);
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
        map.put("goods_id", Util.strArrayToStr(goodsId));
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
        Request<?> request = volleyPost(context, URL_CategoryParent, map, listener, bean);
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
    public static Request<?> getConfirmGoods(Context context, List<String> cat_id, int type, String goods_attr_id, int goods_count, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type + "");
        map.put("goods_attr_id", goods_attr_id + "");
        map.put("goods_count", goods_count + "");
        map.put("cart_id", Util.strArrayToStr(cat_id));
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
        map.put("cart_id", Util.strArrayToStr(cat_id));
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_Order, map, listener, String.class);
        return request;
    }


    /**
     * 订单列表
     *
     * @param context
     * @param type
     * @param curpage
     * @param cursize
     * @param listener
     * @return
     */
    public static Request<?> getOrderList(Context context, String type, int curpage, int cursize, ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
        map.put("curpage", String.valueOf(curpage));
        map.put("cursize", String.valueOf(cursize));
        map.put("type", String.valueOf(type));
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
        Type bean = new TypeToken<List<OrderInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_OrderList, map, listener, bean);
        return request;
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @param resultListener
     * @return
     */
    public static Request<?> getCancelOrder(Context context, String orderId, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
        map.put("order_sn", orderId);
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<OrderInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_OrderCancel, map, resultListener, null);
        return request;
    }

    /**
     * 删除订单
     *
     * @param context
     * @param orderId
     * @param resultListener
     * @return
     */
    public static Request<?> getDelOrder(Context context, String orderId, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
        map.put("order_sn", orderId);
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<OrderInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_OrderDel, map, resultListener, null);
        return request;
    }

    /**
     * @param context
     * @param orderId
     * @param type           1 提醒发货 2 确认收货
     * @param resultListener
     * @return
     */
    public static Request<?> getOrderRemind(Context context, String orderId, int type, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
        map.put("type", type + "");
        map.put("order_sn", orderId);
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<OrderInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_OrderRemind, map, resultListener, String.class);
        return request;
    }

    /**
     * 物流
     *
     * @param context
     * @param resultListener
     * @return
     */
    public static Request<?> getLogisticsDetail(Context context, String order_sn, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
//        运单号：number
//        快递公司代码：type
//        快递公司名 ： express
        map.put("order_sn", order_sn + "");
//        map.put("number", number + "");
//        map.put("type", type + "");
//        map.put("express", express + "");
//        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<OrderInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_LogisticsDetail, map, resultListener, LogisticsInfo.class);
        return request;
    }

    /**
     * 退货
     *
     * @param context
     * @param order_sn
     * @param imgs
     * @param explain
     * @param resultListener
     * @return
     */
    public static Request<?> getSubmitRefunds(Context context, String order_sn, List<String> imgs, String explain, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
        map.put("order_sn", order_sn);
        map.put("img", Util.strArrayToStr(imgs));
        map.put("explain", explain);
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<OrderInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_RefundsGoods, map, resultListener, null);
        return request;
    }

    /**
     * 退货列表
     *
     * @param context
     * @param curpage
     * @param cursize
     * @param resultListener
     * @return
     */
    public static Request<?> getRefundsList(Context context, int curpage, int cursize, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
        map.put("cursize", cursize + "");
        map.put("curpage", curpage + "");
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
        Type bean = new TypeToken<List<RefundsItemInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_RefundsList, map, resultListener, bean);
        return request;
    }

    /**
     * 退货详情
     *
     * @param context
     * @param refundsId
     * @param resultListener
     * @return
     */
    public static Request<?> getRefundsDetail(Context context, String refundsId, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("return_id", refundsId);
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
        Request<?> request = volleyPost(context, URL_RefundsDetail, map, resultListener, RefundsDetailInfo.class);
        return request;
    }

    /**
     * 评论商品
     *
     * @param context
     * @param goodsId
     * @param content
     * @param rank
     * @param images
     * @param user_name
     * @param resultListener
     * @return
     */
    public static Request<?> getCommentGoods(Context context, String goodsId, String content, String rank,
                                             List<String> images, String user_name, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
//        map.put("goods_id", goods_id);
        map.put("goods_id", goodsId);
        map.put("content", content);
        map.put("rank", rank);
        String str = Util.strArrayToStr(images);
        map.put("img", str);
        map.put("user_name", user_name);
        map.put("user_id", ProjectApplication.mUser.getUser_id());

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<OrderInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_CommentGoods, map, resultListener, null);
        return request;
    }

    /**
     * 评论列表
     *
     * @param context
     * @param goodsId
     * @param resultListener
     * @return
     */
    public static Request<?> getCommentList(Context context, String goodsId, int curpage, int cursize, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("cursize", cursize + "");
        map.put("curpage", curpage + "");
//        BaseRes<HomeData> res = new BaseRes<>();
        Type bean = new TypeToken<List<RemarkInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_CommentList, map, resultListener, bean);
        return request;
    }

    /**
     * 支付
     *
     * @param context
     * @param listener
     * @return
     */
    public static Request<?> getPay(Context context, String order_code, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("cart_id", Util.strArrayToStr(cat_id));
        map.put("order_sn", order_code);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_Pay, map, listener, CharPay.class);
        return request;
    }

    /**
     * 支付凭证ali
     *
     * @param context
     * @param order_code
     * @param listener
     * @return
     */
    public static Request<?> getPayAli(Context context, String order_code, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("cart_id", Util.strArrayToStr(cat_id));
        map.put("ordersn", order_code);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_PayAli, map, listener, String.class);
        return request;
    }

    /**
     * 积分商城
     * type 四种值  0 ：首页列表 ，1：零元购，  2：日常家居， 3 新品  4 所有商品
     *
     * @param context
     * @param curpage
     * @param cursize
     * @param listener
     * @return
     */
    public static Request<?> getPointGoods(Context context, int type, int curpage, int cursize, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("cart_id", Util.strArrayToStr(cat_id));
        map.put("type", String.valueOf(type));
        map.put("curpage", String.valueOf(curpage));
        map.put("cursize", String.valueOf(cursize));
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_PointData, map, listener, PointGoodsInfo.class);
        return request;
    }

    /**
     * 积分详情
     *
     * @param context
     * @param goodsId
     * @param listener
     * @return
     */
    public static Request<?> getPointGoodsDetail(Context context, String goodsId, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("cart_id", Util.strArrayToStr(cat_id));
        map.put("goods_id", goodsId);
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_PointDetail, map, listener, PointDetailInfo.class);
        return request;
    }

    /**
     * 积分兑换记录
     *
     * @param context
     * @param curpage
     * @param cursize
     * @param listener
     * @return
     */
    public static Request<?> getPointRecordList(Context context, int curpage, int cursize, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("cart_id", Util.strArrayToStr(cat_id));
        map.put("curpage", String.valueOf(curpage));
        map.put("cursize", String.valueOf(cursize));
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Type bean = new TypeToken<List<PointOrderInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_PointRecordList, map, listener, bean);
        return request;
    }

    /**
     * 删除兑换记录
     *
     * @param context
     * @param orderId
     * @param listener
     * @return
     */
    public static Request<?> getDelExchangeRecord(Context context, String orderId, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("cart_id", Util.strArrayToStr(cat_id));
        map.put("order_id", orderId);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Type bean = new TypeToken<List<PointOrderInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_PointDel, map, listener, null);
        return request;
    }

    /**
     * 积分兑换商品
     *
     * @param context
     * @param goodsId
     * @param addressId
     * @param listener
     * @return
     */
    public static Request<?> getExchangeGoods(Context context, String goodsId, String addressId, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
//        map.put("cart_id", Util.strArrayToStr(cat_id));
        map.put("goods_id", goodsId);
        map.put("address_id", addressId);
        map.put("user_id", ProjectApplication.mUser.getUser_id());
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_PointExchange, map, listener, ExchangeResultInfo.class);
        return request;
    }

    /**
     * 线下商城 周边商户
     *
     * @param context
     * @param curpage
     * @param lon
     * @param lat
     * @param listener
     * @return
     */
    public static Request<?> getUnlineHome(Context context, int curpage, double lon, double lat, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("curpage", curpage + "");
        map.put("grade", "0");
        map.put("lon", String.valueOf(lon));
        map.put("lat", String.valueOf(lat));
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_UnlineHome, map, listener, UnlineHomeInfo.class);
        return request;
    }

    /**
     * 商户详情
     *
     * @param context
     * @param merchant_id
     * @param listener
     * @return
     */
    public static Request<?> getUnlineShopDetail(Context context, String merchant_id, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("merchant_id", merchant_id);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
//        Type bean = new TypeToken<List<CategoryChild>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_UnlineShopDetail, map, listener, UnlineShopDetailInfo.class);
        return request;
    }

    /**
     * 商户列表
     *
     * @param context
     * @param type_id
     * @param cat_id
     * @param lat
     * @param lon
     * @param grade    1 评分 0 位置
     * @param curpage
     * @param listener
     * @return
     */
    public static Request<?> getUnlineShopList(Context context, String type_id,
                                               String cat_id, double lat, double lon, int grade, int curpage,
                                               final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("type_id", type_id);
        map.put("cat_id", cat_id);
        map.put("lat", String.valueOf(lat));
        map.put("lon", String.valueOf(lon));
        //1 评分 0 位置
        map.put("grade", String.valueOf(grade));
        map.put("curpage", String.valueOf(curpage));
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Type bean = new TypeToken<List<ShopInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_UnlineShopList, map, listener, bean);
        return request;
    }

    /**
     * 搜索商户
     *
     * @param context
     * @param keywords
     * @param lat
     * @param lon
     * @param curpage
     * @param listener
     * @return
     */
    public static Request<?> getUnlineShopSearch(Context context, String keywords,
                                                 double lat, double lon, int curpage,
                                                 final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("lat", String.valueOf(lat));
        map.put("lon", String.valueOf(lon));
        map.put("curpage", String.valueOf(curpage));
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Type bean = new TypeToken<List<ShopInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_UnlineShopSearch, map, listener, bean);
        return request;
    }

    /**
     * 评论商户
     *
     * @param context
     * @param merchant_id
     * @param grade
     * @param imgs
     * @param details
     * @param resultListener
     * @return
     */
    public static Request<?> getSubmitShopRemark(Context context, String merchant_id, float grade, List<String> imgs, String details, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("grade", String.valueOf(grade));
        map.put("merchant_id", merchant_id);
        map.put("photo", Util.strArrayToStr(imgs));
        map.put("details", details);

//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken<List<OrderInfo>>() {
//        }.getType();
        Request<?> request = volleyPost(context, URL_UnlineShopSubmitRemark, map, resultListener, null);
        return request;
    }

    /**
     * 商户分类
     *
     * @param context
     * @param type_id
     * @param listener
     * @return
     */
    public static Request<?> getUnlineShopType(Context context, String type_id,
                                               final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("type_id", type_id);
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Type bean = new TypeToken<List<ShopTypeInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_UnlineShopListCategory, map, listener, bean);
        return request;
    }

    /**
     * 商户图片
     *
     * @param context
     * @param merchant_id
     * @param listener
     * @param curpage
     * @param cursize
     * @return
     */
    public static Request<?> getUnlineShopImg(Context context, String merchant_id, int curpage, int cursize,
                                              final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("merchant_id", merchant_id);
        map.put("curpage", curpage + "");
        map.put("cursize", cursize + "");
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Type bean = new TypeToken<List<ShopImgInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_UnlineShopImg, map, listener, bean);
        return request;
    }

/////////////////////////////////////////////////////////网络基本请求////////////////////////////////////////////////////////////////////////


    /**
     * 商户评论列表
     *
     * @param context
     * @param merchant_id
     * @param curpage
     * @param cursize
     * @param listener
     * @return
     */
    public static Request<?> getUnlineShopRemarkList(Context context, String merchant_id, int curpage, int cursize, final ResultListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("merchant_id", merchant_id);
        map.put("curpage ", curpage + "");
        map.put("cursize", cursize + "");
//        BaseRes<HomeData> res = new BaseRes<>();
//        Type bean = new TypeToken< BaseRes<HomeData>>(){}.getType();
        Type bean = new TypeToken<List<ShopRemarkInfo>>() {
        }.getType();
        Request<?> request = volleyPost(context, URL_UnlineShopRemarkList, map, listener, bean);
        return request;
    }

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
//        map.put("secret","97ccf08eba886673de8e3a378de8d6b3");
//        String secret = ProjectApplication.mUser.getToken_secret();
        map.put("uid", ProjectApplication.mUser != null ? ProjectApplication.mUser.getUser_id() : "0");
        map.put("secret", ProjectApplication.mUser != null ? ProjectApplication.mUser.getToken_secret() : CstProject.DEFAULT_SECRET);

//        String memberKey = PrefUtils.getString(context, Const.MEMBER_KEY, "");
//        if (!TextUtils.isEmpty(memberKey)) {
//            map.put("pubMemberKey", memberKey);
//        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                UtilLog.d("resp-url:" + url);
                UtilLog.d("resp-map:" + map.toString());
                UtilLog.d("resp-str:" + s);
                try {
                    JSONObject json = new JSONObject(s);
                    BaseRes obj = (BaseRes) JSONParser.toObject(json.toString(), BaseRes.class);
                    if (obj.getState() != 1) {
                        switch (obj.getState()) {
                            case 14:
//                                ProDispatcher.goLoginActivity(context);
                                ProjectApplication.mUser = null;
                                ProjectApplication.mPrefrence.setUserId(null);
                                ProDispatcher.sendNeedLoginBroadcast(context);
                                ProDispatcher.sendLogoutBroadcast(context);
                                listener.onFail(context.getString(R.string.error_login_needlog));
                                ProDispatcher.goLoginActivity(context);
//                                listener.onFail(obj.getMsg());
                                break;
                            default:
                                listener.onFail(obj.getMsg());
                        }
                    } else if (bean != null) {
                        String strdata = JSONParser.toString(obj.getDatas());
                        Object data = JSONParser.toObject(strdata, bean);
                        listener.onSuccess(data);
                    } else {
                        listener.onSuccess(null);
                    }
                } catch (Exception e) {
                    UtilLog.d("try_catch" + s);
                    e.printStackTrace();
                    listener.onFail(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String errorMsg = VolleyErrorHelper.getMessage(volleyError, context);
                UtilLog.d("resp-url:" + url);
                UtilLog.d("resp-map:" + map.toString());
                UtilLog.d("resp-errorMsg:" + errorMsg);
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
                    UtilLog.d("resp-url:" + url);
                    UtilLog.d("resp-str:" + s);
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
                UtilLog.d("resp-url:" + url);
                UtilLog.d("resp-errorMsg:" + errorMsg);
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
