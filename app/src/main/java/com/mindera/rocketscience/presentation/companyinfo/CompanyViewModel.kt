package com.mindera.rocketscience.presentation.companyinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.domain.common.DataResponse
import com.mindera.rocketscience.domain.common.UiState
import com.mindera.rocketscience.domain.companyinfo.Company
import com.mindera.rocketscience.domain.companyinfo.CompanyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val useCase: CompanyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Company>>(UiState.Loading)
    val state: StateFlow<UiState<Company>> = _state

    init {
        viewModelScope.launch {
            useCase().collect {
                when (it) {
                    is DataResponse.Error -> _state.value = UiState.Error(it.message)
                    is DataResponse.Success -> _state.value = UiState.Success(it.data)
                }
            }
        }
    }
}
