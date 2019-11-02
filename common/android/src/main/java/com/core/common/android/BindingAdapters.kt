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
    @BindingAdapter("selected")
    fun selected(view: View, selected: Boolean) {
        view.isSelected = selected
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleInvisible")
    fun visibleInvisible(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("stringResource")
    fun stringResource(view: TextView, resourceId: Int) {
        if (resourceId != 0)
            view.setText(resourceId)
    }

    @JvmStatic
    @BindingAdapter(value = ["text_date", "text_date_format"], requireAll = false)
    fun parseLocalDateTimeToDate(
        view: TextView,
        date: LocalDateTime?,
        format: String? = "dd MMM yyyy"
    ) {
        view.text = date?.format(DateTimeFormatter.ofPattern(format ?: "dd MMM yyyy")) ?: view.text
    }

    @JvmStatic
    @BindingAdapter(value = ["text_date", "text_date_format"], requireAll = false)
    fun parseLocalDateToDate(view: TextView, date: LocalDate?, format: String? = "dd MMM yyyy") {
        view.text = date?.format(DateTimeFormatter.ofPattern(format ?: "dd MMM yyyy")) ?: view.text
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