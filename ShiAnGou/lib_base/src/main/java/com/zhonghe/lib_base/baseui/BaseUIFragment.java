package com.zhonghe.lib_base.baseui;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhonghe.lib_base.R;


/**
 * Date: 2017/7/4.
 * Author: whyang
 */
public class BaseUIFragment extends BaseFragment implements View.OnClickListener {
    //系统栏背景颜色管理器
    private SystemBarTintManager mSbtManager;
    private FrameLayout mFlContent;
    private ProgressBar progressBar;
    private Toolbar mToolbar;
    //appbar自定义布局
    private int mAppCustomLayoutRes;
    //界面构选项
    private long mOptions;
    //数据加载中
    private ProgressWheel mPwLoading;
    //暂无数据提示
    private TextView mTvEmpty;
    //网络请求重试
    private TextView mTvRetry;
    private View mViewShadow;
    //标题
    private TextView mTvTitle;
    private AlertDialog mWaitDialog;
    private OnRetryListener mRetryListener;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        //获取构造的界面结构
        mOptions = initOptions();
    }

    /**
     * 初始化appbar Toolbar
     */
    protected void initAppToobar() {

    }

    @Override
    protected View initBaseViews() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.res_activity_base_ui, null);

        ViewStub appbarStub = (ViewStub) view.findViewById(R.id.base_id_appbar_stub);
        appbarStub.setLayoutResource(R.layout.res_layout_base_appbar);
        appbarStub.inflate();

        ViewStub toolbarStub = (ViewStub) view.findViewById(R.id.base_id_appbar_toolbar_stub);
        if (withOption(UIOptions.UI_OPTIONS_APPBAR_TOOLBAR)) {
            toolbarStub.setLayoutResource(R.layout.res_layout_base_appbar_toolbar);
            toolbarStub.inflate();

            mToolbar = (Toolbar) view.findViewById(R.id.base_id_appbar_toolbar);
            mTvTitle = (TextView) view.findViewById(R.id.base_id_appbar_toolbar_title);

//            TypedArray a = mActivity.obtainStyledAttributes(R.style.res_style_toolbar_title_gravity,
//                    R.styleable.Toolbar);
//            mTitleGravity = a.getInt(R.styleable.Toolbar_titleGravity, TOOLBAR_TITLE_GRAVITY_LEFT);
//            a.recycle();

        }

        //初始化appbar custom
        if (withOption(UIOptions.UI_OPTIONS_APPBAR_CUSTIOM)) {
            initAppCustom();
            if (mAppCustomLayoutRes > 0) {
                ViewStub customStub = (ViewStub) view.findViewById(R.id.base_id_appbar_custom_stub);
                customStub.setLayoutResource(mAppCustomLayoutRes);
                customStub.inflate();
            }
        }


        ViewStub contentStub = (ViewStub) view.findViewById(R.id.base_id_content_stub);
        contentStub.setLayoutResource(R.layout.res_layout_base_content_custom);
        contentStub.inflate();

        mViewShadow = view.findViewById(R.id.base_id_content_shadow_top);

        mFlContent = (FrameLayout) view.findViewById(R.id.base_id_content_root);
        mPwLoading = (ProgressWheel) view.findViewById(R.id.base_id_content_progress);
        mTvEmpty = (TextView) view.findViewById(R.id.base_id_content_empty);
        mTvRetry = (TextView) view.findViewById(R.id.base_id_content_retry);
        mTvRetry.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected void initViews() {

    }

    /**
     * 设置标题
     *
     * @param txtRes
     */
    public void setTitle(@StringRes int txtRes) {
        setTitle(getString(txtRes));
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (withOption(UIOptions.UI_OPTIONS_APPBAR_TOOLBAR)) {
            if (mToolbar != null) {
                mToolbar.setTitle(title);
            }
            if (mTvTitle != null) {
                mTvTitle.setText(title);
            }
        } else {

        }
    }

    /**
     * 显示等待对话框
     *
     * @param enabled
     */
    public void setWaitingDialog(boolean enabled) {
        if (enabled) {
            if (mWaitDialog == null) {

                mWaitDialog = new AlertDialog.Builder(mActivity, R.style.res_theme_dialog_waiting)
                        .setView(R.layout.res_layout_base_progress)
                        .show();
                mWaitDialog.setCanceledOnTouchOutside(false);
            } else if (!mWaitDialog.isShowing()) {
                mWaitDialog.show();
            }
        } else {
            if (mWaitDialog != null && mWaitDialog.isShowing()) {
                mWaitDialog.cancel();
            }
        }
    }

    /**
     * 设置状态栏背景颜色,支持api>=19
     *
     * @param color
     */
    public void setStatusBarColor(int color) {
//        if (
//// !mSystembarEnabled
////                ||
//                !withOption(UIOptions.UI_OPTIONS_APPBAR_TOOLBAR)) {
//            return;
//        }

        //android4.4以上的手机启用设置状态栏背景
        //系统导航栏背景默认不设置，并只支持api>=21以上设置，api 19、20也可以
        //做到设置系统导航栏背景，但会引起很多兼容性和体验问题
//        mIsInitStatusBarColor = true;
        Window window = mActivity.getWindow();
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH) {
            //设置状态栏的默认背景颜色
            if (mSbtManager == null) {
                //设置透明状态栏
                WindowManager.LayoutParams params = window.getAttributes();
                params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                window.setAttributes(params);

                mSbtManager = new SystemBarTintManager(mActivity);
                mSbtManager.setStatusBarTintEnabled(true);
            }

            mSbtManager.setStatusBarTintColor(color);
            mSbtManager.setStatusBarAlpha(Color.alpha(color));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }


    /**
     * 设置视图
     *
     * @param layoutResId
     */
    public void setContentView(int layoutResId) {
        if (layoutResId > 0) {
            View view = LayoutInflater.from(mActivity).inflate(layoutResId, null);
            setContentView(view);
        }
    }

    /**
     * 设置视图
     *
     * @param view
     */
    public void setContentView(View view) {
        if (view != null && mFlContent != null) {
            mFlContent.addView(view,
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
        }
    }

    /**
     * 根据资源id找到视图
     *
     * @param id
     * @return
     */
    public View findViewById(int id) {
        return mFlContent.findViewById(id);
    }

    /**
     * 初始化Appbar custom
     */
    protected void initAppCustom() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        destroyCallback();
    }

    /**
     * 是否有当前界面构造选项
     *
     * @param option
     * @return
     */
    private boolean withOption(long option) {
        return (mOptions & option) != 0;
    }

    /**
     * 初始化界面构造选项
     *
     * @return
     */
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_SCREEN_DEFAULT;
    }


    /**
     * 设置appbar通用布局的layout
     *
     * @param layoutRes
     */
    public void setAppCustomLayout(@LayoutRes int layoutRes) {
        this.mAppCustomLayoutRes = layoutRes;
    }


    /**
     * 显示空数据文本提示
     *
     * @param txtRes
     */
    public void setEmptyText(@StringRes int txtRes) {
        setEmptyText(getString(txtRes));
    }

    /**
     * 显示空数据文本提示
     *
     * @param emptyText
     */
    public void setEmptyText(String emptyText) {
        if (withOption(UIOptions.UI_OPTIONS_APPBAR_TABS
                | UIOptions.UI_OPTIONS_NAVBAR_TABS)) {
            return;
        }

        if (mFlContent != null) mFlContent.setVisibility(View.GONE);
//        if (mPwLoading != null) mPwLoading.setVisibility(View.GONE);
//        if (mTvRetry != null) mTvRetry.setVisibility(View.GONE);
        if (mTvEmpty != null) {
            mTvEmpty.setVisibility(View.VISIBLE);
            mTvEmpty.setText(emptyText);
        }
    }

    /**
     * 显示空数据图片文本提示
     *
     * @param txtRes
     */
    public void setEmptyText(@StringRes int txtRes, @Nullable Drawable top) {
        setEmptyText(getString(txtRes), null, top, null, null);
    }

    /**
     * 显示空数据图片文本提示
     *
     * @param emptyText
     */
    public void setEmptyText(String emptyText, @Nullable Drawable start, @Nullable Drawable top,
                             @Nullable Drawable end, @Nullable Drawable bottom) {
        if (mFlContent != null) mFlContent.setVisibility(View.GONE);
//        if (mPwLoading != null) mPwLoading.setVisibility(View.GONE);
//        if (mTvRetry != null) mTvRetry.setVisibility(View.GONE);
        if (mTvEmpty != null) {
            mTvEmpty.setVisibility(View.VISIBLE);
            mTvEmpty.setText(emptyText);
            mTvEmpty.setCompoundDrawables(start, top, end, bottom);
        }
    }


    /**
     * 显示空数据图片文本提示
     *
     * @param txtRes
     */
    public void setEmptyText(@StringRes int txtRes, int top) {
        setEmptyText(getString(txtRes), 0, top, 0, 0);
    }

    /**
     * 显示空数据图片文本提示
     *
     * @param emptyText
     */
    public void setEmptyText(String emptyText, int left, int top,
                             int right, int bottom) {
        if (mFlContent != null) mFlContent.setVisibility(View.GONE);
//        if (mPwLoading != null) mPwLoading.setVisibility(View.GONE);
//        if (mTvRetry != null) mTvRetry.setVisibility(View.GONE);
        if (mTvEmpty != null) {
            mTvEmpty.setVisibility(View.VISIBLE);
            mTvEmpty.setText(emptyText);
            mTvEmpty.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

    /**
     * 隐藏空数据文本提示
     */
    public void hideEmptyText() {
        if (withOption(UIOptions.UI_OPTIONS_APPBAR_TABS
                | UIOptions.UI_OPTIONS_NAVBAR_TABS)) {
            return;
        }

        if (mFlContent != null) mFlContent.setVisibility(View.VISIBLE);
        if (mTvEmpty != null) mTvEmpty.setVisibility(View.GONE);
    }

    /**
     * 重试
     *
     * @param enabled
     */
    public void setRetry(boolean enabled) {
        if (enabled) {
            if (mTvRetry != null) mTvRetry.setVisibility(View.VISIBLE);
        } else {
            if (mTvRetry != null) mTvRetry.setVisibility(View.GONE);
        }
    }

    /**
     * 网络请求重试监听器
     */
    public interface OnRetryListener {
        //重试
        public void onRetry();
    }

    /**
     * 设置重试事件
     *
     * @param listener
     */
    public void setOnRetryListener(OnRetryListener listener) {
        this.mRetryListener = listener;
    }

    public void setRetryText(@StringRes int txtRes) {
        Drawable drawable = ContextCompat.getDrawable(mActivity, R.mipmap.res_ic_retry);
        setRetryText(getString(txtRes), null, drawable, null, null);
    }

    public void setRetryText(@StringRes int txtRes, @DrawableRes int DabRes) {
        Drawable drawable = ContextCompat.getDrawable(mActivity, DabRes);
        setRetryText(getString(txtRes), null, drawable, null, null);
    }

    public void setRetryText(String text, @Nullable Drawable start, @Nullable Drawable top,
                             @Nullable Drawable end, @Nullable Drawable bottom) {
        if (mTvRetry != null) {
            mTvRetry.setVisibility(View.VISIBLE);
            mTvRetry.setText(text);
//            mTvRetry.setCompoundDrawables(start, top, end, bottom);
            mTvRetry.setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom);
        }
    }

    /**
     * 调用重试
     */
    private void retry() {
        if (mRetryListener != null) {
            mRetryListener.onRetry();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.base_id_content_retry) {
            retry();
        }
    }
}
