package com.zhonghe.shiangou.ui.activity;

import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhonghe.lib_base.constant.CstScheme;
import com.zhonghe.shiangou.R;
import com.zhonghe.shiangou.data.bean.RefundImgInfo;
import com.zhonghe.shiangou.ui.adapter.RefundSubmitAdapter;
import com.zhonghe.shiangou.ui.baseui.BaseSelectImageActivity;
import com.zhonghe.shiangou.ui.widget.RatingBar;
import com.zhonghe.shiangou.utile.image.CropParams;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RemarkActivity extends BaseSelectImageActivity {


    @Bind(R.id.id_home_category_item_iv)
    SimpleDraweeView idHomeCategoryItemIv;
    @Bind(R.id.id_home_category_item_title_tv)
    TextView idHomeCategoryItemTitleTv;
    @Bind(R.id.id_refund_submit_desc_et)
    EditText idRefundSubmitDescEt;
    @Bind(R.id.id_refund_submit_img_gv)
    GridView idRefundSubmitImgGv;
    @Bind(R.id.id_item_remark_desc_tv)
    TextView idItemRemarkDescTv;


    //保存图片路径
    private String mPath;
    RefundSubmitAdapter adapter;
    RefundImgInfo infoadd;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_remark);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTop() {
        setTitle(R.string.remark_title);
        setNavigation(R.mipmap.common_nav_back);
    }

    @Override
    protected void initViews() {
        adapter = new RefundSubmitAdapter(this);
        idRefundSubmitImgGv.setAdapter(adapter);

        infoadd = new RefundImgInfo();
        infoadd.setAdd(true);
        adapter.addItem(infoadd);
        adapter.notifyDataSetChanged();
        adapter.setAddListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getCount() < 4)
                    selectPic(view);
            }
        });


//        RatingBar rbar_score = holder.getViewById(R.id.rbar_score);
//        rbar_score.setStar(item.getComment().getCommentScore());
//
//        holder.setText(R.id.et_content, item.getComment().getCommentContent());
//        rbar_score.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
//            @Override
//            public void onRatingChange(float ratingCount) {
//                item.getComment().setCommentScore((int) ratingCount);
//            }
//        });
    }

    private void selectPic(View parent) {
        CropParams params = new CropParams(this);
        params.compress = true;
        params.enable = false;
        params.compressQuality = 80;
        selectPicture(parent, params);
    }


    @Override
    public void onCompressed(Uri uri) {
        super.onCompressed(uri);
        if (uri != null) {
//            files = new ArrayList<String>();
            String url = null;
            url = uri.toString();
            RefundImgInfo info = new RefundImgInfo();
            info.setAdd(false);
            mPath = url.replace(CstScheme.FILE, "");
            info.setImgUrl(url);
            if (adapter.getCount() == 3) {
                adapter.getData().set(adapter.getCount() - 1, info);
            } else {
                adapter.addItem(adapter.getCount() - 1 < 0 ? 0 : adapter.getCount() - 1, info);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
