package com.thegiff.giffapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thegiff.giffapp.utils.Constants.NAME_TABLE

@Entity(tableName = NAME_TABLE)
data class GifEntityData(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val gifId:String,
    val url: String,
    val title: String,
    val isDeleted: Boolean = false
)
