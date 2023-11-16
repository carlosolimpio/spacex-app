package com.mindera.rocketscience.data.launcheslist

import com.mindera.rocketscience.data.common.utils.handleResponse
import com.mindera.rocketscience.data.launcheslist.local.LaunchesDao
import com.mindera.rocketscience.data.launcheslist.remote.LaunchesService
import com.mindera.rocketscience.domain.common.DataResponse
import com.mindera.rocketscience.domain.launcheslist.LaunchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val launchesService: LaunchesService,
    private val launchesDao: LaunchesDao
) : LaunchesRepository {

    override suspend fun getLaunchesList() = flow {
        val launches = launchesDao.getAllLaunches()

        if (launches.isEmpty()) {
            launchesService.getAllLaunches()
                .handleResponse(
                    onSuccess = { launchDtoList ->
                        launchesDao.insertLaunches(launchDtoList.map { it.toLaunchEntity() })
                        emit(DataResponse.Success(launchDtoList.map { it.toLaunch() }))
                    },
                    onError = {
                        emit(DataResponse.Error(it))
                    }
                )
        } else {
            emit(DataResponse.Success(launches.map { it.toLaunch() }))
        }
    }.flowOn(Dispatchers.IO)
}
