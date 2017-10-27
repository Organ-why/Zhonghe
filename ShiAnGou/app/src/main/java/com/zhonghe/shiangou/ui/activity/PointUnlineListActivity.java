package com.zhonghe.shiangou.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhonghe.lib_base.baseui.UIOptions;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.HomeData;
import com.zhonghe.shiangou.data.bean.ShopCatInfo;
import com.zhonghe.shiangou.data.bean.ShopInfo;
import com.zhonghe.shiangou.data.bean.ShopTypeInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.adapter.UnlineShopAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.PopListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhonghe.shiangou.system.constant.CstProject.KEY.KEY;

/**
 * 商户列表
 */
public class PointUnlineListActivity extends BaseTopActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {
    @BindView(R.id.id_default_listview)
    PullToRefreshListView idDefaultListview;
    @BindView(R.id.float_ll)
    LinearLayout floatLl;
    @BindView(R.id.title_user_ivb)
    ImageButton titleUserIvb;
    @BindView(R.id.title_msg_ivb)
    ImageButton titleMsgIvb;
    @BindView(R.id.id_category_title_tv)
    TextView idCategoryTitleTv;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    ImageView typeIv;
    ImageView orderbyIv;

    private int lastIndex;
    private PopListPresenter typePresenter;
    private PopListPresenter orderByPresenter;
    private TextView typeTv;
    private TextView orderByTv;

    private String shopId = "0";
    private List<ShopTypeInfo> typeList;
    private View floatView;
    private int curpage = 1;
    private String catId = "0";
    private int orderBy = 0;
    private List<ShopInfo> shopList;
    private UnlineShopAdapter adapter;

    @Override
    protected void initAppCustom() {
        setAppCustomLayout(R.layout.layout_custom_top);
    }

    @Override
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_SYSTEMBAR | UIOptions.UI_OPTIONS_APPBAR_CUSTIOM | UIOptions.UI_OPTIONS_CONTENT_CUSTOM;
    }

    @Override
    protected void initTop() {

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        shopId = intent.getStringExtra(CstProject.KEY.ID);
        rlTitle.setBackgroundResource(R.color.res_color_white);
        titleUserIvb.setImageResource(R.mipmap.common_nav_back);
        titleMsgIvb.setImageResource(R.mipmap.icon_exchange_record);
        idCategoryTitleTv.setText(R.string.search_unline_tip);
        idCategoryTitleTv.setBackgroundResource(R.drawable.circle_search_gray_bg);

        floatView = LayoutInflater.from(mContext).inflate(R.layout.layout_unline_list_float, null);
        typeIv = (ImageView) floatView.findViewById(R.id.id_type_iv);
        orderbyIv = (ImageView) floatView.findViewById(R.id.id_orderby_iv);
        typeTv = (TextView) floatView.findViewById(R.id.id_type_tv);
        orderByTv = (TextView) floatView.findViewById(R.id.id_orderby_tv);
        floatLl.addView(floatView);

        shopList = new ArrayList<>();
        typeList = new ArrayList<>();
        idDefaultListview.setOnRefreshListener(this);
        adapter = new UnlineShopAdapter(mContext, shopList);
        idDefaultListview.setAdapter(adapter);

        final List<String> list = new ArrayList<>();
        list.add("距离");
        list.add("评分");

        orderByPresenter = new PopListPresenter(mContext, list, floatView, orderbyIv, new PopListPresenter.OnPopItemClickListener() {
            @Override
            public void OnClickItem(String t, int position) {
                orderByTv.setText(t);
                orderBy = position;
                curpage = 1;
                getShopList(catId, ProjectApplication.mLocation.getLatitude(), ProjectApplication.mLocation.getLongitude(), orderBy);
            }
        });
        getShopCategory();
    }

    @OnClick({R.id.title_user_ivb, R.id.title_msg_ivb, R.id.id_category_title_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_user_ivb:
                finish();
                break;
            case R.id.title_msg_ivb:
                //兑换记录
                ProDispatcher.goSearchShopActivity(mContext);
                break;
            case R.id.id_category_title_tv:
                ProDispatcher.goSearchShopActivity(mContext);
                break;
            case R.id.id_default_tv:
                catId = typeList.get(0).getCat_id();
                getShopList(catId, ProjectApplication.mLocation.getLatitude(), ProjectApplication.mLocation.getLongitude(), orderBy);
                typeTv.setText(typeList.get(0).getCat_name());
                break;
            case R.id.id_type_ll:
                if (typePresenter != null)
                    typePresenter.show();
                break;
            case R.id.id_orderby_ll:
                orderByPresenter.show();
                break;
        }
    }

    void getShopCategory() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUnlineShopType(mContext, shopId, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                typeList = (List<ShopTypeInfo>) obj;
//                if (UtilList.getCount(typeList) == 0) {
//
//                    return;
//                }
                List<String> strings = new ArrayList<String>();
                for (ShopTypeInfo typeInfo :
                        typeList) {
                    strings.add(typeInfo.getCat_name());
                }
                if (catId.equals("0")) {
                    catId = typeList.get(0).getCat_id();
                    getShopList(catId, ProjectApplication.mLocation.getLatitude(), ProjectApplication.mLocation.getLongitude(), orderBy);
                    typeTv.setText(typeList.get(0).getCat_name());
                }
                typePresenter = new PopListPresenter(mContext, strings, floatView, typeIv, new PopListPresenter.OnPopItemClickListener() {
                    @Override
                    public void OnClickItem(String t, int position) {
                        typeTv.setText(t);
                        catId = typeList.get(position).getCat_id();
                        curpage = 1;
                        getShopList(catId, ProjectApplication.mLocation.getLatitude(), ProjectApplication.mLocation.getLongitude(), orderBy);
//                ProDispatcher.sendChangeCategoryBroadcast(mContext, list.get(position));
                    }
                });
            }
        });
        addRequest(request);
    }

    void getShopList(String catId, double lat, double lon, int grade) {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getUnlineShopList(mContext, shopId, catId, lat, lon, grade, curpage, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
                Util.toast(mContext, error);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                idDefaultListview.onRefreshComplete();
                shopList = (List<ShopInfo>) obj;
                if (curpage == 1) {
                    adapter.setList(shopList);
                } else {
                    adapter.addList(shopList);
                }
                if (shopList.size() > 0) {
                    curpage++;
                }
            }
        });
        addRequest(request);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        curpage = 1;
        getShopList(catId, ProjectApplication.mLocation.getLatitude(), ProjectApplication.mLocation.getLongitude(), orderBy);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

        getShopList(catId, ProjectApplication.mLocation.getLatitude(), ProjectApplication.mLocation.getLongitude(), orderBy);
    }
}
