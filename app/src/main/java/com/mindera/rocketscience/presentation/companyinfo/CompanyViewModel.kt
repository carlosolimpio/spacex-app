package com.mindera.rocketscience.presentation.companyinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.domain.companyinfo.ApiResponse
import com.mindera.rocketscience.domain.companyinfo.Company
import com.mindera.rocketscience.domain.companyinfo.CompanyUseCase
import com.mindera.rocketscience.presentation.companyinfo.CompanyState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val useCase: CompanyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CompanyState<Company>>(Loading)
    val state: StateFlow<CompanyState<Company>> = _state

    init {
        viewModelScope.launch {
            useCase().collect {
                when (it) {
                    is ApiResponse.Error -> _state.value = CompanyState.Error(it.message)
                    is ApiResponse.Success -> _state.value = CompanyState.Success(it.data)
                }
            }
        }
    }
}

sealed class CompanyState<out T : Any> {
    data class Success<out T : Any>(val data: T) : CompanyState<T>()
    data class Error(val message: String) : CompanyState<Nothing>()
    object Loading : CompanyState<Nothing>()
}
