package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.LogisticsInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 物流
 */
public class LogisticsActivity extends BaseTopActivity {


    @BindView(R.id.id_logistics_progress_tv)
    TextView idLogisticsProgressTv;
    @BindView(R.id.id_logistics_type_tv)
    TextView idLogisticsTypeTv;
    @BindView(R.id.id_logistics_code_tv)
    TextView idLogisticsCodeTv;
    @BindView(R.id.id_logistics_progress_ll)
    LinearLayout idLogisticsProgressLl;
    private String order_sn;
//    private String number;
//    private String type;
//    private String express;
    private LogisticsInfo mData;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_logistics);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.order_title_logistics);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        order_sn = intent.getStringExtra(CstProject.KEY.ID);
//        number = intent.getStringExtra(CstProject.KEY.VALUES1);
//        type = intent.getStringExtra(CstProject.KEY.VALUES2);
//        express = intent.getStringExtra(CstProject.KEY.VALUES3);
        getLogistics();
    }

    void getLogistics() {
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getLogisticsDetail(mContext,order_sn, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                mData = (LogisticsInfo) obj;
                setmData();

            }
        });
        addRequest(request);
    }

    void setmData() {
        //1 在途中 2 派件中 3 已签收 4派送失败
        switch (mData.getDeliverystatus()) {
            case "1":
                idLogisticsProgressTv.setText("在途中");
                break;
            case "2":
                idLogisticsProgressTv.setText("派件中");
                break;
            case "3":
                idLogisticsProgressTv.setText("已签收");
                break;
            case "4":
                idLogisticsProgressTv.setText("派送失败");

                break;
        }
        idLogisticsTypeTv.setText(mData.getExpress());
        idLogisticsCodeTv.setText(mData.getNumber());
        for (int i = 0; i < mData.getList().size(); i++) {
            LogisticsInfo.ListBean info = mData.getList().get(i);
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_logistics_progress, null);
            TextView title = (TextView) view.findViewById(R.id.id_logistics_progress_title_tv);
            title.setText(info.getStatus());
            TextView time = (TextView) view.findViewById(R.id.id_logistics_progress_time_tv);
            time.setText(info.getTime());
            ImageView dot = (ImageView) view.findViewById(R.id.id_view_dot);
            View downview = view.findViewById(R.id.id_view_down);
            View upview = view.findViewById(R.id.id_view_up);
            if (i == mData.getList().size() - 1) {
                downview.setVisibility(View.INVISIBLE);
            }
            if (i == 0) {
                dot.setImageResource(R.mipmap.icon_orange_dot);
                upview.setVisibility(View.INVISIBLE);
            }

            idLogisticsProgressLl.addView(view);
        }
    }

}
