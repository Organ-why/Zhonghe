package com.zhonghe.lib_base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.View;

import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.zhonghe.lib_base.R;

/**
 * Created by a on 2017/8/9.
 */

public class UtilImage {
    private static UtilImage mInstance;
    private GenericDraweeHierarchyBuilder mDefaultHierarchyBuilder;
    private GenericDraweeHierarchyBuilder mDefaultCropHierarchyBuilder;
    private GenericDraweeHierarchyBuilder mRoundedHierarchyBuilder;
    private GenericDraweeHierarchyBuilder mCircleHierarchyBuilder;

    private Postprocessor mSkewXPostprocessor;

    private UtilImage(Context ctx) {
        //fresco初始化
        Fresco.initialize(ctx);

        Resources res = ctx.getResources();

        //默认图片配置
        BitmapDrawable defaultPlaceholderDrawable = new BitmapDrawable(res,
                BitmapFactory.decodeResource(res, R.mipmap.common_image_default));
        mDefaultHierarchyBuilder = new GenericDraweeHierarchyBuilder(res)
                .setFadeDuration(300)
                .setActualImageScaleType(ScaleType.FIT_XY)
                .setPlaceholderImage(defaultPlaceholderDrawable);
        mDefaultCropHierarchyBuilder = new GenericDraweeHierarchyBuilder(res)
                .setFadeDuration(300)
                .setActualImageScaleType(ScaleType.CENTER_CROP)
                .setPlaceholderImage(defaultPlaceholderDrawable);

        //圆角图片配置
        float radius = Utilm.dip2px(ctx, 8);
        RoundingParams roundedParams = new RoundingParams();
        roundedParams.setRoundAsCircle(false);
        roundedParams.setCornersRadii(radius, radius, radius, radius);
        mRoundedHierarchyBuilder = new GenericDraweeHierarchyBuilder(res)
                .setFadeDuration(300)
                .setActualImageScaleType(ScaleType.CENTER_CROP)
                .setRoundingParams(roundedParams);

        //圆形图片配置
        RoundingParams circleParams = new RoundingParams();
        circleParams.setRoundAsCircle(true);
        mCircleHierarchyBuilder = new GenericDraweeHierarchyBuilder(res)
                .setFadeDuration(300)
                .setActualImageScaleType(ScaleType.CENTER_CROP)
                .setRoundingParams(circleParams);


        mSkewXPostprocessor = new BasePostprocessor() {
            @Override
            public String getName() {
                return "SkewXPostprocessor";
            }

            @Override
            public CloseableReference<Bitmap> process(
                    Bitmap sourceBitmap,
                    PlatformBitmapFactory bitmapFactory) {
                double value = 0.28;
                int width = sourceBitmap.getWidth();
                int height = sourceBitmap.getHeight();
                int skewX = (int) (width * value);
                double step = width * value / height;

                CloseableReference<Bitmap> bitmapRef = bitmapFactory.createBitmap(
                        sourceBitmap.getWidth() + skewX,
                        sourceBitmap.getHeight());
                try {

                    Bitmap destBitmap = bitmapRef.get();
                    destBitmap.setHasAlpha(true);
                    for (int y = 0; y < height; y += 1) {
                        for (int x = 0; x < width; x += 1) {
                            destBitmap.setPixel((int) (x + skewX - y * step), y, sourceBitmap.getPixel(x, y));
                        }
                    }

                    return CloseableReference.cloneOrNull(bitmapRef);
                } finally {
                    CloseableReference.closeSafely(bitmapRef);
                }
            }

        };
    }


    /**
     * 单例模式
     *
     * @param ctx 上下文
     * @return
     */
    public synchronized static UtilImage getInstance(Context ctx) {
        if (null == mInstance) {
            mInstance = new UtilImage(ctx);
        }

        return mInstance;
    }

    /**
     * 加载图片
     *
     * @param view
     * @param url
     */
    public void loadImage(SimpleDraweeView view, String url) {
        if (view != null && UtilString.isNotBlank(url)) {
            view.setHierarchy(mDefaultHierarchyBuilder.build());
            Uri uri = Uri.parse(url);
            view.setImageURI(uri);
        }
    }

