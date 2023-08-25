package com.example.core_domain.domain.common.components

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import com.example.core_domain.R
import com.example.core_domain.databinding.TopToastComponentBinding
import com.example.core_domain.domain.common.extensions.hide
import com.example.core_domain.domain.common.extensions.show

class CustomTopToastComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {
    private var binding = TopToastComponentBinding.inflate(LayoutInflater.from(context), this)
    private var countDownTimer: CountDownTimer? = null

    fun showTopToast(
        isErrorMessage: Boolean,
        message: String,
        animationFinishAction: () -> Unit = {},
    ) {
        val slideIn = AnimationUtils.loadAnimation(context, R.anim.transition_top_in)
        val slideOut = AnimationUtils.loadAnimation(context, R.anim.transition_top_out)

        binding.topToast.background =
            when (isErrorMessage) {
                true -> {
                    AppCompatResources.getDrawable(context, R.drawable.red_message_background)
                }
                false -> {
                    AppCompatResources.getDrawable(context, R.drawable.green_message_background)
                }
            }

        binding.topToast.apply {
            text = message
            show()
            startAnimation(slideIn)
        }

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) { }

            override fun onFinish() {
                binding.topToast.apply {
                    startAnimation(slideOut)
                    slideOut.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {}
                        override fun onAnimationRepeat(p0: Animation?) {}
                        override fun onAnimationEnd(p0: Animation?) {
                            hide()
                            animationFinishAction()
                        }
                    })
                }
                this.cancel()
            }
        }.start()
    }
}