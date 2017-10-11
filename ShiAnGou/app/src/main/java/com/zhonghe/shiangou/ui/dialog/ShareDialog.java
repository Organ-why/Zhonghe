package com.zhonghe.shiangou.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * auther: whyang
 * date: 2017/9/28
 * desc: share
 */

public class ShareDialog extends PopupWindow implements View.OnClickListener{
    TextView idShareQq;
    TextView idShareWechat;
    TextView idShareMoments;
    TextView idShareSina;
    TextView idShareQzone;
    TextView idShareEmail;
    TextView idShareMsg;
    TextView idShareLink;
    TextView idShareCancel;
    private View mMenuView;
    Context mContext;
    private String url = "www.baidu.com";
    private String thumb_img;
    private String title;
    private String desc;


    /**
     *
     * @param context
     * @param url
     * @param thumb_img
     * @param title
     * @param desc
     */
    public ShareDialog(Context context, String url, String thumb_img, String title, String desc) {
        super(context);
        mContext = context;
        this.url = url;
        this.thumb_img = thumb_img;
        this.title = title;
        this.desc = desc;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_share_dialog, null);
        ButterKnife.bind(mMenuView);
        idShareQq = ButterKnife.findById(mMenuView, R.id.id_share_qq);
        idShareQq.setOnClickListener(this);
        idShareWechat = ButterKnife.findById(mMenuView, R.id.id_share_wechat);  idShareWechat.setOnClickListener(this);
        idShareMoments = ButterKnife.findById(mMenuView, R.id.id_share_moments);  idShareMoments.setOnClickListener(this);
        idShareSina = ButterKnife.findById(mMenuView, R.id.id_share_sina);  idShareSina.setOnClickListener(this);
        idShareQzone = ButterKnife.findById(mMenuView, R.id.id_share_qzone);
        idShareEmail = ButterKnife.findById(mMenuView, R.id.id_share_email);
        idShareMsg = ButterKnife.findById(mMenuView, R.id.id_share_msg);
        idShareLink = ButterKnife.findById(mMenuView, R.id.id_share_link);
        idShareCancel = ButterKnife.findById(mMenuView, R.id.id_share_cancel);


        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        mMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x7f000000);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        setContentView(mMenuView);


    }

    @Override
    public void dismiss() {
        super.dismiss();
        ButterKnife.unbind(mMenuView);
    }

    private void ShareWeb(String url, String thumb_img, String title, String desc, SHARE_MEDIA shareMedia) {
        UMImage thumb = new UMImage(mContext, thumb_img);
        UMWeb web = new UMWeb(url);
        web.setThumb(thumb);
        web.setDescription(desc);
        web.setTitle(title);
        new ShareAction((Activity) mContext).withMedia(web).setPlatform(shareMedia).setCallback(shareListener).share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Util.toast(mContext, R.string.common_success_tip);
//            Toast.makeText(ShareDetailActivity.this,"成功了",Toast.LENGTH_LONG).show()；
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Util.toast(mContext, R.string.common_fail_tip);
//            Toast.makeText(ShareDetailActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Util.toast(mContext, R.string.common_cancel);
//            Toast.makeText(ShareDetailActivity.this,"取消了",Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_share_qq:
                ShareWeb(url, thumb_img, title, desc, SHARE_MEDIA.QQ);
                break;
            case R.id.id_share_wechat:
                ShareWeb(url, thumb_img, title, desc, SHARE_MEDIA.WEIXIN);
                break;
            case R.id.id_share_moments:
                ShareWeb(url, thumb_img, title, desc, SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.id_share_sina:
                ShareWeb(url, thumb_img, title, desc, SHARE_MEDIA.SINA);
                break;
            case R.id.id_share_qzone:
                break;
            case R.id.id_share_email:
                break;
            case R.id.id_share_msg:
                break;
            case R.id.id_share_link:
                break;
            case R.id.id_share_cancel:
                break;
        }
    }
}
