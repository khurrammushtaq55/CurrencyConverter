package com.test.paypayassignment.di

import android.content.Context
import androidx.room.Room
import com.test.paypayassignment.core.mapper.CurrencyConverterMapper
import com.test.paypayassignment.core.mapper.CurrencyLocalMapper
import com.test.paypayassignment.core.mapper.CurrencyRemoteMapper
import com.test.paypayassignment.core.utils.Constants.BASE_URL
import com.test.paypayassignment.core.utils.Constants.DATABASE_NAME
import com.test.paypayassignment.data.api.ExRateRemoteDataSource
import com.test.paypayassignment.data.api.ExRateRemoteDataSourceImpl
import com.test.paypayassignment.data.api.service.ApiService
import com.test.paypayassignment.data.db.AppDatabase
import com.test.paypayassignment.data.db.ExRateLocalDataSource
import com.test.paypayassignment.data.db.ExRateLocalDataSourceImpl
import com.test.paypayassignment.data.db.dao.ExRateDao
import com.test.paypayassignment.data.repo.ExRateRepoImpl
import com.test.paypayassignment.domain.repo.ExRatesRepo
import com.test.paypayassignment.domain.usecase.ExchangeRatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ClassModules {

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()

    }

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)

    }

    @Provides
    @Singleton
    fun provideCurrencyDao(appDatabase: AppDatabase): ExRateDao {
        return appDatabase.exchangeRateDao()
    }

    @Singleton
    @Provides
    fun provideRatesLocalDataSource(appDatabase: AppDatabase): ExRateLocalDataSource =
        ExRateLocalDataSourceImpl(appDatabase)

    @Singleton
    @Provides
    fun provideRatesRemoteDataSource(api: ApiService): ExRateRemoteDataSource =
        ExRateRemoteDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideRatesUseCase(repository: ExRatesRepo): ExchangeRatesUseCase =
        ExchangeRatesUseCase(repository)

    @Singleton
    @Provides
    fun provideCurrencyLocalMapper(): CurrencyLocalMapper = CurrencyLocalMapper()

    @Singleton
    @Provides
    fun provideCurrencyRemoteMapper(): CurrencyRemoteMapper = CurrencyRemoteMapper()

    @Singleton
    @Provides
    fun provideCurrencyConverterMapper(): CurrencyConverterMapper = CurrencyConverterMapper()

    @Provides
    @Singleton
    fun provideExRateRepo(
        exRateLocalDataSource: ExRateLocalDataSource,
        exRateRemoteDataSource: ExRateRemoteDataSource,
        localRatesMapper: CurrencyLocalMapper,
        remoteRatesMapper: CurrencyRemoteMapper
    ): ExRatesRepo {
        return ExRateRepoImpl(
            exRateLocalDataSource,
            exRateRemoteDataSource,
            localRatesMapper,
            remoteRatesMapper
        )
    }


}