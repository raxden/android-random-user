package com.core.features.common

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.core.component.BaseComponentView
import kotlinx.android.synthetic.main.avatar_view.view.*

class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : BaseComponentView(context, attrs, defStyleAttr, defStyleRes) {

    enum class Gender { MALE, FEMALE }

    private var mImage: String = ""
    private var mGender: Gender = Gender.MALE

    override val mStyleable: IntArray
        get() = R.styleable.AvatarView

    override val mLayoutId: Int
        get() = R.layout.avatar_view

    override fun onLoadStyledAttributes(attrs: TypedArray) {
        mImage = attrs.getString(R.styleable.AvatarView_av_image) ?: ""
        mGender = Gender.values()[attrs.getInt(R.styleable.AvatarView_av_gender, 0)]
    }

    override fun onViewCreated() {
        setImage(mImage)
    }

    fun setImage(image: String?) {
        Glide.with(avatar_image_view)
            .load(image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CircleCrop())
            .into(avatar_image_view)
    }
}