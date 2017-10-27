package com.zhonghe.shiangou.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.baseui.adapter.AbsAdapter;
import com.zhonghe.lib_base.utils.UtilString;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.RefundImgInfo;
import com.zhonghe.shiangou.system.global.ProjectApplication;
import com.zhonghe.shiangou.ui.activity.RemarkActivity;
import com.zhonghe.shiangou.ui.dialog.ImgDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by a on 2017/8/16.
 */

public class RefundSubmitAdapter extends AbsAdapter<RefundImgInfo> {
    View.OnClickListener addlistener;
    private CancelImgListener onCancelImg;
    boolean isCancel = false;

    public RefundSubmitAdapter(Context context, boolean isCancel) {
        super(context);
        this.isCancel = isCancel;
    }

    public void setAddListener(View.OnClickListener addlistener) {
        this.addlistener = addlistener;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_refund_img, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        RefundImgInfo info = mList.get(position);
        if (info.isAdd()) {
            holder.idRefundImgitemLl.setVisibility(View.VISIBLE);
            holder.idRefundImgitemLl.setOnClickListener(this.addlistener);
        } else {

            holder.idRefundImgitemLl.setVisibility(View.GONE);
        }
        if (UtilString.isNotBlank(info.getImgUrl())) {
            ProjectApplication.mImageLoader.loadImage(holder.idRefundImgitemImg, info.getImgUrl());
        }
        if (!isCancel) {
            holder.idRefundCancelImgIb.setVisibility(View.INVISIBLE);
        } else {
            holder.idRefundCancelImgIb.setVisibility(View.VISIBLE);
        }

        holder.idRefundCancelImgIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(position);
                onCancelImg.OnCancelPosition(position);
                if (getCount() < 3) {
                    RefundImgInfo infoadd = new RefundImgInfo();
                    infoadd.setAdd(true);
                    boolean needadd = false;
                    for (RefundImgInfo info : mList
                            ) {
                        if (info.isAdd()) {
                            needadd = true;
                            break;
                        }

                    }
                    if (!needadd) {
                        addItem(getCount(), infoadd);
                    }
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }

    public void setOnCancelImg(CancelImgListener onCancelImg) {
        this.onCancelImg = onCancelImg;
    }

    static class ViewHolder {
        @BindView(R.id.id_refund_imgitem_img)
        SimpleDraweeView idRefundImgitemImg;
        @BindView(R.id.id_refund_imgitem_ll)
        LinearLayout idRefundImgitemLl;
        @BindView(R.id.id_refund_cancel_img_ib)
        ImageButton idRefundCancelImgIb;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface CancelImgListener {
        void OnCancelPosition(int position);
    }
}
