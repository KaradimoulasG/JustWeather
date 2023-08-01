package com.example.justweather.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.justweather.common.extensions.hide
import com.example.justweather.common.extensions.show
import com.example.justweather.databinding.SearchBarComponentBinding

class SearchBarComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0,
) : RelativeLayout(context, attrs, defStyleRes) {

    private val binding = SearchBarComponentBinding.inflate(LayoutInflater.from(context), this)
    private var searchModeToChangeIcons: Boolean = true

    fun cancelSearch(action: () -> Unit) {
        binding.apply {
            cancelSearch.setOnClickListener {
                searchEt.text.clear()
                searchModeToChangeIcons = false
                hideIcons()
                action()
            }
        }
    }

    fun cleanSearch(action: () -> Unit) {
        binding.apply {
            cleanSearch.setOnClickListener {
                searchEt.text.clear()
                searchModeToChangeIcons = true
                showIcons()
                action()
            }
        }
    }
//    fun textChangesListener(timeOut: Long, lifecycleScope: LifecycleCoroutineScope, action: (CharSequence) -> Unit) {
//        binding.searchEt.textChanges().filterNot {
//            hideIcons()
//            it.isNullOrBlank()
//        }.debounce(timeOut)
//            .onEach { char ->
//                char?.let {
//                    action(it)
//                    showIcons()
//                }
//            }
//            .launchIn(lifecycleScope)
//    }

    fun eventKeyListener(action: (String) -> Unit) {
        binding.searchEt.apply {
            setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    action(text.toString())
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    fun setHint(hint: String) {
        binding.searchEt.hint = hint
    }

    private fun showIcons() {
        binding.apply {
            cleanSearch.show()
            cancelSearch.show()
        }
    }

    private fun hideIcons() {
        binding.apply {
            cleanSearch.hide()
            cancelSearch.hide()
        }
    }

    fun getSearchText(): String = binding.searchEt.text.toString()

    fun setSearchMode() { searchModeToChangeIcons = true }
    fun getSearchMode() = searchModeToChangeIcons
}
