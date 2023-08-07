package com.example.justweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.justweather.R
import com.example.justweather.databinding.ActivityMainBinding
import com.example.justweather.di.cityModule
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        loadKoinModules(cityModule)
        setUpViewModel()
    }

    override fun onResume() {
        super.onResume()
        setUpUi()
    }

    private fun setUpUi() {
        binding.bottomNavigationView.apply {
            background = null
            menu.getItem(3).isEnabled = false
        }

        val navController = Navigation.findNavController(this, R.id.nav_host)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    timber.log.Timber.i("PAOK home")
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.searchFragment -> {
                    timber.log.Timber.i("PAOK search")
                    navController.navigate(R.id.searchFragment)
                    true
                }
                else -> false
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
                    else -> Timber.i("sonar won't let me keep this empty")
                }
            }.launchIn(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        unloadKoinModules(cityModule)
    }
}
