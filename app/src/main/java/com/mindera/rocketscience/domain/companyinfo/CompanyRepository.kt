package com.mindera.rocketscience.domain.companyinfo

import com.mindera.rocketscience.domain.common.DataResponse
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    suspend fun getCompany(): Flow<DataResponse<Company>>
}
