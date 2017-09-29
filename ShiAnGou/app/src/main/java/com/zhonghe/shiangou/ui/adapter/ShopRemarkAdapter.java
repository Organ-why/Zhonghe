package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilList;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.RefundImgInfo;
import com.zhonghe.shiangou.data.bean.ShopRemarkInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.widget.RatingBar;
import com.zhonghe.shiangou.utile.UtilPro;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/9/21
 * desc: 商户评论
 */

public class ShopRemarkAdapter extends AbsAdapter<ShopRemarkInfo> {
    private RefundSubmitAdapter adapter;
    private OnImgClick click;

    public ShopRemarkAdapter(Context context) {
        super(context);

    }

    public void setImgClick(OnImgClick click) {
        this.click = click;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_unline_shop_remark, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ShopRemarkInfo info = mList.get(position);
        ProjectApplication.mImageLoader.loadCircleImage(holder.idItemRemarkHeaderImg, UtilPro.getImgHttpUrl(info.getUser_pic()));
        holder.idItemRemarkNameTv.setText(UtilString.nullToEmpty(info.getNick_name()));
        holder.idItemRemarkDateTv.setText(UtilString.nullToEmpty(Util.formatDate(info.getDatetime(), Util.ALLDATETIME)));
        holder.ratingBar.setStar(info.getGrade());
        holder.idItemRemarkDescTv.setText(UtilString.nullToEmpty(info.getDetails()));
        adapter = new RefundSubmitAdapter(mContext, false);
        List<RefundImgInfo> imgList = new ArrayList<>();
        final List<String> imgs = new ArrayList<>();
        if (UtilList.isNotEmpty(info.getPhotolist())) {
            for (ShopRemarkInfo.PhotolistBean img : info.getPhotolist()) {
                RefundImgInfo imginfo = new RefundImgInfo();
                imginfo.setAdd(false);
                imginfo.setImgUrl(UtilPro.getImgHttpUrl(img.getPath()));
                imgs.add(UtilPro.getImgHttpUrl(img.getPath()));
                imgList.add(imginfo);
            }
        } else {
            imgList.clear();
            imgs.clear();
        }
//        holder.ratingBar.setStar(info.get);
        adapter.setList(imgList);
        holder.idRefundSubmitImgGv.setAdapter(adapter);
        holder.idRefundSubmitImgGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                click.OnClickItem(imgs,position);
            }
        });
        return view;
    }

    public interface OnImgClick {
        void OnClickItem(List<String> imgs, int position);
    }

    static class ViewHolder {
        @Bind(R.id.id_item_remark_header_img)
        SimpleDraweeView idItemRemarkHeaderImg;
        @Bind(R.id.id_item_remark_name_tv)
        TextView idItemRemarkNameTv;
        @Bind(R.id.ids_popular_tv)
        TextView idsPopularTv;
        @Bind(R.id.ratingBar)
        RatingBar ratingBar;
        @Bind(R.id.id_item_remark_date_tv)
        TextView idItemRemarkDateTv;
        @Bind(R.id.id_item_remark_desc_tv)
        TextView idItemRemarkDescTv;
        @Bind(R.id.id_refund_submit_img_gv)
        GridView idRefundSubmitImgGv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
