package com.cts.mywall.util

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cts.mywall.R

class BindingUtils {
    companion object {

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: ImageView, url: String) {

            val options: RequestOptions = RequestOptions()
                .fitCenter()
                .error(R.mipmap.ic_launcher)
            Glide.with(view.context).load(url).apply(options).into(view)
        }

        @JvmStatic
        @BindingAdapter("imageColor")
        fun imageColor(view: ImageView, color: String) {
            view.setColorFilter(Color.parseColor(color))
        }
    }
}