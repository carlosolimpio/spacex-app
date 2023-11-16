package com.mindera.rocketscience.data.launcheslist

import com.mindera.rocketscience.data.launcheslist.local.entities.LaunchEntity
import com.mindera.rocketscience.data.launcheslist.remote.dto.LaunchDto
import com.mindera.rocketscience.domain.launcheslist.Launch
fun LaunchDto.toLaunchEntity() = LaunchEntity(
    missionName = missionName,
    launchDate = launchDate,
    wasLaunchSuccessful = wasLaunchSuccessful,
    rocketName = rocket.name,
    rocketType = rocket.type,
    missionPatchUrl = links.missionPatchUrl ?: "",
    missionPatchSmallUrl = links.missionPatchSmallUrl ?: "",
    articleLink = links.articleLink ?: "",
    wikipediaLink = links.wikipediaLink ?: "",
    videoLink = links.videoLink ?: ""
)

fun LaunchDto.toLaunch() = Launch(
    missionName = missionName,
    launchDate = launchDate,
    wasLaunchSuccessful = wasLaunchSuccessful,
    rocketName = rocket.name,
    rocketType = rocket.type,
    missionPatchUrl = links.missionPatchUrl ?: "",
    missionPatchSmallUrl = links.missionPatchSmallUrl ?: "",
    articleLink = links.articleLink ?: "",
    wikipediaLink = links.wikipediaLink ?: "",
    videoLink = links.videoLink ?: ""
)

fun LaunchEntity.toLaunch() = Launch(
    missionName = missionName,
    launchDate = launchDate,
    wasLaunchSuccessful = wasLaunchSuccessful,
    rocketName = rocketName,
    rocketType = rocketType,
    missionPatchUrl = missionPatchUrl ?: "",
    missionPatchSmallUrl = missionPatchSmallUrl ?: "",
    articleLink = articleLink ?: "",
    wikipediaLink = wikipediaLink ?: "",
    videoLink = videoLink ?: ""
)
