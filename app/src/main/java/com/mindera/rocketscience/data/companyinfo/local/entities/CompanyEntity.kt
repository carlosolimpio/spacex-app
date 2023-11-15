package com.mindera.rocketscience.data.companyinfo.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val founder: String,
    val yearFounded: Int,
    val employees: Int,
    val launchSites: Int,
    val valuation: Long
)
