package com.example.justweather.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.justweather.databinding.FragmentFavoritesBinding
import com.example.justweather.presentation.BindingFragment

class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoritesBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
