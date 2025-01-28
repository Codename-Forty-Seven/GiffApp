package com.thegiff.giffapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thegiff.giffapp.utils.Constants.DELETED_NAME_TABLE

@Entity(tableName = DELETED_NAME_TABLE)
data class DeletedGifIdEntityData(
    @PrimaryKey val gifId: String
)
