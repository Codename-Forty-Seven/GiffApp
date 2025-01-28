package com.thegiff.giffapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thegiff.giffapp.data.local.dao.GifDao
import com.thegiff.giffapp.data.model.DeletedGifIdEntityData
import com.thegiff.giffapp.data.model.GifEntityData

@Database(
    entities = [
        GifEntityData::class,
        DeletedGifIdEntityData::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao
}