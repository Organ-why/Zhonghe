package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.Util;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.RefundImgInfo;
import com.zhonghe.shiangou.data.bean.RemarkInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * auther: whyang
 * date: 2017/8/29
 * desc: 评论列表
 */

public class RemarkAdapter extends AbsAdapter<RemarkInfo> {
    RefundSubmitAdapter adapter;

    public RemarkAdapter(Context context, List<RemarkInfo> addlist) {
        super(context, addlist);
        adapter = new RefundSubmitAdapter(context,false);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_remark, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        RemarkInfo info = mList.get(i);
        ProjectApplication.mImageLoader.loadCircleImage(holder.idItemRemarkHeaderImg,info.getUser_pic());
        holder.idItemRemarkNameTv.setText(UtilString.nullToEmpty(info.getNick_name()));
        holder.idItemRemarkDateTv.setText(Util.formatDate(info.getAdd_time(), Util.ALLDATE));
        holder.idItemRemarkDescTv.setText(UtilString.nullToEmpty(info.getContent()));
        List<RefundImgInfo> imgList = new ArrayList<>();
        for (String img : info.getImg()) {
            RefundImgInfo imginfo = new RefundImgInfo();
            imginfo.setAdd(false);
            imginfo.setImgUrl(img);
            imgList.add(imginfo);
        }
//        holder.ratingBar.setStar(info.get);
        adapter.setList(imgList);
        holder.idRefundSubmitImgGv.setAdapter(adapter);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.id_item_remark_header_img)
        SimpleDraweeView idItemRemarkHeaderImg;
        @BindView(R.id.id_item_remark_name_tv)
        TextView idItemRemarkNameTv;
        @BindView(R.id.id_item_remark_date_tv)
        TextView idItemRemarkDateTv;
        @BindView(R.id.id_item_remark_desc_tv)
        TextView idItemRemarkDescTv;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.id_refund_submit_img_gv)
        GridView idRefundSubmitImgGv;
        @BindView(R.id.id_layout_remark)
        RelativeLayout idLayoutRemark;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
