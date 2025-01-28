package com.thegiff.giffapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.thegiff.giffapp.data.local.dao.GifDao
import com.thegiff.giffapp.data.model.DeletedGifIdEntityData
import com.thegiff.giffapp.data.model.GifEntityData
import com.thegiff.giffapp.data.model.mapper.toGifEntityData
import com.thegiff.giffapp.data.remote.service.ApiService
import com.thegiff.giffapp.domain.repository.GifRepository
import com.thegiff.giffapp.presentation.model.GifEntityUi
import com.thegiff.giffapp.utils.Constants.gif_repository_tag
import com.thegiff.giffapp.utils.GifPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GifRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gifDao: GifDao
) : GifRepository {
    override fun getGifs(query: String): Flow<PagingData<GifEntityUi>> {
        return Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false),
            pagingSourceFactory = {
                GifPagingSource(apiService, query) { gifs ->
                    saveGifsToDatabase(gifs)
                }
            }
        ).flow
    }

    override suspend fun getSavedGifs(): List<GifEntityData> {
        return gifDao.getGifs()
    }

    override fun getFilteredGifs(): List<GifEntityData> {
        return gifDao.getFilteredGifs()
    }

    override suspend fun deleteGif(gifId: String) {
        gifDao.deleteGif(gifId)
        gifDao.addDeletedGifId(DeletedGifIdEntityData(gifId))
    }

    override suspend fun isGifDeleted(gifId: String): Boolean {
        return gifDao.getDeletedIds().contains(gifId)
    }

    private suspend fun saveGifsToDatabase(gifs: List<GifEntityUi>) {
        val newGifs = gifs.filter { gif ->
            gifDao.getGifById(gif.id) == null
        }

        if (newGifs.isNotEmpty()) {
            Log.d(gif_repository_tag, "Saving new GIFs: ${Gson().toJson(newGifs)}")
            gifDao.insertGifs(newGifs.map { it.toGifEntityData() })
        } else {
            Log.d(gif_repository_tag, "No new GIFs to save.")
        }
    }
}

