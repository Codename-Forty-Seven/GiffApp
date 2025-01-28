package com.thegiff.giffapp.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thegiff.giffapp.data.model.mapper.toGifEntityUi
import com.thegiff.giffapp.data.remote.service.ApiService
import com.thegiff.giffapp.presentation.model.GifEntityUi
import com.thegiff.giffapp.utils.Constants.API_KEY
import javax.inject.Inject

class GifPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val query: String,
    private val saveToDatabase: suspend (List<GifEntityUi>) -> Unit
) : PagingSource<Int, GifEntityUi>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifEntityUi> {
        return try {
            val offset = params.key ?: 0
            val response = apiService.searchGifs(API_KEY, query, 25, offset)
            val gifs = response.data.map { it.toGifEntityUi() }

            saveToDatabase(gifs)

            LoadResult.Page(
                data = gifs,
                prevKey = if (offset == 0) null else offset - 25,
                nextKey = if (gifs.isEmpty()) null else offset + 25
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GifEntityUi>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(25)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(25)
        }
    }
}