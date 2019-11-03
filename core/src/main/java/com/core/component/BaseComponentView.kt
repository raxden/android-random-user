package com.core.component

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

abstract class BaseComponentView : FrameLayout {

    protected abstract val mStyleable: IntArray
    protected abstract val mLayoutId: Int

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
            View.inflate(context, mLayoutId, this)
            onViewCreated()
        }
    }

    abstract fun onLoadStyledAttributes(attrs: TypedArray)

    abstract fun onViewCreated()

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode)
            View.inflate(context, mLayoutId, this)
    }
}
