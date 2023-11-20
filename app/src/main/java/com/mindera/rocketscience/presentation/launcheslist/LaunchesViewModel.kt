package com.mindera.rocketscience.presentation.launcheslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.domain.common.DataResponse
import com.mindera.rocketscience.domain.common.UiState
import com.mindera.rocketscience.domain.launcheslist.Launch
import com.mindera.rocketscience.domain.launcheslist.LaunchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val repository: LaunchesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Launch>>>(UiState.Loading)
    var state: StateFlow<UiState<List<Launch>>> = _state

    private val _yearsState = MutableStateFlow<Set<String>>(emptySet())
    var yearsState: StateFlow<Set<String>> = _yearsState

    private val _notFoundState = MutableSharedFlow<String>(0)
    var notFoundState: SharedFlow<String> = _notFoundState

    private var launchList = mutableListOf<Launch>()

    fun fetchLaunches() {
        viewModelScope.launch {
            repository.getLaunchesList().collect {
                when (it) {
                    is DataResponse.Error -> _state.value = UiState.Error(it.message)
                    is DataResponse.Success -> {
                        _state.value = UiState.Success(it.data)
                        launchList = it.data.toMutableList()
                    }
                }
            }
        }
    }

    fun fetchYears() {
        viewModelScope.launch {
            _yearsState.value = launchList.map { it.launchYear }.toSet()
        }
    }

    fun applyFilters(
        years: List<String>,
        onlySuccessful: Boolean = false,
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

            _state.value = UiState.Success(
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
