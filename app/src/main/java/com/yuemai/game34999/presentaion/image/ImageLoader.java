package com.yuemai.game34999.presentaion.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.yuemai.game34999.R;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/29  16:40
 * @email : 923080261@qq.com
 * @description : 图片加载
 */
public class ImageLoader {

    public static void loadCache(Context context, String url, ImageView view) {
        GlideApp.with(context)
                .load(url)
                .error(R.drawable.default_gift_bg)
                .placeholder(R.drawable.default_gift_bg)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(view);
    }

    /**
     * 加载游戏图标
     *
     * @param context
     * @param obj
     * @param view
     */
    public static void loadGameIcon(Context context, Object obj, ImageView view) {
        loadImage(context, obj, R.drawable.default_game_icon, view);
    }

    /**
     * 加载新闻列表图片
     *
     * @param context
     * @param obj
     * @param view
     */
    public static void loadNewsItemImage(Context context, Object obj, ImageView view) {
        loadImage(context, obj, R.drawable.default_news, view);
    }

    /**
     * 加载新闻列表图片
     *
     * @param context
     * @param obj
     * @param view
     */
    public static void loadNewsBigImage(Context context, Object obj, ImageView view) {
        loadImage(context, obj, R.drawable.default_gift_bg, view);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param obj
     * @param errorRes
     * @param placeholderRes
     * @param view
     */
    public static void loadImage(Context context, Object obj, int errorRes, int placeholderRes, ImageView view) {
        GlideApp.with(context)
                .load(obj)
                .error(errorRes)
                .placeholder(placeholderRes)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(view);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param obj
     * @param imgRes
     * @param view
     */
    public static void loadImage(Context context, Object obj, int imgRes, ImageView view) {
        loadImage(context, obj, imgRes, imgRes, view);
    }


    public static void clear(Context context) {
        GlideApp.get(context).clearMemory();
    }

    public static void cancel(Context context, ImageView view) {
        GlideApp.with(context).clear(view);
    }

}