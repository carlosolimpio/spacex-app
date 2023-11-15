package com.mindera.rocketscience.data.companyinfo.remote.dto

import com.google.gson.annotations.SerializedName

data class CompanyDto(
    val name: String,
    val founder: String,
    @SerializedName("founded") val yearFounded: Int,
    val employees: Int,
    @SerializedName("launch_sites") val launchSites: Int,
    val valuation: Long
)
