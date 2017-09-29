package com.zhonghe.shiangou.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.BaseBannerInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.utile.UtilPro;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: whyang
 * date: 2017/9/28
 * desc: 大图显示
 */

public class ImgDialog extends PopupWindow {
    private View mMenuView;
    private ViewPager vpAdvertise;
    private VPAdapter adapter;
    List<String> advertiseArray;
    Context mContext;

    public ImgDialog(Context context, List<String> advertiseArray, int pos) {
        super(context);
        mContext = context;
        this.advertiseArray = advertiseArray;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_img_vp, null);
        vpAdvertise = (ViewPager) mMenuView.findViewById(R.id.vpAdvertise);
        adapter = new VPAdapter(mContext);
        vpAdvertise.setAdapter(adapter);
        vpAdvertise.setCurrentItem(pos);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        mMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x7f000000);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        setContentView(mMenuView);
    }

    public class VPAdapter extends PagerAdapter {

        private Context context;
        private List<View> views;


        public VPAdapter(Context context) {
            this.context = context;
            this.views = new ArrayList<>();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return advertiseArray.size();
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


            View itemView;
            if (views.size() == 0) {
                itemView = LayoutInflater.from(context).inflate(R.layout.advertisement_item_fitxy, null);
            } else {
                itemView = views.remove(views.size() - 1);
            }
            SimpleDraweeView ivAdvertise = (SimpleDraweeView) itemView.findViewById(R.id.ivAdvertise);
            String images = advertiseArray.get(position);
            ProjectApplication.mImageLoader.loadImage(ivAdvertise, UtilPro.getImgHttpUrl(images));
//            final int finalMPOSITION = position;
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.OnVpClick(finalMPOSITION);
//                }
//            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            container.addView(itemView);
            return itemView;
        }

    }
}
