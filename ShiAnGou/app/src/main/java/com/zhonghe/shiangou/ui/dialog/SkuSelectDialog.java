package com.zhonghe.shiangou.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zhonghe.shiangou.R;


/**
 * @desc   :
 */
public class SkuSelectDialog extends PopupWindow {
    private View mMenuView;
    private View mLlRoot;
    private Context mContext;

    public SkuSelectDialog(Activity context, OnClickListener listener) {
        super(context); 
        mContext = context;
        LayoutInflater inflater = (LayoutInflater)context
        		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        mMenuView = inflater.inflate(R.layout.activity_skuselect, null);
        mLlRoot = (RelativeLayout)mMenuView.findViewById(R.id.sku_context_id_context);
//
//        //取消按钮
//        mMenuView.findViewById(R.id.photo_id_cancel).setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v) {
//                //销毁弹出框
//                dismiss();
//            }
//        });
//
//        //设置按钮监听
//       	//拍照
//        mMenuView.findViewById(R.id.photo_id_camera).setOnClickListener(listener);
//        //从相册选取图片
//        mMenuView.findViewById(R.id.photo_id_picked).setOnClickListener(listener);
      
        setContentView(mMenuView);   
        setWidth(LayoutParams.MATCH_PARENT);  
        setHeight(LayoutParams.MATCH_PARENT);
        setFocusable(true);  
        //设置弹出窗体动画效果  
        setAnimationStyle(R.style.commom_popup_fade_anim_style);  
        //实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0x7f000000);  
        //设置弹出窗体的背景  
        this.setBackgroundDrawable(dw);  
        
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
        mMenuView.setOnTouchListener(new OnTouchListener() {  
              
            public boolean onTouch(View v, MotionEvent event) {  
                 
                int height = mLlRoot.getTop();  
                int y=(int) event.getY();  
                if(event.getAction()==MotionEvent.ACTION_UP){  
                    if(y<height){  
                    	 dismiss();
                    }  
                } 
                
                return true;  
            }  
        }); 
        
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.common_push_up_in);
        mLlRoot.startAnimation(anim);
    }
    
    
	@Override
	public void dismiss() {
		Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.common_push_down_out);
		mLlRoot.startAnimation(anim);
		
        new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				 SkuSelectDialog.super.dismiss();
			}
       	  
         }, 300);
        
	}  
  
}  