package com.mindera.rocketscience.domain.launcheslist

data class Launch(
    val missionName: String,
    val launchDate: String,
    val launchYear: String,
    val wasLaunchSuccessful: Boolean,
    val rocketName: String,
    val rocketType: String,
    val missionPatchUrl: String,
    val missionPatchSmallUrl: String,
    val articleLink: String,
    val wikipediaLink: String,
    val videoLink: String
)
