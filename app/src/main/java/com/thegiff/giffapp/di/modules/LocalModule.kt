package com.thegiff.giffapp.di.modules

import android.content.Context
import androidx.room.Room
import com.thegiff.giffapp.data.local.dao.GifDao
import com.thegiff.giffapp.data.local.database.AppDatabase
import com.thegiff.giffapp.utils.Constants.NAME_TABLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, NAME_TABLE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideVolumeSettingDao(database: AppDatabase): GifDao {
        return database.gifDao()
    }
}