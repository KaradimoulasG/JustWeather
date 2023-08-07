package com.example.justweather.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.justweather.R
import com.example.justweather.databinding.FragmentSearchBinding
import com.example.justweather.presentation.BindingFragment

class SearchFragment : BindingFragment<FragmentSearchBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}