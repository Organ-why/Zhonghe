package com.zhonghe.shiangou.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.MenuTxt;
import com.zhonghe.lib_base.constant.CstScheme;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.UtilLog;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.RefundImgInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.adapter.RefundSubmitAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseSelectImageActivity;
import com.zhonghe.shiangou.ui.listener.ResultListener;
import com.zhonghe.shiangou.ui.widget.RatingBar;
import com.zhonghe.shiangou.utile.image.CropParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemarkActivity extends BaseSelectImageActivity implements BaseSelectImageActivity.upLoadListener, RefundSubmitAdapter.CancelImgListener, RatingBar.OnRatingChangeListener {


    @Bind(R.id.id_home_category_item_iv)
    SimpleDraweeView idHomeCategoryItemIv;
    @Bind(R.id.id_home_category_item_title_tv)
    TextView idHomeCategoryItemTitleTv;
    @Bind(R.id.id_refund_submit_desc_et)
    EditText idRefundSubmitDescEt;
    @Bind(R.id.id_refund_submit_img_gv)
    GridView idRefundSubmitImgGv;
    @Bind(R.id.id_item_remark_desc_tv)
    TextView idItemRemarkDescTv;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.id_unname_cb)
    CheckBox idUnnameCb;


    //保存图片路径
    private String mPath;
    private List<String> imgs;
    RefundSubmitAdapter adapter;
    RefundImgInfo infoadd;
    float startCout = 5;
    private String goodsId;
    private MenuTxt mMeunManager;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_remark);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.remark_title);
        setNavigation(R.mipmap.common_nav_back);
        mMeunManager = new MenuTxt.MenuTxtBuilder(this)
                .setTitle(R.string.common_submit)
                .setListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        submitRemark();
                        return false;
                    }
                }).build();
        addMenu(mMeunManager);
    }

    @Override
    protected void initViews() {
        imgs = new ArrayList<>();
        Intent intent = getIntent();
        goodsId = intent.getStringExtra(CstProject.KEY.ID);
        ratingBar.setOnRatingChangeListener(this);
        adapter = new RefundSubmitAdapter(this,true);
        idRefundSubmitImgGv.setAdapter(adapter);
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

    private void selectPic(View parent) {
        CropParams params = new CropParams(this);
        params.compress = true;
        params.enable = false;
        params.compressQuality = 80;
        selectPicture(parent, params);
    }


    @Override
    public void onCompressed(Uri uri) {
        //选择图片回调
        super.onCompressed(uri);
        if (uri != null) {
//            files = new ArrayList<String>();
            String url = null;
            url = uri.toString();

            mPath = url.replace(CstScheme.FILE, "");
            List<File> files = new ArrayList<File>();
            files.add(new File(mPath));
            upLowdImage(files,false, this);


            RefundImgInfo info = new RefundImgInfo();
            info.setAdd(false);
            info.setImgUrl(url);
            if (adapter.getCount() == 3) {
                adapter.getData().set(adapter.getCount() - 1, info);
            } else {
                adapter.addItem(adapter.getCount() - 1 < 0 ? 0 : adapter.getCount() - 1, info);
            }
            adapter.notifyDataSetChanged();
        }
    }

    //提交评论
    void submitRemark() {
        String content = idRefundSubmitDescEt.getText().toString();
        if (UtilString.isEmpty(content) || imgs.size() == 0)
            return;
        setWaitingDialog(true);
        Request<?> request = HttpUtil.getCommentGoods(mContext, goodsId, content, String.valueOf(startCout), imgs,
                idUnnameCb.isChecked() ? "1" : ProjectApplication.mUser.getNick_name(), new ResultListener() {
                    @Override
                    public void onFail(String error) {
                        setWaitingDialog(false);
                        Util.toast(mContext, error);
                    }

                    @Override
                    public void onSuccess(Object obj) {
                        setWaitingDialog(false);
                        Util.toast(mContext, R.string.common_success_tip);
                        finish();
                    }
                });
        addRequest(request);
    }


//    @OnClick(R.id.id_home_category_item_iv)
//    public void onViewClicked() {
//        submitRemark();
//    }


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
    public void onRatingChange(float ratingCount) {
        startCout = ratingCount;
    }

}
