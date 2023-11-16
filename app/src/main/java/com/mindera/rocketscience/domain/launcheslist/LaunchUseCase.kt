package com.mindera.rocketscience.domain.launcheslist

import javax.inject.Inject

class LaunchUseCase @Inject constructor(
    private val repository: LaunchesRepository
) {
    suspend operator fun invoke() = repository.getLaunchesList()
}
