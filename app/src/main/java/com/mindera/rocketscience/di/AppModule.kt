package com.mindera.rocketscience.di

import android.content.Context
import androidx.room.Room
import com.mindera.rocketscience.data.common.local.AppRoomDatabase
import com.mindera.rocketscience.data.companyinfo.CompanyRepositoryImpl
import com.mindera.rocketscience.data.companyinfo.local.CompanyDao
import com.mindera.rocketscience.data.companyinfo.remote.CompanyService
import com.mindera.rocketscience.domain.companyinfo.CompanyRepository
import com.mindera.rocketscience.domain.companyinfo.CompanyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    private const val SPACEX_DATA_API_URL = "https://api.spacexdata.com/v3/"

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SPACEX_DATA_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCompanyService(retrofit: Retrofit): CompanyService {
        return retrofit.create(CompanyService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppRoomDataBase(
        @ApplicationContext context: Context,
    ): AppRoomDatabase {
        return Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            "AppRoomDatabase",
        ).build()
    }

    @Singleton
    @Provides
    fun provideCompanyDao(
        appDatabase: AppRoomDatabase,
    ): CompanyDao {
        return appDatabase.getCompanyDao()
    }

    @Singleton
    @Provides
    fun provideCompanyRepository(
        companyService: CompanyService,
        companyDao: CompanyDao
    ): CompanyRepository {
        return CompanyRepositoryImpl(companyService, companyDao)
    }

    @Singleton
    @Provides
    fun provideCompanyUseCase(
        repository: CompanyRepository
    ): CompanyUseCase {
        return CompanyUseCase(repository)
    }
}
