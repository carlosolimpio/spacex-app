package com.mindera.rocketscience.data.launcheslist.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch")
data class LaunchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val missionName: String,
    val launchDate: String,
    val wasLaunchSuccessful: Boolean,
    val rocketName: String,
    val rocketType: String,
    val missionPatchUrl: String,
    val missionPatchSmallUrl: String,
    val articleLink: String,
    val wikipediaLink: String,
    val videoLink: String,
)
