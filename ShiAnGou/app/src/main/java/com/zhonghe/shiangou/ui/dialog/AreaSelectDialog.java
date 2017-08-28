package com.zhonghe.shiangou.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.AddressInfo;
import com.zhonghe.shiangou.ui.widget.PickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/28
 * desc:地址选择
 */

public class AreaSelectDialog extends PopupWindow {
    @Bind(R.id.id_area_province)
    PickerView idAreaProvince;
    @Bind(R.id.id_area_city)
    PickerView idAreaCity;
    @Bind(R.id.id_area_district)
    PickerView idAreaDistrict;
    @Bind(R.id.photo_id_root)
    LinearLayout photoIdRoot;
    @Bind(R.id.photo_id_cancel)
    TextView photoIdCancel;
    private View mMenuView;
    private Context mContext;
    private View mLlRoot;

    public AreaSelectDialog(Context context, List<AddressInfo> provinceList) {
        super(context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_area_select, null);
        mLlRoot = (LinearLayout) mMenuView.findViewById(R.id.photo_id_root);
        idAreaProvince = ButterKnife.findById(mMenuView, R.id.id_area_province);
        idAreaCity = ButterKnife.findById(mMenuView, R.id.id_area_city);
        idAreaDistrict = ButterKnife.findById(mMenuView, R.id.id_area_district);
        setAddressShow(idAreaProvince, provinceList);

        //取消按钮
        mMenuView.findViewById(R.id.photo_id_cancel).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });

        setContentView(mMenuView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        //设置弹出窗体动画效果
        setAnimationStyle(R.style.commom_popup_fade_anim_style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x7f000000);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);


        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mLlRoot.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
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

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                AreaSelectDialog.super.dismiss();
            }

        }, 300);

    }

    void setAddressShow(PickerView pv, List<AddressInfo> provinceList) {
        List<String> areaStr = new ArrayList<>();
        for (AddressInfo ainfo : provinceList) {
            areaStr.add(ainfo.getAddress_name());
        }
        pv.setData(areaStr);
        //        pickerView.setSelected(MainActivity.num-1);
//        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
//            @Override
//            public void onSelect(String text) {
//                MainActivity.num=Integer.parseInt(text);
//            }
//        });
    }
}
