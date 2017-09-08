package com.zhonghe.shiangou.ui.dialog;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.zhonghe.lib_base.baseui.BaseDialog;
import com.zhonghe.lib_base.constant.CstFile;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilFile;
import com.zhonghe.lib_base.utils.UtilPackage;
import com.zhonghe.lib_base.utils.UtilStorage;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.http.DownloadUtil;
import com.zhonghe.shiangou.http.HttpUtil;
import com.zhonghe.shiangou.system.constant.CstProject;
import com.zhonghe.shiangou.ui.activity.MainActivity;

import java.io.File;

import static com.zhonghe.shiangou.system.constant.CstProject.URL_PRO;

/**
 * auther: whyang
 * date: 2017/8/31
 * desc:
 */

public class AppUpdataDialog extends BaseDialog {
    // 应用更新下载通知ID
    private static final int APP_DOWNLOAD_NOTIFY_ID = 0;

    String versionName;

    public AppUpdataDialog(Context context, String versionName) {
        super(context);
        this.versionName = versionName;
    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected void initViews() {
        setTitle(R.string.upgrade_update_title);
        setMessage("新版本:" + versionName);
       /* setSingle(R.string.upgrade_update);*/
        setDouble(R.string.upgrade_cancel, R.string.upgrade_update);
        setOnDoubleListener(new OnLeftListener() {

            @Override
            public void onLeft() {
                cancel();
            }
        }, new OnRightListener() {

            @Override
            public void onRight() {
                update();
            }


        });
    }

    private void update() {
//        String fileName = UtilMD5.getStringMD5(mInfo.getUrl()) + CstFile.SUFFIX_APK;

        final String filePath = UtilStorage.getFilePath(mContext, CstProject.PROJECT + CstFile.SUFFIX_APK);
//        final String filePath = UtilStorage.getFilePath(mContext, CstProject.PROJECT + CstFile.SUFFIX_EXPLORER);
        cancel();
        //上一次已经下载了该安装包，并通过校验，则直接安装
        if (new File(filePath).exists()) {
            UtilPackage.installApk(mContext, filePath);

//            if(mInfo.isForceUpdate()) {
//                mActivity.finish();
//            }
        } else {
            //进入下载对话框，下载升级应用
//            if(mInfo.isForceUpdate()) {
//                AppDownloadDialog dialog = new AppDownloadDialog(mActivity, mInfo);
//                dialog.show();
//            } else {
            //通知栏方式下载更新
            downloadApp();
//            }
        }


    }

    private void downloadApp() {
//        final String filePath = UtilStorage.createFilePath(mContext, CstProject.PROJECT + CstFile.SUFFIX_EXPLORER );
        final String filePath = UtilStorage.createFilePath(mContext, CstProject.PROJECT + CstFile.SUFFIX_APK);
        //开始
        nofityAppDownload(mContext, 0);
        new DownloadUtil().download(HttpUtil.APP_DOWNLOAD_URL, filePath, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String name) {
                cancelAppDownload(mContext);
                //下载完成，校验安装包，成功安装应用，否则删除该安装包
//                if(UtilMD5.getFileMD5(filePath).equals(mInfo.getMd5())) {
//                UtilPackage.installApk(mContext, filePath+"/"+name);
                UtilPackage.installApk(mContext, filePath);
//                } else {
//                    UtilFile.deleteFile(filePath);
//                }
            }

            @Override
            public void onDownloading(int progress) {
                nofityAppDownload(mContext, progress);
            }

            @Override
            public void onDownloadFailed() {
                Util.toast(mContext, R.string.upgrade_download_failure);
                cancelAppDownload(mContext);
            }
        });
    }


    /**
     * 通知安装包下载进度
     *
     * @param context  上下文
     * @param progress 下载进度
     */
    private void nofityAppDownload(Context context, int progress) {
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.upgrade_download_title))
                .setContentText(context.getString(R.string.upgrade_download_progress, progress))
                .setTicker(context.getString(R.string.upgrade_download_ticker))
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT))
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setSmallIcon(R.mipmap.icon_logo)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .build();


        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(APP_DOWNLOAD_NOTIFY_ID, notification);
    }

    /**
     * 取消下载通知
     *
     * @param context 上下文
     */
    private void cancelAppDownload(Context context) {
        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(APP_DOWNLOAD_NOTIFY_ID);
    }

}
