package com.mindera.rocketscience.data.companyinfo

import com.mindera.rocketscience.data.companyinfo.local.CompanyDao
import com.mindera.rocketscience.data.companyinfo.remote.CompanyService
import com.mindera.rocketscience.domain.companyinfo.ApiResponse
import com.mindera.rocketscience.domain.companyinfo.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyService: CompanyService,
    private val companyDao: CompanyDao,
) : CompanyRepository {

    override suspend fun getCompany() = flow {
        val company = companyDao.getCompany()

        if (company == null) {
            val response = companyService.getCompanyInfo()

            if (response.isSuccessful) {
                response.body()?.let {
                    companyDao.insertCompany(it.toCompanyEntity())
                    emit(ApiResponse.Success(it.toCompany()))
                } ?: emit(ApiResponse.Error("Response not successful. Null response"))
            } else {
                emit(ApiResponse.Error("Response not successful. Code: ${response.code()}"))
            }
        } else {
            emit(ApiResponse.Success(company.toCompany()))
        }
    }.flowOn(Dispatchers.IO)
}
