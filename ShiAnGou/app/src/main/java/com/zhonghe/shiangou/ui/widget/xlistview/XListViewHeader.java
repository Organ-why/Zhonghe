/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.zhonghe.shiangou.ui.widget.xlistview;



import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zhonghe.shiangou.R;


public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private ProgressBar mProgressBar;
	private int mState = STATE_NORMAL;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mProgressBar = (ProgressBar)findViewById(R.id.xlistview_header_progressbar);
		
	}

	public void setState(int state) {
		if (state == mState) return ;
		
		if (state == STATE_REFRESHING) {	// 显示进度
			mProgressBar.setVisibility(View.VISIBLE);
		} else {	// 显示箭头图片
			mProgressBar.setVisibility(View.INVISIBLE);
		}
		
		switch(state){
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				mProgressBar.setVisibility(View.VISIBLE);
			}
			if (mState == STATE_REFRESHING) {
				mProgressBar.setVisibility(View.VISIBLE);
			}
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mProgressBar.setVisibility(View.VISIBLE);
			}
			break;
		case STATE_REFRESHING:
			mProgressBar.setVisibility(View.VISIBLE);
			break;
			default:
		}
		
		mState = state;
	}
	
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

}
