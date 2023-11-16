package com.mindera.rocketscience.data.launcheslist.remote

import com.mindera.rocketscience.data.launcheslist.remote.dto.LaunchDto
import retrofit2.Response
import retrofit2.http.GET

interface LaunchesService {
    @GET("launches")
    suspend fun getAllLaunches(): Response<List<LaunchDto>>
}
