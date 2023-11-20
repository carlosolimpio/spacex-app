package com.mindera.rocketscience.data.companyinfo

import com.mindera.rocketscience.data.common.utils.handleResponse
import com.mindera.rocketscience.data.companyinfo.local.CompanyDao
import com.mindera.rocketscience.data.companyinfo.remote.CompanyService
import com.mindera.rocketscience.domain.common.DataResponse
import com.mindera.rocketscience.domain.companyinfo.CompanyRepository
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CompanyRepositoryImpl @Inject constructor(
    private val companyService: CompanyService,
    private val companyDao: CompanyDao
) : CompanyRepository {

    override suspend fun getCompany() = flow {
        val company = companyDao.getCompany()

        if (company == null) {
            try {
                companyService.getCompanyInfo()
                    .handleResponse(
                        onSuccess = { companyDto ->
                            companyDao.insertCompany(companyDto.toCompanyEntity())
                            emit(DataResponse.Success(companyDto.toCompany()))
                        },
                        onError = {
                            emit(DataResponse.Error(it))
                        }
                    )
            } catch (uh: UnknownHostException) {
                emit(DataResponse.Error("Device not connected to internet"))
            } catch (e: Exception) {
                emit(DataResponse.Error(e.message.toString()))
            }
        } else {
            emit(DataResponse.Success(company.toCompany()))
        }
    }.flowOn(Dispatchers.IO)
}
