package com.zhonghe.lib_base.baseui;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.zhonghe.lib_base.R;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.lib_base.utils.UtileDevice;

/**
 * @desc   :
 */
public abstract class BaseDialog extends AbsDialog implements View.OnClickListener{
	protected Context mContext;

	private FrameLayout mFlRoot;
	private ViewStub mViewTop;
	private ViewStub mViewSingleBottom;
	private ViewStub mViewDoubleBottom;

	private TextView mTvTitle;
	private Button mBtnLeft;
	private Button mBtnRight;
	private Button mBtnSingle;

	private OnLeftListener mLeftListener;
	private OnRightListener mRightListener;
	private OnSingleListener mSingleListener;

	//数据加载中
	private ProgressWheel mPbLoading;
	//网络请求重试
	private TextView mTvRetry;

	private OnRetryListener mRetryListener;

    public interface OnRetryListener {
        public void onRetry();
    }

	public interface OnLeftListener {
        public void onLeft();
    }

    public interface OnRightListener {
        public void onRight();
    }

    public interface OnSingleListener {
        public void onSingle();
    }

	public BaseDialog(Context context) {
		this(context, R.style.res_theme_dialog_base);
	}

	public BaseDialog(Context context, int theme){
	    super(context, theme);
	    this.mContext = context;
	}

	@Override
	protected void initBaseViews() {
		View view = getLayoutInflater().inflate(R.layout.res_dialog_base, null);
        super.setContentView(view);

        mFlRoot = (FrameLayout)view.findViewById(R.id.dialog_base_id_root);
        mViewTop = (ViewStub)view.findViewById(R.id.dialog_base_id_top);
        mViewSingleBottom = (ViewStub)view.findViewById(R.id.dialog_base_id_single);
        mViewDoubleBottom = (ViewStub)view.findViewById(R.id.dialog_base_id_double);

		mPbLoading = (ProgressWheel) view.findViewById(R.id.base_net_id_progress);
		mTvRetry = (TextView)view.findViewById(R.id.base_net_id_retry);

		mTvRetry.setOnClickListener(this);

		setCanceledOnTouchOutside(false);
	}

