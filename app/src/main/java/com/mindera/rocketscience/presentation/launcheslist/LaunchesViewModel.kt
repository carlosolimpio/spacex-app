package com.mindera.rocketscience.presentation.launcheslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.domain.common.DataResponse
import com.mindera.rocketscience.domain.launcheslist.Launch
import com.mindera.rocketscience.domain.launcheslist.LaunchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val launchesUseCase: LaunchUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LaunchesState<List<Launch>>>(LaunchesState.Loading)
    var state: StateFlow<LaunchesState<List<Launch>>> = _state

    init {
        viewModelScope.launch {
            launchesUseCase().collect {
                when (it) {
                    is DataResponse.Error -> _state.value = LaunchesState.Error(it.message)
                    is DataResponse.Success -> _state.value = LaunchesState.Success(it.data)
                }
            }
        }
    }
}

sealed class LaunchesState<out T : Any> {
    data class Success<out T : Any>(val data: T) : LaunchesState<T>()
    data class Error(val message: String) : LaunchesState<Nothing>()
    object Loading : LaunchesState<Nothing>()
}
