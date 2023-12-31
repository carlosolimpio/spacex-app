package com.mindera.rocketscience.di

import android.content.Context
import androidx.room.Room
import com.mindera.rocketscience.data.common.db.AppRoomDatabase
import com.mindera.rocketscience.data.companyinfo.CompanyRepositoryImpl
import com.mindera.rocketscience.data.companyinfo.local.CompanyDao
import com.mindera.rocketscience.data.companyinfo.remote.CompanyService
import com.mindera.rocketscience.data.launcheslist.LaunchesRepositoryImpl
import com.mindera.rocketscience.data.launcheslist.local.LaunchesDao
import com.mindera.rocketscience.data.launcheslist.remote.LaunchesService
import com.mindera.rocketscience.domain.companyinfo.CompanyRepository
import com.mindera.rocketscience.domain.launcheslist.LaunchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    private const val SPACEX_DATA_API_URL = "https://api.spacexdata.com/v3/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SPACEX_DATA_API_URL)
            .client(client)
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
    fun provideLaunchesService(retrofit: Retrofit): LaunchesService {
        return retrofit.create(LaunchesService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppRoomDataBase(
        @ApplicationContext context: Context
    ): AppRoomDatabase {
        return Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            "AppRoomDatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCompanyDao(
        appDatabase: AppRoomDatabase
    ): CompanyDao {
        return appDatabase.getCompanyDao()
    }

    @Singleton
    @Provides
    fun provideLaunchesDao(
        appDatabase: AppRoomDatabase
    ): LaunchesDao {
        return appDatabase.getLaunchesDao()
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
    fun provideLaunchesRepository(
        launchesService: LaunchesService,
        launchesDao: LaunchesDao
    ): LaunchesRepository {
        return LaunchesRepositoryImpl(launchesService, launchesDao)
    }
}