	@Override
	public void show() {
		super.show();

		Window window = getWindow();
		// 获取对话框当前的参数值
	    LayoutParams params = window.getAttributes();
	    //高度
	    params.height = LayoutParams.WRAP_CONTENT;
	    //宽度设置为屏幕的0.9
	    params.width = (int) (UtileDevice.getDevice(mContext).getWidth() * 0.9);
	    window.setAttributes(params);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.dialog_double_bottom_id_left) {
				onLeft();
		} else if(id == R.id.dialog_double_bottom_id_right) {
			onRight();
		} else if(id == R.id.dialog_single_bottom_id_middle) {
			onSingle();
		} else if(id == R.id.base_net_id_retry) {
			retry();
		}
	}



	@Override
    public void setContentView(int layoutResId) {
		if(layoutResId > 0 ) {
	        View view = LayoutInflater.from(mContext).inflate(layoutResId, null);
	        mFlRoot.addView(view);
		}
    }


    @Override
    public void setContentView(View view) {
    	if(view != null) {
    		mFlRoot.addView(view);
    	}
    }

    public View findViewById(int id) {
    	return mFlRoot.findViewById(id);
    }


	/**
	 * 显示/隐藏重试图标
	 * @param retry
     */
	public void setRetry(boolean retry) {
		if(retry) {
			mFlRoot.setVisibility(View.GONE);
			mPbLoading.setVisibility(View.GONE);
			mTvRetry.setVisibility(View.VISIBLE);
		} else {
			mFlRoot.setVisibility(View.VISIBLE);
			mTvRetry.setVisibility(View.GONE);
		}
	}

    /**
     * 设置重试事件
     * @param listener
     */
    public void setOnRetryListener(OnRetryListener listener) {
    	this.mRetryListener = listener;
    }


	/**
	 * 是否显示加载过程
	 * @param loading
     */
	public void setLoading(boolean loading) {
		if(loading) {
			mFlRoot.setVisibility(View.GONE);
			mPbLoading.setVisibility(View.VISIBLE);
			mTvRetry.setVisibility(View.GONE);
		} else {
			mFlRoot.setVisibility(View.VISIBLE);
			mPbLoading.setVisibility(View.GONE);
		}
	}


    /**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title) {
		if(mTvTitle == null) {
			View view = mViewTop.inflate();
			mTvTitle = (TextView)view.findViewById(R.id.dialog_top_id_title);
		} else {
			mViewTop.setVisibility(View.VISIBLE);
		}

		mTvTitle.setText(title);
	}

	/**
	 * 设置标题
	 * @param resId
	 */
	public void setTitle(int resId) {
		if(resId > 0) {
			setTitle(mContext.getString(resId));
		}
	}

	/**
	 * 隐藏标题
	 */
	public void hideTitle() {
		if(mTvTitle != null) {
			mViewTop.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示双按钮
	 * @param left
	 * @param right
	 */
	public void setDouble(String left, String right) {
		if(mBtnLeft == null) {
			View view = mViewDoubleBottom.inflate();
			mBtnLeft = (Button)view.findViewById(R.id.dialog_double_bottom_id_left);
			mBtnRight = (Button)view.findViewById(R.id.dialog_double_bottom_id_right);
			mBtnLeft.setOnClickListener(this);
	        mBtnRight.setOnClickListener(this);
		} else {
			mViewDoubleBottom.setVisibility(View.VISIBLE);
		}

		mViewSingleBottom.setVisibility(View.GONE);
		mBtnLeft.setText(left);
		mBtnRight.setText(right);
	}

	/**
	 * 显示双按钮
	 * @param leftResId
	 * @param rightResId
	 */
	public void setDouble(int leftResId, int rightResId) {
		if(leftResId > 0 && rightResId > 0) {
			setDouble(mContext.getString(leftResId), mContext.getString(rightResId));
		}
	}

	/**
	 * 隐藏双按钮
	 */
	public void hideDouble() {
		if(mBtnLeft != null) {
			mViewDoubleBottom.setVisibility(View.GONE);
		}
	}

	/**
	 *
	 * @param leftListener
	 * @param rightListener
	 */
	public void setOnDoubleListener(OnLeftListener leftListener, OnRightListener rightListener) {
		this.mLeftListener = leftListener;
		this.mRightListener = rightListener;
	}

	/**
	 * 显示单按钮
	 * @param single
	 */
	public void setSingle(String single) {
		if(mBtnSingle == null) {
			View view = mViewSingleBottom.inflate();
			mBtnSingle = (Button)view.findViewById(R.id.dialog_single_bottom_id_middle);
			mBtnSingle.setOnClickListener(this);
		} else {
			mViewSingleBottom.setVisibility(View.VISIBLE);
		}

		mViewDoubleBottom.setVisibility(View.GONE);
		mBtnSingle.setText(single);
	}

	/**
	 * 显示单按钮
	 * @param singleResId
	 */
	public void setSingle(int singleResId) {
		if(singleResId > 0) {
			setSingle(mContext.getString(singleResId));
		}
	}

	/**
	 * 隐藏单按钮
	 */
	public void hideSingle() {
		if(mBtnSingle != null) {
			mViewSingleBottom.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置单按钮事件
	 * @param listener
	 */
	public void setOnSingleListener(OnSingleListener listener) {
		this.mSingleListener = listener;
	}

	/**
	 * 设置文本内容
	 * @param msg
	 */
	public void setMessage(String msg) {
		TextView tv = new TextView(mContext);
		tv.setText(Html.fromHtml(UtilString.nullToEmpty(msg)));
		tv.setTextColor(mContext.getResources().getColor(R.color.res_color_black_secondary_text));
		tv.setTextSize(16);
		setContentView(tv);
	}

	/**
	 * 设置文本内容
	 * @param resTxt
	 */
	public void setMessage(int resTxt) {
		if(resTxt > 0) {
			setMessage(mContext.getString(resTxt));
		}
	}

	/**
	 * 执行双按钮左按钮事件
	 */
	private void onLeft() {
		if(mLeftListener != null) {
			mLeftListener.onLeft();
		}
	}

	/**
	 * 执行双按钮右按钮事件
	 */
	private void onRight() {
		if(mRightListener != null) {
			mRightListener.onRight();
		}
	}

	/**
	 * 执行单按钮事件
	 */
	private void onSingle() {
		if(mSingleListener != null) {
			mSingleListener.onSingle();
		}
	}

	 /**
     * 重试加载
     */
    private void retry() {
    	if (mRetryListener != null) {
    		mRetryListener.onRetry();
        }
    }



}
