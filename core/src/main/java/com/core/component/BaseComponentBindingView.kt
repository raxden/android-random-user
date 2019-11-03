package com.core.component

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseComponentBindingView<VDB : ViewDataBinding> : FrameLayout {

    protected abstract val mStyleable: IntArray
    protected abstract val mLayoutId: Int
    protected lateinit var mBinding: VDB

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor (context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) {
        if (!isInEditMode) {
            attrs?.also {
                context.theme.obtainStyledAttributes(it, mStyleable, defStyleAttr, defStyleRes).apply {
                    try {
                        onLoadStyledAttributes(this)
                    } finally {
                        recycle()
                    }
                }
            }
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mBinding = DataBindingUtil.inflate(inflater, mLayoutId, this, true)
            onBindingCreated(mBinding)
        }
    }

    abstract fun onLoadStyledAttributes(attrs: TypedArray)

    abstract fun onBindingCreated(binding: VDB)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode)
            View.inflate(context, mLayoutId, this)
    }
}

