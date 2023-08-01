package com.example.justweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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
        loadKoinModules(cityModule)
        setUpViewModel()
    }

    override fun onResume() {
        super.onResume()

        binding.bottomNavigationView.apply {
            background = null
            menu.getItem(3).isEnabled = false
        }

        viewModel.testingCase()
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
        unloadKoinModules(cityModule)
    }
}
