package com.zhonghe.shiangou.ui.baseui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.baseres.BaseRes;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.activity.UserActivity;
import com.zhonghe.shiangou.ui.baseui.BaseTopActivity;
import com.zhonghe.shiangou.ui.dialog.SelectPictureDialog;
import com.zhonghe.shiangou.utile.JSONParser;
import com.zhonghe.shiangou.utile.PrefUtils;
import com.zhonghe.shiangou.utile.UploadImageTask;
import com.zhonghe.shiangou.utile.image.CropHandler;
import com.zhonghe.shiangou.utile.image.CropHelper;
import com.zhonghe.shiangou.utile.image.CropParams;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 图片选择界面基类
 */
public abstract class BaseSelectImageActivity extends BaseTopActivity
        implements CropHandler {
    private SelectPictureDialog mDialog;
    private CropParams mCropParams;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mCropParams = new CropParams(this);
    }

    /**
     * @param parent
     */
    public void selectPicture(View parent) {
        if (mDialog == null) {
            mCropParams.enable = true;
            mCropParams.compress = false;
            mDialog = new SelectPictureDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCropParams.refreshUri();
                    int id = view.getId();
                    if (id == R.id.photo_id_camera) {//拍照
                        Intent intent = CropHelper.buildCameraIntent(mCropParams);
                        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
                    } else if (id == R.id.photo_id_picked) {//相册选择图片
                        Intent intent = CropHelper.buildGalleryIntent(mCropParams);
                        startActivityForResult(intent, CropHelper.REQUEST_PICK);
                    }
                }
            });
        }

        mDialog.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    /**
     * @param parent
     */
    public void selectPicture(View parent, final CropParams params) {
        if (mDialog == null) {
            mCropParams = params;
            mDialog = new SelectPictureDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCropParams.refreshUri();
                    int id = view.getId();
                    if (id == R.id.photo_id_camera) {//拍照
                        Intent intent = CropHelper.buildCameraIntent(mCropParams);
                        startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
                    } else if (id == R.id.photo_id_picked) {//相册选择图片
                        Intent intent = CropHelper.buildGalleryIntent(mCropParams);
                        startActivityForResult(intent, CropHelper.REQUEST_PICK);
                    }
                }
            });
        }

        mDialog.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        CropHelper.clearCacheDir();
        super.onDestroy();
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onCompressed(Uri uri) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }

    public void upLowdImage(List<File> fileList, final upLoadListener listener) {
        setWaitingDialog(true);
        UploadImageTask uploadImage = new UploadImageTask(HttpUtil.URL_IMGUp,
//                PrefUtils.getString(this, Const.MEMBER_KEY, "")
                fileList) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.e("uploawd", result.toString());
                setWaitingDialog(false);
                try {
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject json = new JSONObject(result);
                        BaseRes obj = (BaseRes) JSONParser.toObject(json.toString(), BaseRes.class);
                        if (obj.getState() == 1) {
//                            listener.onFail(obj.getMsg());
                            String strdata = JSONParser.toString(obj.getDatas());
//                            Type bean = new TypeToken<List<String>>() {
//                            }.getType();
                            String imsgUrl = (String) JSONParser.toObject(strdata, String.class);
                            listener.onLoadFinish(imsgUrl);
                        }
//                        else {
//                            listener.onLoadFinish(null);
//                        }
//                        ToastUtils.showError(activity);
//                        dialog.dismiss();
                        return;
                    }
//                    AddPhotoResponse response = (AddPhotoResponse) JSONParser.toObject(result, AddPhotoResponse.class);
//                    if (response.getCode() == 0) {
//                        CommentPhoto commentPhoto = new CommentPhoto();
//                        commentPhoto.setImgUrl(response.getUrls().get(0).getUrl());
//                    } else {
//                        ToastUtils.showError(activity, response.getMsg());
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("uploawd_error", e.toString());
//                    ToastUtils.showError(activity);
                }

//                dialog.dismiss();
            }
        };
        uploadImage.execute();
    }

    public interface upLoadListener {
        void onLoadFinish(String imgfile);
    }


}
