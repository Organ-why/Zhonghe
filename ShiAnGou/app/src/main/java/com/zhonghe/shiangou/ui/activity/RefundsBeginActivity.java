package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.zhonghe.lib_base.baseui.MenuTxt;
import com.zhonghe.lib_base.constant.CstScheme;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.RefundImgInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.adapter.RefundSubmitAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseSelectImageActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.utile.image.CropParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by a on 2017/8/16.
 */

public class RefundsBeginActivity extends BaseSelectImageActivity implements BaseSelectImageActivity.upLoadListener, RefundSubmitAdapter.CancelImgListener {
    @Bind(R.id.id_refund_submit_desc_et)
    EditText idRefundSubmitDescEt;
    @Bind(R.id.id_refund_submit_img_gv)
    GridView idRefundSubmitImgGv;
    @Bind(R.id.id_refund_submit_bt)
    Button idRefundSubmitBt;
    @Bind(R.id.id_refund_price_tv)
    TextView idRefundPriceTv;

    private List<String> files;
    //保存图片路径
    private String mPath;
    RefundSubmitAdapter adapter;
    RefundImgInfo infoadd;
    private MenuTxt mMeunManager;
    private List<String> imgs;
    private String orderId;
    private String orderPrice;

    @Override
    protected void initTop() {
        setTitle(R.string.prodetail_title_refunds);
        setNavigation(R.mipmap.common_nav_back);
        mMeunManager = new MenuTxt.MenuTxtBuilder(this)
                .setTitle(R.string.common_submit)
                .setListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        submitRefunds();
                        return false;
                    }
                }).build();
        addMenu(mMeunManager);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_refundsbegin);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        orderId = intent.getStringExtra(CstProject.KEY.ID);
        orderPrice = intent.getStringExtra(CstProject.KEY.DATA);
    }

    @Override
    protected void initViews() {
        imgs = new ArrayList<>();
        adapter = new RefundSubmitAdapter(this);
        idRefundSubmitImgGv.setAdapter(adapter);

        idRefundPriceTv.setText(String.format(getString(R.string.symbol_money_format), orderPrice));
        infoadd = new RefundImgInfo();
        infoadd.setAdd(true);
        adapter.addItem(infoadd);
        adapter.notifyDataSetChanged();
        adapter.setAddListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getCount() < 4)
                    selectPic(view);
            }
        });
        adapter.setOnCancelImg(this);
    }

    @OnClick(R.id.id_refund_submit_bt)
    public void onViewClicked() {
    }

    private void selectPic(View parent) {
        CropParams params = new CropParams(this);
        params.compress = true;
        params.enable = false;
        params.compressQuality = 80;
        selectPicture(parent, params);
    }


    @Override
    public void onCompressed(Uri uri) {
        super.onCompressed(uri);
        if (uri != null) {
//            files = new ArrayList<String>();
            String url = null;
            url = uri.toString();
            RefundImgInfo info = new RefundImgInfo();
            info.setAdd(false);
            mPath = url.replace(CstScheme.FILE, "");
            List<File> files = new ArrayList<File>();
            files.add(new File(mPath));
            upLowdImage(files, this);
            info.setImgUrl(url);
            if (adapter.getCount() == 3) {
                adapter.getData().set(adapter.getCount() - 1, info);
            } else {
                adapter.addItem(adapter.getCount() - 1 < 0 ? 0 : adapter.getCount() - 1, info);
            }
            adapter.notifyDataSetChanged();
        }
    }

    void submitRefunds() {
        String content = idRefundSubmitDescEt.getText().toString();
        if (UtilString.isEmpty(content) || imgs.size() == 0)
            return;
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getSubmitRefunds(mContext, orderId, imgs, content, new ResultListener() {
            @Override
            public void onFail(String error) {
                setWaitingDialog(false);
            }

            @Override
            public void onSuccess(Object obj) {
                setWaitingDialog(false);
                finish();
            }
        });
        addRequest(request);
    }

    @Override
    public void onLoadFinish(String imgfile) {
        UtilLog.e("upload....result..." + imgfile);
        imgs.add(imgfile);
    }

    @Override
    public void OnCancelPosition(int position) {
        if (UtilList.isNotEmpty(imgs))
            imgs.remove(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
