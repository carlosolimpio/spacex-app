package com.mindera.rocketscience.data.launcheslist.remote.dto

import com.google.gson.annotations.SerializedName

data class LaunchDto(
    @SerializedName("mission_name") val missionName: String,
    @SerializedName("launch_date_utc") val launchDate: String,
    @SerializedName("launch_success") val wasLaunchSuccessful: Boolean,
    val rocket: Rocket,
    val links: Links
)

data class Rocket(
    @SerializedName("rocket_name") val name: String,
    @SerializedName("rocket_type") val type: String
)

data class Links(
    @SerializedName("mission_patch") val missionPatchUrl: String,
    @SerializedName("mission_patch_small") val missionPatchSmallUrl: String,
    @SerializedName("article_link") val articleLink: String,
    @SerializedName("wikipedia") val wikipediaLink: String,
    @SerializedName("video_link") val videoLink: String,
)
