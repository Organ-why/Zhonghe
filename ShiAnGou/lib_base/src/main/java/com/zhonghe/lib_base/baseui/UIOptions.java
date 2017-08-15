package com.zhonghe.lib_base.baseui;

/**
 * Date: 2017/8/3.
 * Author: whyang
 * 页面类型选项
 */
public class UIOptions {
    public static final long UI_OPTIONS_SYSTEMBAR = 0x1;


    //拥有ToolBar的AppBar
    public static final long UI_OPTIONS_APPBAR_TOOLBAR = UI_OPTIONS_SYSTEMBAR<<1;
    //自定义AppBar
    public static final long UI_OPTIONS_APPBAR_CUSTIOM = UI_OPTIONS_SYSTEMBAR<<12;
    //自定义内容
    public static final long UI_OPTIONS_CONTENT_CUSTOM = UI_OPTIONS_SYSTEMBAR<<2;

    public static final long UI_OPTIONS_SCREEN_TOP = UI_OPTIONS_SYSTEMBAR<<21;
//    public static final long UI_OPTIONS_CONTENT_CUSTOM = UI_OPTIONS_SYSTEMBAR<<22;
    //底部Tab导航栏
    public static final long UI_OPTIONS_NAVBAR_TABS = UI_OPTIONS_SYSTEMBAR<<3;

    //拥有Tab的AppBar
    public static final long UI_OPTIONS_APPBAR_TABS = UI_OPTIONS_SYSTEMBAR<<4;

    //默认构造界面
    public static final long UI_OPTIONS_SCREEN_DEFAULT = UI_OPTIONS_SYSTEMBAR;



    //普通带标题栏&Tab栏的界面
    public static final long UI_OPTIONS_SCREEN_TOP_TABS = UI_OPTIONS_SYSTEMBAR
            | UI_OPTIONS_APPBAR_TOOLBAR
            | UI_OPTIONS_APPBAR_TABS;
}
