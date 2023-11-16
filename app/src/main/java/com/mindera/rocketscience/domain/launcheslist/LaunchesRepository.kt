package com.mindera.rocketscience.domain.launcheslist

import com.mindera.rocketscience.domain.common.DataResponse
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    suspend fun getLaunchesList(): Flow<DataResponse<List<Launch>>>
}
