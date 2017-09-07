package com.zhonghe.shiangou.utile;

import android.os.AsyncTask;

import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author : guxulei
 * @desc : 上传头像
 * @since : 2017/8/16.
 */
public class UploadHeaderTask extends AsyncTask<Object, Integer, String> {

    String url = "";

    String memberId;
    String memberKey;
    List<File> files;

    public UploadHeaderTask(String url, String memberId, String memberKey, List<File> files) {
        this.memberId = memberId;
        this.url = url;
        this.files = files;
        this.memberKey = memberKey;
    }

    public UploadHeaderTask(String url, List<File> files) {
//        this.memberId = userid;
        this.url = url;
        this.files = files;
        this.memberKey = memberKey;
    }

    @Override
    protected String doInBackground(Object... params) {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
//        builder.addFormDataPart("memberId", null, RequestBody.create(MediaType.parse("application/text; charset=utf-8"), memberId));
//        builder.addFormDataPart("memberKey", null, RequestBody.create(MediaType.parse("application/text; charset=utf-8"), memberKey));
        builder.addFormDataPart("user_id", null, RequestBody.create(MediaType.parse("application/text; charset=utf-8"), ProjectApplication.mUser.getUser_id()));
        for (int i = 0; i < files.size(); i++) {
            builder.addFormDataPart("pic", files.get(i).getName(), RequestBody.create(MEDIA_TYPE_PNG, files.get(i)));//
        }

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
