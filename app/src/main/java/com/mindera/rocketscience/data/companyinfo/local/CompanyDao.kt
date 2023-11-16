package com.mindera.rocketscience.data.companyinfo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindera.rocketscience.data.companyinfo.local.entities.CompanyEntity

@Dao
interface CompanyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: CompanyEntity)

    @Query("SELECT * FROM company LIMIT 1")
    suspend fun getCompany(): CompanyEntity?
}
