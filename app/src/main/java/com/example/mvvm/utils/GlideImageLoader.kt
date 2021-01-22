package com.example.mvvm.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by Lu
 * on 2018/3/17 21:30
 */
class GlideImageLoader:ImageLoader() {

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        Glide.with(context).load(path).into(imageView)
    }
}