package com.zhonghe.shiangou.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.zhonghe.shiangou.ui.dialog.UnlineTypePop;

import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/19
 * desc:
 */

public class PopListPresenter {
    private Context mContext;
    private UnlineTypePop popView;
    private List<String> mData;
    private OnPopItemClickListener listener;
    private float mRotation = 180f;
    //显示基础view，箭头旋转
    private View parentView, rotateView;

    /**
     * @param mContext
     * @param mData      list<String> 用于显示
     * @param parentView 显示基础view
     * @param rotateView 箭头
     * @param listener   item点击
     */
    public PopListPresenter(Context mContext, @NonNull final List<String> mData, View parentView, View rotateView, final OnPopItemClickListener listener) {
        this.mContext = mContext;
        this.mData = mData;
        this.listener = listener;
        this.parentView = parentView;
        this.rotateView = rotateView;

        popView = new UnlineTypePop(mContext, mData, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position != -1) {
                    listener.OnClickItem(mData.get(position), position);
                    popView.dismiss();
                }else {
                    TypeIconRotate();
                }
            }
        });


    }

    //箭头180转动
    void TypeIconRotate() {
//                ObjectAnimator anim = ObjectAnimator.ofFloat(typeIv, "rotation",  typeIv.getRotation(),typeIv.getRotation()+180f);
        ObjectAnimator anim = ObjectAnimator.ofFloat(rotateView, "rotation", rotateView.getRotation(), rotateView.getRotation() + mRotation);
        anim.setDuration(500);
        anim.start();
        mRotation = -mRotation;
    }

    public void show() {
        TypeIconRotate();
        popView.showAsDropDown(parentView);
    }

    public interface OnPopItemClickListener {
        void OnClickItem(String t, int position);
    }
}
