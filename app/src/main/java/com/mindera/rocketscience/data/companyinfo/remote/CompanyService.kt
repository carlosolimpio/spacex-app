package com.mindera.rocketscience.data.companyinfo.remote

import com.mindera.rocketscience.data.companyinfo.remote.dto.CompanyDto
import retrofit2.Response
import retrofit2.http.GET

interface CompanyService {
    @GET("info")
    suspend fun getCompanyInfo(): Response<CompanyDto>
}
