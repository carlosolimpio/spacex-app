package com.mindera.rocketscience.domain.companyinfo

import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    suspend fun getCompany(): Flow<ApiResponse<Company>>
}

sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
}
