package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 广告轮播adapter
 */
public class DynamicBannerAdapter extends PagerAdapter {

    private Context context;
    private List<View> views;
    private OnClickListener listener;
    List<? extends BaseBannerInfo> advertiseArray;


    public DynamicBannerAdapter(Context context, List<View> views, List<? extends BaseBannerInfo> advertiseArray) {
        this.context = context;
        this.views = views;
        this.advertiseArray = advertiseArray;
    }

    public DynamicBannerAdapter(Context context, List<View> views, ArrayList<? extends BaseBannerInfo> advertiseArray, OnClickListener listener) {
        this.context = context;
        this.views = views;
        this.advertiseArray = advertiseArray;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
//        return views.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        views.add((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int mPOSITION = position % advertiseArray.size();
        if (mPOSITION < 0) {
            mPOSITION = advertiseArray.size() + position;
        }
//        View view = views.get(mPOSITION);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
//        ViewParent vp =view.getParent();
//        if (vp!=null){
//            ViewGroup parent = (ViewGroup)vp;
//            parent.removeView(view);
//        }
//        String head_img = advertiseArray.get(mPOSITION).getImgUrl();
//        ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
//        Picasso.with(context).load(R.mipmap.banner).into(ivAdvertise);

        View itemView;
        if (views.size() == 0) {
            itemView = LayoutInflater.from(context).inflate(R.layout.advertisement_item_fitxy, null);
        } else {
            itemView = views.remove(views.size() - 1);
        }
        ImageView ivAdvertise = (ImageView) itemView.findViewById(R.id.ivAdvertise);
        Picasso.with(context).load(R.mipmap.banner).into(ivAdvertise);
        container.addView(itemView);
        return itemView;
    }

}
