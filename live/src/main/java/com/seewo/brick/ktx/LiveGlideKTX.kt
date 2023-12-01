package com.seewo.brick.ktx

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun <T : ViewGroup> T.liveGlideImage(
    urlOrPath: LiveData<String>,
    transition: DrawableTransitionOptions? = null,
    requestListener: RequestListener<Drawable>? = null,
    onLoadFailed: ((GlideException?) -> Unit)? = null,
    onResourceReady: ((Drawable) -> Unit)? = null,
    requestOptions: RequestOptions? = null,
    lifecycleOwner: LifecycleOwner? = null,
    block: T.() -> ImageView,
) = glideImage(
    null,
    transition,
    requestListener,
    onLoadFailed,
    onResourceReady,
    requestOptions,
    block,
).apply {
    if (lifecycleOwner != null) {
        urlOrPath.bind(lifecycleOwner) {
            loadImage(
                it,
                requestOptions,
                transition,
                requestListener,
                onLoadFailed,
                onResourceReady
            )
        }
    } else {
        urlOrPath.bind(context) {
            loadImage(
                it,
                requestOptions,
                transition,
                requestListener,
                onLoadFailed,
                onResourceReady
            )
        }
    }
}

private fun ImageView.loadImage(
    urlOrPath: String?,
    requestOptions: RequestOptions? = null,
    transition: DrawableTransitionOptions? = null,
    requestListener: RequestListener<Drawable>?,
    onLoadFailed: ((GlideException?) -> Unit)?,
    onResourceReady: ((Drawable) -> Unit)?
) {
    urlOrPath ?: return
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
                requestListener?.onResourceReady(
                    resource,
                    model,
                    target,
                    dataSource,
                    isFirstResource
                )
                return false
            }

        })
        .into(this)
}

fun <T : ViewGroup> T.liveGlideGif(
    urlOrPath: LiveData<String>,
    requestListener: RequestListener<GifDrawable>? = null,
    onLoadFailed: ((GlideException?) -> Unit)? = null,
    onResourceReady: ((GifDrawable) -> Unit)? = null,
    lifecycleOwner: LifecycleOwner? = null,
    block: T.() -> ImageView
) = glideGif(
    null,
    requestListener,
    onLoadFailed,
    onResourceReady,
    block,
).apply {
    if (lifecycleOwner != null) {
        urlOrPath.bind(lifecycleOwner) {
            loadGif(
                it,
                requestListener,
                onLoadFailed,
                onResourceReady
            )
        }
    } else {
        urlOrPath.bind(context) {
            loadGif(
                it,
                requestListener,
                onLoadFailed,
                onResourceReady
            )
        }
    }
}

private fun ImageView.loadGif(
    urlOrPath: String?,
    requestListener: RequestListener<GifDrawable>? = null,
    onLoadFailed: ((GlideException?) -> Unit)? = null,
    onResourceReady: ((GifDrawable) -> Unit)? = null,
) {
    urlOrPath ?: return
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