package com.kuwai.ysy.utils.glide;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NiceImageView;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by 皓然 on 2017/8/19.
 */

public class GlideRoundLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */

        //Glide 加载图片简单用法

        RoundedCorners roundedCorners= new RoundedCorners(Utils.dp2px(10));

        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(path).apply(options).into(imageView);
        //Picasso 加载图片简单用法
//        Picasso.with(context).load(path).into(imageView);

        //用fresco加载图片简单用法，记得要写下面的createImageView方法
//        Uri uri = Uri.parse((String) path);
//        imageView.setImageURI(uri);
    }

    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        NiceImageView simpleDraweeView = new NiceImageView(context);
        //simpleDraweeView.setCornerRadius(Utils.dp2px(10));
        return simpleDraweeView;
    }

   /* @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        *//**
     注意：
     1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
     2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
     传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
     切记不要胡乱强转！
     *//*
        //Glide 加载图片简单用法
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(path).into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void loadRoundImage(Context context, String url, ImageView imageView) {

        Glide.with(context)
                .load(url)
                .into(imageView);
    }*/
}
