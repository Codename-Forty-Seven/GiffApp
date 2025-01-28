package com.thegiff.giffapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thegiff.giffapp.data.model.DeletedGifIdEntityData
import com.thegiff.giffapp.data.model.GifEntityData
import com.thegiff.giffapp.utils.Constants.NAME_TABLE
import com.thegiff.giffapp.utils.Constants.DELETED_NAME_TABLE

@Dao
interface GifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGifs(gifs: List<GifEntityData>)

    @Query("SELECT * FROM $NAME_TABLE WHERE isDeleted = 0")
    fun getGifs(): List<GifEntityData>

    @Query("SELECT * FROM $NAME_TABLE WHERE isDeleted = 0 AND gifId NOT IN (SELECT gifId FROM $DELETED_NAME_TABLE)")
    fun getFilteredGifs(): List<GifEntityData>

    @Query("UPDATE $NAME_TABLE SET isDeleted = 1 WHERE gifId = :gifId")
    suspend fun deleteGif(gifId: String)

    @Query("SELECT * FROM $NAME_TABLE WHERE gifId = :gifId")
    suspend fun getGifById(gifId: String): GifEntityData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDeletedGifId(deletedGifIdEntity: DeletedGifIdEntityData)

    @Query("SELECT gifId FROM $DELETED_NAME_TABLE")
    suspend fun getDeletedIds(): List<String>
}