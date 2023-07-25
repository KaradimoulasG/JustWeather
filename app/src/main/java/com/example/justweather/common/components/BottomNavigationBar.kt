package com.example.justweather.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.justweather.databinding.BottomNavigationBarBinding

class BottomNavigationBar@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private var binding = BottomNavigationBarBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.bottomNavigationView.apply {
            background = null
//            menu.getItem(4).isEnabled = false
        }
    }
}
