package com.thegiff.giffapp.domain.repository

import androidx.paging.PagingData
import com.thegiff.giffapp.data.model.GifEntityData
import com.thegiff.giffapp.presentation.model.GifEntityUi
import kotlinx.coroutines.flow.Flow

interface GifRepository {
    fun getGifs(query: String): Flow<PagingData<GifEntityUi>>
    suspend fun deleteGif(gifId: String)
    suspend fun getSavedGifs(): List<GifEntityData>
    suspend fun isGifDeleted(gifId: String): Boolean
    fun getFilteredGifs(): List<GifEntityData>
}