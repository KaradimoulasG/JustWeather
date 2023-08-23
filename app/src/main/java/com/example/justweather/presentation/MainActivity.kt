package com.example.justweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.justweather.R
import com.example.justweather.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
    }

    override fun onResume() {
        super.onResume()
        setUpUi()
    }

    private fun setUpUi() {
        NavigationUI.setupWithNavController(binding.bottomNavigationView, findNavController(R.id.nav_host))

        binding.topToastComponent.setContent {

        }

        binding.fab.setOnClickListener {
            viewModel.saveFavouriteCity()
//            Snackbar.make(binding.root, "Saved favourite city", Snackbar.LENGTH_SHORT).show()
        }

        binding.bottomNavigationView.apply {
            background = null
            menu.getItem(3).isEnabled = false

            setOnItemSelectedListener { item ->
                when (item.title) {
                    getString(R.string.bottom_nav_option_home) -> {
                        findNavController(R.id.nav_host).navigate(R.id.homeFragment)
                        NavigationUI.onNavDestinationSelected(item, findNavController(R.id.nav_host))
                        true
                    }
                    getString(R.string.bottom_nav_option_search) -> {
                        findNavController(R.id.nav_host).navigate(R.id.searchFragment)
                        NavigationUI.onNavDestinationSelected(item, findNavController(R.id.nav_host))
                        true
                    }
                    getString(R.string.bottom_nav_option_favourites) -> {
                        findNavController(R.id.nav_host).navigate(R.id.favoritesFragment)
                        NavigationUI.onNavDestinationSelected(item, findNavController(R.id.nav_host))
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setUpViewModel() {
        lifecycleScope.launch {
            viewModel.state.onEach {
                when (it.eventName) {
                    WeatherViewModelEvent.None -> Timber.i("here")
                    WeatherViewModelEvent.Success -> Timber.i("worked")
                    WeatherViewModelEvent.Fail -> Timber.i("failed")
                    WeatherViewModelEvent.Loading -> Timber.i("error")
                    WeatherViewModelEvent.SavedFavouriteCity -> Glide.with(this@MainActivity).load(R.drawable.ic_baseline_favorite_24).into(binding.fab)
                    WeatherViewModelEvent.NotSavedFavouriteCity -> Glide.with(this@MainActivity).load(R.drawable.ic_baseline_favorite_24_white).into(binding.fab)
                    else -> Timber.i("sonar won't let me keep this empty")
                }
            }.launchIn(this)
        }
    }
}
