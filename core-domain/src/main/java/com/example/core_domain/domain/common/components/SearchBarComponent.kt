package com.example.core_domain.domain.common.components

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.core_domain.databinding.SearchBarComponentBinding
import com.example.core_domain.domain.common.extensions.hide
import com.example.core_domain.domain.common.extensions.show

class SearchBarComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0,
) : RelativeLayout(context, attrs, defStyleRes) {

    private val binding = SearchBarComponentBinding.inflate(LayoutInflater.from(context), this)
    private var searchModeToChangeIcons: Boolean = true

//    fun cancelSearch(action: () -> Unit) {
//        binding.apply {
//            cancelSearch.setOnClickListener {
//                searchEt.text.clear()
//                searchModeToChangeIcons = false
//                hideIcons()
//                action()
//            }
//        }
//    }

    fun search(action: () -> Unit) {
        binding.searchBtn.setOnClickListener {
            action()
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

    fun textChangesListener() {
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                showIcons()
            }
        })
    }

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

    private fun showIcons() {
        binding.apply {
            cleanSearch.show()
            searchBtn.show()
        }
    }

    private fun hideIcons() {
        binding.apply {
            cleanSearch.hide()
            searchBtn.hide()
        }
    }

    fun getSearchText(): String = binding.searchEt.text.toString()

    fun setSearchMode() { searchModeToChangeIcons = true }
    fun getSearchMode() = searchModeToChangeIcons
}
