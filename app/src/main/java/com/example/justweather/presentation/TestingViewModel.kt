package com.example.justweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justweather.common.extensions.onError
import com.example.justweather.common.extensions.onException
import com.example.justweather.common.extensions.onSuccess
import com.example.justweather.data.repositories.ICityRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TestingViewModel(
    private val cityRepo: ICityRepo,
) : ViewModel() {

    private val _state = MutableStateFlow(TestingState())
    val state = _state.asStateFlow()

    init {
        _state.value = TestingState()
    }

    fun testingCase() {
        viewModelScope.launch {
            val response = cityRepo.getCityInfo()

            response.onSuccess {
                _state.update {
                    it.copy(
                        eventName = TestingViewModelEvent.Success,
                    )
                }
            }.onException {
                _state.update {
                    it.copy(
                        eventName = TestingViewModelEvent.Fail,
                    )
                }
            }.onError { _, _ ->
                _state.update {
                    it.copy(
                        eventName = TestingViewModelEvent.Loading,
                    )
                }
            }
        }
    }
}

data class TestingState(
    val eventName: TestingViewModelEvent = TestingViewModelEvent.None,
    val message: String = "",
)

sealed class TestingViewModelEvent() {
    object None : TestingViewModelEvent()
    object Loading : TestingViewModelEvent()
    object Success : TestingViewModelEvent()
    object Fail : TestingViewModelEvent()
    object Exception : TestingViewModelEvent()
}
