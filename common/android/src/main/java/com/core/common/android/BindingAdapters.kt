package com.core.common.android

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.core.common.android.extensions.setSafeOnClickListener
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("onSafeClick")
    fun click(view: View, listener: View.OnClickListener) {
        view.setSafeOnClickListener { listener.onClick(it) }
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("strResource")
    fun strResource(view: TextView, resourceId: Int) {
        if (resourceId != 0)
            view.setText(resourceId)
    }

    @JvmStatic
    @BindingAdapter("srcVector")
    fun srcVector(view: ImageView, resourceId: Int) {
        view.setImageResource(resourceId)
    }

    @JvmStatic
    @BindingAdapter("urlImage")
    fun loadImage(view: ImageView, url: String?) {
        url?.run {
            Glide.with(view)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(CenterCrop())
                .into(view)
        } ?: view.run {
            setImageBitmap(null)
        }
    }
}