package com.zhonghe.lib_base.baseui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * @desc   :
 */
public abstract class AbsDialog extends Dialog{
	public AbsDialog(Context context) {
		super(context);
	}
	
	public AbsDialog(Context context, int theme){
	    super(context, theme);
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        initBaseViews();
        initLayout();
        initViews();
    }
	
	/**
     * 初始化
     */
    protected void init(Bundle savedInstanceState){
    	
    };
    
    /**
     * 初始化基类View
     */
    protected abstract void initBaseViews();
    
    /**
     * 初始化布局
     */
    protected abstract void initLayout();

    /**
     * 初始化视图
     */
    protected abstract void initViews();
}
