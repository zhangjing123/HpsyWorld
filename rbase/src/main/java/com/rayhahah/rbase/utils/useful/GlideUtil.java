package com.rayhahah.rbase.utils.useful;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.rayhahah.rbase.BaseApplication;
import com.rayhahah.rbase.R;
import com.rayhahah.rbase.utils.base.ThreadUtil;

import java.security.MessageDigest;

/**
 * Gilde管理类
 */
public class GlideUtil {

    /**
     * 加载资源到ImageView上
     *
     * @param context   上下文
     * @param res       目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView 目标
     */
    /*public static <T extends Object> void load(Context context, T res, ImageView imageView) {
        loadBase(withContext(context), res, R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp,
                imageView, Priority.NORMAL, new BaseTransformation(), null, 0, null);
    }

    *//**
     * 加载资源到ImageView上
     *
     * @param context   上下文
     * @param res       目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView 目标
     *//*
    public static <T extends Object> void load(Activity context, T res, ImageView imageView) {
        loadBase(withContext(context), res, R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp,
                imageView, Priority.NORMAL, new BaseTransformation(), null, 0, null);
    }

    *//**
     * 加载资源到ImageView上
     *
     * @param context   上下文
     * @param res       目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView 目标
     *//*
    public static <T extends Object> void load(Fragment context, T res, ImageView imageView) {
        loadBase(withContext(context), res,
                R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp, imageView,
                Priority.NORMAL, new BaseTransformation(), null, 0, null);
    }

    *//**
     * 加载资源到ImageView上
     *
     * @param context        上下文
     * @param res            目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView      目标
     * @param transformation 图像变形
     *//*
    public static <T extends Object> void loadWithTransform(Fragment context, T res, ImageView imageView, Transformation transformation) {
        loadBase(withContext(context), res,
                R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp, imageView,
                Priority.NORMAL, transformation, null, 0, null);
    }

    *//**
     * 加载资源到ImageView上
     *
     * @param context        上下文
     * @param res            目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView      目标
     * @param transformation 图像变形
     *//*
    public static <T extends Object> void loadWithTransform(Context context, T res, ImageView imageView, Transformation transformation) {
        loadBase(withContext(context), res,
                R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp, imageView,
                Priority.NORMAL, transformation, null, 0, null);
    }

    *//**
     * 加载资源到ImageView上
     *
     * @param context        上下文
     * @param res            目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView      目标
     * @param transformation 图像变形
     *//*
    public static <T extends Object> void loadWithTransform(Activity context, T res, ImageView imageView, Transformation transformation) {
        loadBase(withContext(context), res,
                R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp, imageView,
                Priority.NORMAL, transformation, null, 0, null);
    }


    *//**
     * 清除磁盘缓存
     * 只能在子线程执行
     *//*
    public static void clearDiskCache() {
        ThreadUtil.excute(new Runnable() {
            @Override
            public void run() {
                Glide.get(BaseApplication.getAppContext()).clearDiskCache();
            }
        });
    }

    */
    /**
     * 清除内存缓存
     * 可以在UI线程执行
     *//*
    public static void clearMemory() {
        Glide.get(BaseApplication.getAppContext()).clearMemory();
    }


    private static <C extends Fragment> RequestManager withContext(C context) {
        return Glide.with(context);
    }

    private static <C extends Context> RequestManager withContext(C context) {
        return Glide.with(context);
    }


    private static <R extends Object> void loadBase(
            RequestManager manager, R res, int phRes, int errorRes,
            ImageView target, Priority priority,
            Transformation transformation,
            ViewPropertyAnimation.Animator animator,
            int crossDuration, RequestListener requestListener
    ) {

        manager.load(res)
//                .asBitmap()//只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断
                .listener(requestListener)
                .placeholder(phRes)
                .error(errorRes)
                //.priority(priority)
                .animate(animator)//动画
                .bitmapTransform(transformation)//图形转换
                .diskCacheStrategy(DiskCacheStrategy.ALL)//全部启用硬盘缓存
                .crossFade(crossDuration)
//                .dontTransform()//取消图形变换
//                .skipMemoryCache(true)//是否开启内存缓存，默认开启
//                .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)//指定成图片原有像素的图片
                .into(target);
    }*/

    /*static class BaseTransformation implements Transformation {

        @Override
        public Resource transform(Resource resource, int outWidth, int outHeight) {
            return resource;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }*/

    public static final int placeholderSoWhite = R.color.white;
    public static final int errorSoWhite = R.color.white;
    // public static final int soWhite = R.color.white;

    /*
     *加载图片(默认)
     */
    public static void load(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderSoWhite) //占位图
                .error(context.getResources().getDrawable(R.drawable.center_img_user_default))       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void loadwithNobg(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                //.centerCrop()
                //.placeholder(placeholderSoWhite) //占位图
                //.error(context.getResources().getDrawable(R.drawable.center_img_user_default))       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void loadRetangle(Context context, String url, ImageView imageView) {
        //DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(400).setCrossFadeEnabled(true).build();
        RequestOptions options = new RequestOptions()
                //.centerCrop()
                .placeholder(context.getResources().getDrawable(R.drawable.default_img)) //占位图
                .error(context.getResources().getDrawable(R.drawable.default_img))       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url)
                .apply(options)
                //.transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                .into(imageView);

    }

    public static void loadBanner(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(context.getResources().getDrawable(R.drawable.default_retangle)) //占位图
                .error(context.getResources().getDrawable(R.drawable.default_retangle))       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    public static void loadRound(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderSoWhite) //占位图
                .error(context.getResources().getDrawable(R.drawable.center_img_user_default))       //错误图
                // .priority(Priority.HIGH)
                .circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        //options = RequestOptions.circleCropTransform();

        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /*
     *加载资源图片(默认)
     */
    public static void load(Context context, Object res, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderSoWhite) //占位图
                .error(R.color.white)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(res).apply(options).into(imageView);

    }
}
