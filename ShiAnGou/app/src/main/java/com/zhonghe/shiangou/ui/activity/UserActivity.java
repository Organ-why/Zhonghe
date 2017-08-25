package com.zhonghe.shiangou.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.constant.CstScheme;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.RefundImgInfo;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.baseui.BaseSelectImageActivity;
import com.zhonghe.shiangou.utile.image.CropParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Date: 2017/8/13.
 * Author: whyang
 */
public class UserActivity extends BaseSelectImageActivity {
    @Bind(R.id.id_setup_setheader_sv)
    SimpleDraweeView idSetupSetheaderSv;
    @Bind(R.id.id_user_header_rl)
    RelativeLayout idUserHeaderRl;
    @Bind(R.id.id_user_nickname_rl)
    RelativeLayout idUserNicknameRl;
    @Bind(R.id.id_user_address_rl)
    RelativeLayout idUserAddressRl;
    @Bind(R.id.id_user_logout_bt)
    Button idUserLogoutBt;
    private String mPath;

    @Override
    protected void initTop() {
        setTitle(R.string.common_user_center);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        ProjectApplication.mImageLoader.loadCircleImage(idSetupSetheaderSv, ProjectApplication.mUser.getUser_pic());
    }


    @OnClick({R.id.id_user_header_rl, R.id.id_user_nickname_rl, R.id.id_user_address_rl, R.id.id_user_logout_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_user_header_rl:
                selectPic(view);
                break;
            case R.id.id_user_nickname_rl:
                break;
            case R.id.id_user_address_rl:
                ProDispatcher.goSelectAddressActivity(mContext);
                break;
            case R.id.id_user_logout_bt:
                ProjectApplication.mUser = null;
                ProDispatcher.sendLogoutBroadcast(mContext);
                finish();
                break;
        }
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
            List<File> files = new ArrayList<File>();
            String url = null;
            url = uri.toString();
            RefundImgInfo info = new RefundImgInfo();
            info.setAdd(false);
            mPath = url.replace(CstScheme.FILE, "");
            info.setImgUrl(url);
            files.add(new File(mPath));
            upLowdImage(files);
        }
    }
}
