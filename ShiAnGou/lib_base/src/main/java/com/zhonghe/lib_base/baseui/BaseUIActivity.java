package com.zhonghe.lib_base.baseui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zhonghe.lib_base.R;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.lib_base.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2017/8/3.
 * Author: whyang
 */
public class BaseUIActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    //系统栏背景颜色管理器
    private SystemBarTintManager mSbtManager;
    //界面构选项
    private long mOptions;
    //导航图标点击事件监听器
    private MenuItem.OnMenuItemClickListener mNavigationListener;
    public Context mContext;
    ProgressBar progressBar;
    //与navbar tab关联的fragments
    private List<FragmentTab> mNavTabs;
    //tabhost
    private FragmentTabHost mTabHost;
    private Toolbar mToolbar;

    private FrameLayout mFlContent;
    //toolbar 里的title
    private TextView mTvTitle;
    //appbar自定义布局
    private int mAppCustomLayoutRes;
    //tablayout
    private TabLayout mTabLayout;
    private View mViewShadow;
    //与appbar tab关联的fragments
    private List<FragmentTab> mAppTabs;
    private int mTabIndex;



    //等待对话框
    private Dialog mWaitDialog;
    @Override
    protected void init(Bundle savedInstanceState) {
        mOptions = initOptions();
    }

    @Override
    protected void initBaseViews() {
        super.setContentView(R.layout.res_activity_base_ui);
/////////////////////////////////////    Toolbar    ////////////////////////////////////////////////////
        ViewStub appbarStub = (ViewStub) findViewById(R.id.base_id_appbar_stub);


        if (withOption(UIOptions.UI_OPTIONS_APPBAR_TOOLBAR)) {
            appbarStub.setLayoutResource(R.layout.res_layout_base_appbar);
            appbarStub.inflate();

            ViewStub toolbarStub = (ViewStub) findViewById(R.id.base_id_appbar_toolbar_stub);

            toolbarStub.setLayoutResource(R.layout.res_layout_base_appbar_toolbar);
            toolbarStub.inflate();
            mToolbar = (Toolbar) findViewById(R.id.base_id_appbar_toolbar);
            mTvTitle = (TextView) findViewById(R.id.base_id_appbar_toolbar_title);

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            initAppToobar();
        }
        //初始化appbar custom
        if (withOption(UIOptions.UI_OPTIONS_APPBAR_CUSTIOM)) {
            initAppCustom();
            if (mAppCustomLayoutRes > 0) {
//                ViewStub customStub = (ViewStub) findViewById(R.id.base_id_appbar_custom_stub);
                appbarStub.setLayoutResource(mAppCustomLayoutRes);
                appbarStub.inflate();
            }
        }

        //初始化appbar tabs
        if (withOption(UIOptions.UI_OPTIONS_APPBAR_TABS)) {
            ViewStub tabsStub = (ViewStub) findViewById(R.id.base_id_appbar_tabs_stub);
            tabsStub.setLayoutResource(R.layout.res_layout_base_appbar_tabs);
            tabsStub.inflate();
            mTabLayout = (TabLayout) findViewById(R.id.base_id_appbar_tabs);
            initAppTabs();
        }
/////////////////////////////////////    Content    ////////////////////////////////////////////////////
        ViewStub contentStub = (ViewStub) findViewById(R.id.base_id_content_stub);

        if (withOption(UIOptions.UI_OPTIONS_APPBAR_TABS)) {
            contentStub.setLayoutResource(R.layout.res_layout_base_content_viewpager);
            contentStub.inflate();

            mViewShadow = findViewById(R.id.base_id_content_shadow_top);
            ViewPager viewPager = (ViewPager) findViewById(R.id.base_id_content_viewpager);
            viewPager.setAdapter(new FragmentTabAdapter(getSupportFragmentManager(),
                    mAppTabs));
            mTabLayout.setupWithViewPager(viewPager);
            viewPager.setCurrentItem(mTabIndex);
        }else if (withOption(UIOptions.UI_OPTIONS_CONTENT_CUSTOM)) {
            contentStub.setLayoutResource(R.layout.res_layout_base_content_custom);
            contentStub.inflate();
        }



/////////////////////////////////////    Navigationbar    ////////////////////////////////////////////////////
        if (withOption(UIOptions.UI_OPTIONS_NAVBAR_TABS)) {
            ViewStub navStub = (ViewStub) findViewById(R.id.base_id_navbar_stub);
            navStub.setLayoutResource(R.layout.res_layout_base_navbar_tabs);
            navStub.inflate();

            initNavTabs();
            //初始化Tabhost
            mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
            mTabHost.setup(this, getSupportFragmentManager(), R.id.base_id_content_root);
            mTabHost.setOnTabChangedListener(this);
            if (UtilList.isNotEmpty(mNavTabs)) {
                Log.i("mNavTabs", String.valueOf(mNavTabs.size()));
                for (int i = 0; i < mNavTabs.size(); i++) {
                    FragmentTab tab = mNavTabs.get(i);
                    mTabHost.addTab(mTabHost.newTabSpec(tab.getFragmentClazz().getName() + i)
                                    .setIndicator(newTabIndicator(tab.getTitle(), tab.getIcon())),
                            tab.getFragmentClazz(), tab.getBundle());
                }

                mTabHost.getTabWidget().setDividerDrawable(null);
            }
        }


        mFlContent = (FrameLayout) findViewById(R.id.base_id_content_root);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        if (layoutResId > 0) {
            View view = LayoutInflater.from(this).inflate(layoutResId, null);
            setContentView(view);
        }
    }


    @Override
    public void setContentView(View view) {
        if (view != null && mFlContent != null) {
            mFlContent.addView(view,
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected void initViews() {

    }

    /**
     * 初始化navigation bar的tabs
     */
    protected void initNavTabs() {

    }

    /**
     * 初始化appbar Toolbar
     */
    protected void initAppToobar() {

    }
    /**
     * 初始化appbar的tabs
     */
    protected void initAppTabs() {

    }
    /**
     * 选中条颜色
     *
     * @param color
     */
    public void setAppTabSelectedColor(int color) {
        if (mTabLayout != null) {
            mTabLayout.setSelectedTabIndicatorColor(color);
        }
    }

    /**
     * 选中文字颜色
     *
     * @param nomalcolor
     * @param selectedcolor
     */
    public void setTabTextColors(int nomalcolor, int selectedcolor) {
        if (mTabLayout != null) {
            mTabLayout.setTabTextColors(nomalcolor, selectedcolor);
        }
    }
    /**
     * 初始化Appbar custom
     */
    protected void initAppCustom() {

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
     * 初始化界面构造选项
     *
     * @return
     */
    protected long initOptions() {
        return UIOptions.UI_OPTIONS_SCREEN_DEFAULT;
    }

    /**
     * 设置状态栏背景颜色,支持api>=19
     *
     * @param color
     */
    public void setStatusBarColor(int color) {
        if (
// !mSystembarEnabled
//                ||
                !withOption(UIOptions.UI_OPTIONS_SYSTEMBAR)) {
            return;
        }

        //android4.4以上的手机启用设置状态栏背景
        //系统导航栏背景默认不设置，并只支持api>=21以上设置，api 19、20也可以
        //做到设置系统导航栏背景，但会引起很多兼容性和体验问题
//        mIsInitStatusBarColor = true;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH) {
            //设置状态栏的默认背景颜色
            if (mSbtManager == null) {
                //设置透明状态栏
                WindowManager.LayoutParams params = window.getAttributes();
                params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                window.setAttributes(params);

                mSbtManager = new SystemBarTintManager(this);
                mSbtManager.setStatusBarTintEnabled(true);
            }

            mSbtManager.setStatusBarTintColor(color);
            mSbtManager.setStatusBarAlpha(Color.alpha(color));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
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

    @Override
    public void onTabChanged(String tabId) {

    }

    /**
     * 创建tab item
     *
     * @param title
     * @param imgRes
     * @return
     */
    private View newTabIndicator(String title, @DrawableRes int imgRes) {
        View indicator = LayoutInflater.from(this)
                .inflate(R.layout.res_layout_base_navbar_tab_indicator, null);
        ImageView ivIcon = (ImageView) indicator.findViewById(R.id.base_id_tab_img);
        ivIcon.setBackgroundResource(imgRes);
        TextView tvName = (TextView) indicator.findViewById(R.id.base_id_tab_text);
        tvName.setText(title);
        return indicator;
    }

    /**
     * 添加navbar的tab item
     *
     * @param txtRes
     * @param imgRes
     * @param cls
     */
    public void addNavTab(@StringRes int txtRes, @DrawableRes int imgRes, Class<? extends BaseUIFragment> cls) {
        addNavTab(getString(txtRes), imgRes, cls, null);
    }

    /**
     * 添加navbar的tab item
     *
     * @param txtRes
     * @param imgRes
     * @param cls
     */
    public void addNavTab(@StringRes int txtRes, @DrawableRes int imgRes, Class<? extends BaseUIFragment> cls, Bundle bundle) {
        addNavTab(getString(txtRes), imgRes, cls, bundle);
    }

    /**
     * 添加navbar的tab item
     *
     * @param title
     * @param imgRes
     * @param cls
     */
    public void addNavTab(String title, @DrawableRes int imgRes, Class<? extends BaseUIFragment> cls) {
        addNavTab(title, imgRes, cls, null);
    }

    /**
     * 添加navbar的tab item
     *
     * @param title
     * @param imgRes
     * @param cls
     */
    public void addNavTab(String title, @DrawableRes int imgRes, Class<? extends BaseUIFragment> cls, Bundle bundle) {
//        if (!withOption(UIOptions.UI_OPTIONS_NAVBAR_TABS)
//                || cls == null) {
//            return;
//        }

        if (UtilString.isNotBlank(title)) {
            if (mNavTabs == null) {
                mNavTabs = new ArrayList<>(4);
            }

            FragmentTab tab = new FragmentTab();
            tab.setTitle(title);
            tab.setIcon(imgRes);
            tab.setFragmentClazz(cls);
            tab.setBundle(bundle);
            mNavTabs.add(tab);
        }
    }
    /**
     * 设置选中的tab
     *
     * @param index
     */
    public void setAppTabIndex(int index) {
        this.mTabIndex = index;
    }
    /**
     * 添加appbar上的文本tab
     *
     * @param txtRes
     * @param cls
     */
    public void addAppTab(@StringRes int txtRes, Class<? extends BaseUIFragment> cls) {
        addAppTab(getString(txtRes), cls, null);
    }
    /**
     * 添加appbar上的文本tab
     *
     * @param title
     * @param cls
     * @param args
     */
    public void addAppTab(String title, Class<? extends BaseUIFragment> cls, Bundle args) {
        if (!withOption(UIOptions.UI_OPTIONS_APPBAR_TABS)
                || cls == null) {
            return;
        }

        if (mTabLayout != null && UtilString.isNotBlank(title)) {
            BaseUIFragment fragment = (BaseUIFragment) Fragment.instantiate(this, cls.getName(), args);
            if (mAppTabs == null) {
                mAppTabs = new ArrayList<>(4);
            }

            FragmentTab tab = new FragmentTab();
            tab.setTitle(title);
            tab.setFragment(fragment);
            mAppTabs.add(tab);
        }
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
     * 显示左边导航控件和其默认点击事件监听器（退出界面）
     *
     * @param imgRes
     */
    public void setNavigation(@DrawableRes int imgRes) {
        BitmapDrawable drawable = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), imgRes));

        MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                return false;
            }
        };

        setNavigation(drawable, listener);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
//        if (withOption(UIOptions.UI_OPTIONS_APPBAR_COLLAPSED_TOOLBAR)) {
//            if (mCtlLayout != null) {
//                mCtlLayout.setTitle(title);
//            }
//        } else
        if (withOption(UIOptions.UI_OPTIONS_APPBAR_TOOLBAR)) {
//            if (mTitleGravity == TOOLBAR_TITLE_GRAVITY_CENTER) {
//                if (mTvTitle != null) {
            mTvTitle.setText(title);
//                }
//            } else {
//                if (mToolbar != null) {
//                    mToolbar.setTitle(title);
//                }
//            }
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

                mWaitDialog = new AlertDialog.Builder(this, R.style.res_theme_dialog_waiting)
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
     * 显示左边导航控件和其默认点击事件监听器（退出界面）
     *
     * @param drawable
     * @param listener
     */
    public void setNavigation(Drawable drawable, MenuItem.OnMenuItemClickListener listener) {
//                | UIOptions.UI_OPTIONS_APPBAR_COLLAPSED_TOOLBAR)
        if (!withOption(UIOptions.UI_OPTIONS_APPBAR_TOOLBAR)
                ) {
            return;
        }

        if (mToolbar != null) {
            mToolbar.setNavigationIcon(drawable);
        }

        mNavigationListener = listener;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
//        MenuBase value = mMenus.get(itemId);

        if (android.R.id.home == itemId) {
            if (mNavigationListener != null) {
                mNavigationListener.onMenuItemClick(item);
            }
        }
//        else {
//            if (value != null) {
//                if (value instanceof MenuTxt) {//触发点击文本menu
//                    MenuTxt elem = (MenuTxt) value;
//                    MenuItem.OnMenuItemClickListener listener = elem.getListener();
//                    if (listener != null) {
//                        listener.onMenuItemClick(item);
//                    }
//                } else if (value instanceof MenuImg) {//触发点击图片menu
//                    MenuImg elem = (MenuImg) value;
//                    MenuItem.OnMenuItemClickListener listener = elem.getListener();
//                    if (listener != null) {
//                        listener.onMenuItemClick(item);
//                    }
//                } else if (value instanceof MenuPopup) {//触发打开popupmenu
//                    MenuPopup elem = (MenuPopup) value;
//                    View view = mToolbar.findViewById(elem.getMenuId());
//                    PopupMenu popup = new PopupMenu(this, view);
//                    MenuInflater inflater = popup.getMenuInflater();
//                    inflater.inflate(elem.getMenuRes(), popup.getMenu());
//                    popup.show();
//
//                    PopupMenu.OnMenuItemClickListener listener = elem.getListener();
//                    if (listener != null) {
//                        popup.setOnMenuItemClickListener(listener);
//                    }
//                }
//            }
//        }

        return super.onOptionsItemSelected(item);
    }


}
