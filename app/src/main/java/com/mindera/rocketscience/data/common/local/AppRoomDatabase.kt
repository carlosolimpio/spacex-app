package com.mindera.rocketscience.data.common.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindera.rocketscience.data.companyinfo.local.CompanyDao
import com.mindera.rocketscience.data.companyinfo.local.entities.CompanyEntity

@Database(entities = [CompanyEntity::class, /*Launch*/], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getCompanyDao(): CompanyDao
//    abstract fun getLaunches
}