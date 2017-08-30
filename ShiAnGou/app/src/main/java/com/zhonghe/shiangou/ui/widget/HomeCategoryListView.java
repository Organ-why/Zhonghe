package com.zhonghe.shiangou.ui.widget;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.lib_base.utils.Utilm;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.GoodsInfo;
import com.zhonghe.shiangou.data.bean.HomeCategoryInfo;
import com.zhonghe.shiangou.data.bean.ProInfo;
import com.zhonghe.shiangou.system.global.ProDispatcher;
import com.zhonghe.shiangou.system.global.ProjectApplication;

import java.util.List;

/**
 * Date: 2017/8/7.
 * Author: whyang
 * 分类商品列表
 */
public class HomeCategoryListView {
    private Context context;
    private LayoutInflater inflater;

    public HomeCategoryListView(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void initView(List<HomeCategoryInfo> categoryInfos, ViewGroup parentView) {
        for (HomeCategoryInfo info : categoryInfos) {
            View inflate = inflater.inflate(R.layout.layout_home_category_item, null);
            SimpleDraweeView img = (SimpleDraweeView) inflate.findViewById(R.id.id_home_category_item_title_iv);//分类大图片
            ProjectApplication.mImageLoader.loadImage(img, info.getCat_images());
            LinearLayout itemList = (LinearLayout) inflate.findViewById(R.id.id_home_category_item_ll);//商品列表
            if (UtilList.isNotEmpty(info.getList())) {
                for (final GoodsInfo itemInfo : info.getList()) {
                    View itemView = inflater.inflate(R.layout.item_home_category_item, null);
                    SimpleDraweeView proimg = (SimpleDraweeView) itemView.findViewById(R.id.id_home_category_item_iv);
                    ProjectApplication.mImageLoader.loadImage(proimg, itemInfo.getGoods_img());
                    TextView tvtitle = (TextView) itemView.findViewById(R.id.id_home_category_item_title_tv);
                    tvtitle.setText(UtilString.nullToEmpty(itemInfo.getGoods_name()));
                    TextView tvprice = (TextView) itemView.findViewById(R.id.id_home_category_item_price_tv);
                    tvprice.setText(UtilString.nullToEmpty(itemInfo.getShop_price()));
                    TextView tvmarketprice = (TextView) itemView.findViewById(R.id.id_home_category_item_oldprice_tv);
                    tvmarketprice.setText(UtilString.nullToEmpty(itemInfo.getMarket_price()));
                    tvmarketprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ProDispatcher.goGoodsDetailActivity(context, itemInfo.getGoods_id());
                        }
                    });
                    itemList.addView(itemView);
                }
            }
            parentView.addView(inflate);
        }

    }
}
