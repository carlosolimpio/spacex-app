package com.mindera.rocketscience.domain.companyinfo

data class Company(
    val name: String,
    val founder: String,
    val yearFounded: Int,
    val employees: Int,
    val launchSites: Int,
    val valuation: Long
)
