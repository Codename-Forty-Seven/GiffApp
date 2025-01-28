package com.thegiff.giffapp.di.modules

import com.thegiff.giffapp.data.local.dao.GifDao
import com.thegiff.giffapp.data.remote.service.ApiService
import com.thegiff.giffapp.data.repository.GifRepositoryImpl
import com.thegiff.giffapp.domain.repository.GifRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSoundManagerRepository(
        apiService: ApiService,
        gifDao: GifDao
    ): GifRepository {
        return GifRepositoryImpl(apiService, gifDao)
    }
}