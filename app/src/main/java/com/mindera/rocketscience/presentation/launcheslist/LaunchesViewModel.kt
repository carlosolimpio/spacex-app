package com.mindera.rocketscience.presentation.launcheslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.domain.common.DataResponse
import com.mindera.rocketscience.domain.launcheslist.Launch
import com.mindera.rocketscience.domain.launcheslist.LaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val launchesUseCase: LaunchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LaunchesState<List<Launch>>>(LaunchesState.Loading)
    var state: StateFlow<LaunchesState<List<Launch>>> = _state

    private val _yearsState = MutableStateFlow<Set<String>>(emptySet())
    var yearsState: StateFlow<Set<String>> = _yearsState

    private val _notFoundState = MutableSharedFlow<String>(0)
    var notFoundState: SharedFlow<String> = _notFoundState

    private var launchList = mutableListOf<Launch>()

    init {
        fetchLaunches()
    }

    fun fetchLaunches() {
        viewModelScope.launch {
            launchesUseCase().collect {
                when (it) {
                    is DataResponse.Error -> _state.value = LaunchesState.Error(it.message)
                    is DataResponse.Success -> {
                        _state.value = LaunchesState.Success(it.data)
                        launchList = it.data.toMutableList()
                    }
                }
            }
        }
    }

    fun fetchLaunchesYears() {
        viewModelScope.launch {
            _yearsState.value = launchList.map { it.launchYear }.toSet()
        }
    }

    fun applyFilters(
        years: List<String>,
        onlySuccessful: Boolean,
        sortOrder: SortOrder = SortOrder.NOT_CHECKED
    ) {
        viewModelScope.launch {
            var filteredLaunches = launchList.filter { years.contains(it.launchYear) }

            if (onlySuccessful) {
                filteredLaunches = filteredLaunches.ifEmpty {
                    launchList
                }.filter { it.wasLaunchSuccessful }
            }

            filteredLaunches = when (sortOrder) {
                SortOrder.ASC -> {
                    filteredLaunches.ifEmpty { launchList }.sortedBy { it.missionName }
                }
                SortOrder.DESC -> {
                    filteredLaunches.ifEmpty { launchList }.sortedByDescending { it.missionName }
                }
                SortOrder.NOT_CHECKED -> filteredLaunches
            }

            _state.value = LaunchesState.Success(
                filteredLaunches.ifEmpty {
                    _notFoundState.emit("No results found for the applied filter. Try again!")
                    launchList
                }
            )
        }
    }
}

enum class SortOrder {
    ASC,
    DESC,
    NOT_CHECKED
}

sealed class LaunchesState<out T : Any> {
    data class Success<out T : Any>(val data: T) : LaunchesState<T>()
    data class Error(val message: String) : LaunchesState<Nothing>()
    object Loading : LaunchesState<Nothing>()
}
