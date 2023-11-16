package com.mindera.rocketscience.domain.launcheslist

import javax.inject.Inject

class LaunchesUseCase @Inject constructor(
    private val repository: LaunchesRepository
) {
    suspend operator fun invoke() = repository.getLaunchesList()
}
