package com.example.justweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.core_domain.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber
import com.example.core_domain.R as R2

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        loadKoinModules(CityModule)
        setContentView(binding.root)
        setUpViewModel()
    }

    override fun onResume() {
        super.onResume()
        setUpUi()
    }

    private fun setUpUi() {
        NavigationUI.setupWithNavController(binding.bottomNavigationView, findNavController(R2.id.nav_host))

        binding.fab.setOnClickListener {
            viewModel.saveFavouriteCity()
        }

        binding.bottomNavigationView.apply {
            background = null
            menu.getItem(3).isEnabled = false

            setOnItemSelectedListener { item ->
                when (item.title) {
                    getString(R2.string.bottom_nav_option_home) -> {
                        findNavController(R2.id.nav_host).navigate(R2.id.homeFragment)
                        NavigationUI.onNavDestinationSelected(item, findNavController(R2.id.nav_host))
                        true
                    }
                    getString(R2.string.bottom_nav_option_search) -> {
                        findNavController(R2.id.nav_host).navigate(R2.id.searchFragment)
                        NavigationUI.onNavDestinationSelected(item, findNavController(R2.id.nav_host))
                        true
                    }
                    getString(R2.string.bottom_nav_option_favourites) -> {
                        findNavController(R2.id.nav_host).navigate(R2.id.favoritesFragment)
                        NavigationUI.onNavDestinationSelected(item, findNavController(R2.id.nav_host))
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
                    WeatherViewModelEvent.SavedFavouriteCity -> Glide.with(this@MainActivity).load(R2.drawable.ic_baseline_favorite_24).into(binding.fab)
                    WeatherViewModelEvent.NotSavedFavouriteCity -> Glide.with(this@MainActivity).load(R2.drawable.ic_baseline_favorite_24_white).into(binding.fab)
                    else -> Timber.i("sonar won't let me keep this empty")
                }
            }.launchIn(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(CityModule)
    }
}
