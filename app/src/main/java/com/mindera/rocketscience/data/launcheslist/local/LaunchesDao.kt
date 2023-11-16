package com.mindera.rocketscience.data.launcheslist.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindera.rocketscience.data.launcheslist.local.entities.LaunchEntity

@Dao
interface LaunchesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(allLaunches: List<LaunchEntity>)

    @Query("SELECT * FROM launch")
    suspend fun getAllLaunches(): List<LaunchEntity>
}
