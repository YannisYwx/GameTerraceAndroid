package com.yuemai.game34999.data.net;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yannis.common.widget.banner.loader.ImageLoader;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/16  16:36
 * @email : 923080261@qq.com
 * @description :
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
