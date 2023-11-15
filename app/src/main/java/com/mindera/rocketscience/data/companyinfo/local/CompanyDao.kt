package com.mindera.rocketscience.data.companyinfo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindera.rocketscience.data.companyinfo.local.entities.CompanyEntity

@Dao
abstract class CompanyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCompany(company: CompanyEntity)

    @Query("SELECT * FROM company LIMIT 1")
    abstract suspend fun getCompany(): CompanyEntity?
}
