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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class BaseUIActivity extends BaseActivity implements TabHost.OnTabChangeListener, View.OnClickListener {
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
    //menu集合
    private SparseArray<MenuBase> mMenus;

    //等待对话框
    private Dialog mWaitDialog;
    //空信息
    private TextView mTvEmpty;
    //自定义title
    private LinearLayout mViewTitleTop;
    private TextView mTvRetry;
    private OnRetryListener mRetryListener;

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = this;
        mOptions = initOptions();

        mMenus = new SparseArray<>(8);
        mMenus.put(R.id.res_id_menu_item0, null);
        mMenus.put(R.id.res_id_menu_item1, null);
        mMenus.put(R.id.res_id_menu_item2, null);
        mMenus.put(R.id.res_id_menu_item3, null);
        mMenus.put(R.id.res_id_menu_item4, null);
        mMenus.put(R.id.res_id_menu_item5, null);
        mMenus.put(R.id.res_id_menu_item6, null);
        mMenus.put(R.id.res_id_menu_item7, null);
    }

    @Override
    protected void initBaseViews() {
        super.setContentView(R.layout.res_activity_base_ui);
/////////////////////////////////////    Toolbar    ////////////////////////////////////////////////////
        ViewStub appbarStub = (ViewStub) findViewById(R.id.base_id_appbar_stub);


        appbarStub.setLayoutResource(R.layout.res_layout_base_appbar);
        appbarStub.inflate();
        if (withOption(UIOptions.UI_OPTIONS_APPBAR_TOOLBAR)) {

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
                ViewStub customStub = (ViewStub) findViewById(R.id.base_id_appbar_custom_stub);
                customStub.setLayoutResource(mAppCustomLayoutRes);
                customStub.inflate();
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
        } else if (withOption(UIOptions.UI_OPTIONS_CONTENT_CUSTOM)) {
            contentStub.setLayoutResource(R.layout.res_layout_base_content_custom);
            contentStub.inflate();
            mViewTitleTop = (LinearLayout) findViewById(R.id.base_id_title_top);
            mTvEmpty = (TextView) findViewById(R.id.base_id_content_empty);
            mTvRetry = (TextView) findViewById(R.id.base_id_content_retry);
            mTvRetry.setOnClickListener(this);
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
     * 显示的tab项
     *
     * @param index
     */
    public void setOnTabChanged(int index) {
        if (index >= 0 && index <= mTabHost.getChildCount())
            mTabHost.setCurrentTab(index);
    }

    /**
     * 选择的tab项
     */
    public int getCurrentTab() {
        return mTabHost.getCurrentTab();
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
        if (UtilString.isEmpty(title)) {
            tvName.setVisibility(View.GONE);
        } else {
            tvName.setText(title);
        }
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

//        if (UtilString.isNotBlank(title)) {
        if (mNavTabs == null) {
            mNavTabs = new ArrayList<>(5);
        }

        FragmentTab tab = new FragmentTab();
        tab.setTitle(UtilString.nullToEmpty(title));
        tab.setIcon(imgRes);
        tab.setFragmentClazz(cls);
        tab.setBundle(bundle);
        mNavTabs.add(tab);
//        }
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
     * @param txtRes
     * @param cls
     */
    public void addAppTab(@StringRes int txtRes, Class<? extends BaseUIFragment> cls, Bundle args) {
        addAppTab(getString(txtRes), cls, args);
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
     * 添加menu
     *
     * @param menu
     */
    public void addMenu(@NonNull MenuBase menu) {
        if (!withOption(UIOptions.UI_OPTIONS_APPBAR_TOOLBAR)) {
            return;
        }

        if (menu != null && mMenus.get(menu.getMenuId()) == null) {
            for (int i = 0; i < mMenus.size(); i++) {
                int key = mMenus.keyAt(i);
                if (mMenus.get(key) == null) {
                    menu.setMenuId(key);
                    mMenus.put(key, menu);
                    invalidateOptionsMenu();
                    break;
                }
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        for (int i = 0; i < mMenus.size(); i++) {
            MenuBase value = mMenus.valueAt(i);
            if (value != null) {
                if (value instanceof MenuTxt) {//添加文本menu
                    MenuTxt elem = (MenuTxt) value;
                    MenuItem item = menu.add(Menu.NONE,
                            elem.getMenuId(), elem.getOrder(), elem.getTitle());
                    item.setShowAsAction(elem.getShowAsAction());
                } else if (value instanceof MenuImg) {//添加图片menu
                    MenuImg elem = (MenuImg) value;
                    MenuItem item = menu.add(Menu.NONE,
                            elem.getMenuId(), elem.getOrder(), elem.getTitle());
                    item.setIcon(elem.getIcon());
                    item.setShowAsAction(elem.getShowAsAction());
                } else if (value instanceof MenuSearch) {//添加搜索menu
                    MenuSearch elem = (MenuSearch) value;
                    MenuItem item = menu.add(Menu.NONE,
                            elem.getMenuId(), elem.getOrder(), elem.getTitle());
                    SearchView svSearch = new SearchView(this);
                    svSearch.setOnQueryTextListener(elem.getListener());
                    item.setActionView(svSearch);
                    item.setShowAsAction(elem.getShowAsAction());
                } else if (value instanceof MenuPopup) {//添加popup menu
                    MenuPopup elem = (MenuPopup) value;
                    MenuItem item = menu.add(Menu.NONE,
                            elem.getMenuId(), elem.getOrder(), elem.getTitle());
                    item.setIcon(elem.getIcon());
                    item.setShowAsAction(elem.getShowAsAction());
                }
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        MenuBase value = mMenus.get(itemId);

        if (android.R.id.home == itemId) {
            if (mNavigationListener != null) {
                mNavigationListener.onMenuItemClick(item);
            }
        } else {
            if (value != null) {
                if (value instanceof MenuTxt) {//触发点击文本menu
                    MenuTxt elem = (MenuTxt) value;
                    MenuItem.OnMenuItemClickListener listener = elem.getListener();
                    if (listener != null) {
                        listener.onMenuItemClick(item);
                    }
                } else if (value instanceof MenuImg) {//触发点击图片menu
                    MenuImg elem = (MenuImg) value;
                    MenuItem.OnMenuItemClickListener listener = elem.getListener();
                    if (listener != null) {
                        listener.onMenuItemClick(item);
                    }
                } else if (value instanceof MenuPopup) {//触发打开popupmenu
                    MenuPopup elem = (MenuPopup) value;
                    View view = mToolbar.findViewById(elem.getMenuId());
                    PopupMenu popup = new PopupMenu(this, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(elem.getMenuRes(), popup.getMenu());
                    popup.show();

                    PopupMenu.OnMenuItemClickListener listener = elem.getListener();
                    if (listener != null) {
                        popup.setOnMenuItemClickListener(listener);
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 隐藏menu
     *
     * @param menu
     */
    public void hideMenu(MenuBase menu) {
        if (menu != null) {
            hideMenu(menu.getMenuId());
            menu.setMenuId(0);
        }
    }

    /**
     * 隐藏menu
     *
     * @param menuId
     */
    public void hideMenu(int menuId) {
        if (mMenus.get(menuId) != null) {
            mMenus.put(menuId, null);
            invalidateOptionsMenu();
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

    public void setTitleView(@LayoutRes int layoutResId) {
        if (layoutResId > 0) {
            View view = LayoutInflater.from(this).inflate(layoutResId, null);
            mViewTitleTop.setVisibility(View.VISIBLE);
            if (mViewTitleTop != null) mViewTitleTop.addView(view);
        }
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.base_id_content_retry) {
            retry();
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

    public void setRetryText(@StringRes int txtRes, @Nullable Drawable top) {
        setEmptyText(getString(txtRes), null, top, null, null);
    }

    public void setRetryText(String text, @Nullable Drawable start, @Nullable Drawable top,
                             @Nullable Drawable end, @Nullable Drawable bottom) {
        if (mTvRetry != null) {
            mTvRetry.setVisibility(View.VISIBLE);
            mTvRetry.setText(text);
            mTvRetry.setCompoundDrawables(start, top, end, bottom);
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
}
