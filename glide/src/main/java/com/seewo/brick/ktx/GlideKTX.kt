package com.seewo.brick.ktx

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.Rotate
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.seewo.brick.glide.exception.GlideBlurTransformation
import com.seewo.brick.params.CornerRadius

@SuppressLint("CheckResult")
fun ViewGroup.glideImage(
    urlOrPath: String,
    transition: DrawableTransitionOptions? = null,
    requestListener: RequestListener<Drawable>? = null,
    onLoadFailed: ((GlideException?) -> Unit)? = null,
    onResourceReady: ((Drawable) -> Unit)? = null,
    requestOptions: RequestOptions? = null,
    block: ViewGroup.() -> ImageView,
) = block().apply {
    Glide.with(this)
        .load(urlOrPath)
        .apply {
            requestOptions?.let { apply(it) }
            transition?.let { transition(it) }
        }
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadFailed?.invoke(e)
                requestListener?.onLoadFailed(e, model, target, isFirstResource)
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onResourceReady?.invoke(resource)
                requestListener?.onResourceReady(resource, model, target, dataSource, isFirstResource)
                return false
            }

        })
        .into(this)
}

fun ViewGroup.glideGif(
    urlOrPath: String,
    requestListener: RequestListener<GifDrawable>? = null,
    onLoadFailed: ((GlideException?) -> Unit)? = null,
    onResourceReady: ((GifDrawable) -> Unit)? = null,
    block: ViewGroup.() -> ImageView
) = block().apply {
    Glide.with(this)
        .asGif()
        .load(urlOrPath)
        .listener(object : RequestListener<GifDrawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<GifDrawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadFailed?.invoke(e)
                requestListener?.onLoadFailed(e, model, target, isFirstResource)
                return false
            }

            override fun onResourceReady(
                resource: GifDrawable,
                model: Any?,
                target: Target<GifDrawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onResourceReady?.invoke(resource)
                requestListener?.onResourceReady(resource, model, target, dataSource, isFirstResource)
                return false
            }

        })
        .into(this)
}

fun RequestOptions.roundedCorners(
    radius: CornerRadius,
) = transform(GranularRoundedCorners(
    radius.leftTop, radius.rightTop, radius.rightBottom, radius.leftBottom)
)

fun RequestOptions.rotate(
    degrees: Int,
) = transform(Rotate(degrees))

fun RequestOptions.blur(
    radius: Float,
) = transform(GlideBlurTransformation(applicationContext, radius))