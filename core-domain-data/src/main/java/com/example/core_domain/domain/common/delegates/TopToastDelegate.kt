package com.example.core_domain.domain.common.delegates

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.core_domain.domain.common.components.CustomTopToastComponent

interface ITopToast {
    fun registerTopToastDelegate(context: Context, rootLayout: ViewGroup, index: Int? = null, insetTop: Int = 0)
    fun showTopToast(isError: Boolean, message: String, animationFinishAction: () -> Unit = {})
}

class TopToastDelegate : ITopToast {

    private lateinit var topToast: CustomTopToastComponent

    override fun registerTopToastDelegate(context: Context, rootLayout: ViewGroup, index: Int?, insetTop: Int) {
        topToast = CustomTopToastComponent(context)
        topToast.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
            topMargin += insetTop + 44
        }
        when (index) {
            null -> { rootLayout.addView(topToast) }
            else -> { rootLayout.addView(topToast, index) }
        }
    }

    override fun showTopToast(isError: Boolean, message: String, animationFinishAction: () -> Unit) {
        topToast.showTopToast(isError, message, animationFinishAction)
    }
}