    /**
     * 图片  不裁剪
     * @param view
     * @param url
     */
    public void loadCropImage(SimpleDraweeView view, String url) {
        if (view != null && UtilString.isNotBlank(url)) {
            view.setHierarchy(mDefaultCropHierarchyBuilder.build());
            Uri uri = Uri.parse(url);
            view.setImageURI(uri);
        }
    }

    /**
     * 加载图片
     *
     * @param view
     * @param url
     */
    public void loadImage(SimpleDraweeView view, String url, GenericDraweeHierarchyBuilder builder) {
        if (view != null && UtilString.isNotBlank(url)) {
            view.setHierarchy(builder.build());
            Uri uri = Uri.parse(url);
            view.setImageURI(uri);
        }
    }

    /**
     * 加载带圆角的图片
     *
     * @param view
     * @param url
     */
    public void loadRoundedImage(SimpleDraweeView view, String url) {
        if (view != null && UtilString.isNotBlank(url)) {
            view.setHierarchy(mRoundedHierarchyBuilder.build());
            Uri uri = Uri.parse(url);
            view.setImageURI(uri);
        }
    }

    /**
     * 加载圆形图片
     *
     * @param view
     * @param url
     */
    public void loadCircleImage(SimpleDraweeView view, String url) {
        if (view != null && UtilString.isNotBlank(url)) {
            view.setHierarchy(mCircleHierarchyBuilder.build());
            Uri uri = Uri.parse(url);
            view.setImageURI(uri);
        }
    }


    /**
     * 加载水平倾斜图片
     *
     * @param view
     * @param url
     */
    public void loadSkewXImage(SimpleDraweeView view, String url) {
        if (view != null) {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                    .setPostprocessor(mSkewXPostprocessor)
                    .build();

            PipelineDraweeController controller = (PipelineDraweeController)
                    Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(view.getController())
                            .build();
            view.setController(controller);
        }
    }


    /**
     * 把View绘制到Bitmap上
     * //     * @param view 需要绘制的View
     *
     * @param width  该View的宽度
     * @param height 该View的高度
     * @return 返回Bitmap对象
     * add by csj 13-11-6
     */
    @SuppressLint("NewApi")
    public Bitmap getViewBitmap(View comBitmap, int width, int height) {
        Bitmap bitmap = null;
        if (comBitmap != null) {
            comBitmap.clearFocus();
            comBitmap.setPressed(false);

            boolean willNotCache = comBitmap.willNotCacheDrawing();
            comBitmap.setWillNotCacheDrawing(false);

            // Reset the drawing cache background color to fully transparent
            // for the duration of this operation
            int color = comBitmap.getDrawingCacheBackgroundColor();
            comBitmap.setDrawingCacheBackgroundColor(0);
            float alpha = comBitmap.getAlpha();
            comBitmap.setAlpha(1.0f);

            if (color != 0) {
                comBitmap.destroyDrawingCache();
            }

            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            comBitmap.measure(widthSpec, heightSpec);
            comBitmap.layout(0, 0, width/4, height/4);
//            comBitmap.layout(0, 0, width, height);

            comBitmap.buildDrawingCache();
            Bitmap cacheBitmap = comBitmap.getDrawingCache();
            if (cacheBitmap == null) {
//                Log.e("view.ProcessImageToBlur", "failed getViewBitmap(" + comBitmap + ")",
//                        new RuntimeException());
                return null;
            }
            bitmap = Bitmap.createBitmap(cacheBitmap);
//            bitmap.setHeight(height / 2);
//            bitmap.setWidth(width / 2);
            // Restore the view
            comBitmap.setAlpha(alpha);
            comBitmap.layout(0, 0, width, height);
            comBitmap.destroyDrawingCache();
            comBitmap.setWillNotCacheDrawing(willNotCache);
            comBitmap.setDrawingCacheBackgroundColor(color);
        }
        return bitmap;
    }
}
