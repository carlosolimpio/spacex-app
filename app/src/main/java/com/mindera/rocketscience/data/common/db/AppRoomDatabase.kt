package com.mindera.rocketscience.data.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindera.rocketscience.data.companyinfo.local.CompanyDao
import com.mindera.rocketscience.data.companyinfo.local.entities.CompanyEntity
import com.mindera.rocketscience.data.launcheslist.local.LaunchesDao
import com.mindera.rocketscience.data.launcheslist.local.entities.LaunchEntity

@Database(entities = [CompanyEntity::class, LaunchEntity::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getCompanyDao(): CompanyDao
    abstract fun getLaunchesDao(): LaunchesDao
}
