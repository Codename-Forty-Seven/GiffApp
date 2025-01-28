package com.thegiff.giffapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.google.gson.Gson
import com.thegiff.giffapp.domain.useCase.DeleteGifUseCase
import com.thegiff.giffapp.domain.useCase.GetSavedGifsUseCase
import com.thegiff.giffapp.domain.useCase.IsGifDeletedUseCase
import com.thegiff.giffapp.domain.useCase.SearchGifsUseCase
import com.thegiff.giffapp.presentation.model.GifEntityUi
import com.thegiff.giffapp.utils.Constants.gif_view_model_tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifViewModel @Inject constructor(
    private val searchGifsUseCase: SearchGifsUseCase,
    private val getSavedGifsUseCase: GetSavedGifsUseCase,
    private val deleteGifUseCase: DeleteGifUseCase,
    private val isGifDeletedUseCase: IsGifDeletedUseCase
) : ViewModel() {

    private val _selectedGif = MutableLiveData<GifEntityUi?>()
    val selectedGif: LiveData<GifEntityUi?> get() = _selectedGif

    private val _cachedGifs = MutableLiveData<List<GifEntityUi>>()
    val cachedGifs: LiveData<List<GifEntityUi>> get() = _cachedGifs

    private val _query = MutableSharedFlow<String>(replay = 0)

    val gifs: Flow<PagingData<GifEntityUi>> = _query
        .flatMapLatest { query ->
            searchGifsUseCase(query).map { pagingData ->
                pagingData.filter { gif ->
                    !isGifDeletedUseCase(gif.id)
                }
            }.cachedIn(viewModelScope)
        }

    fun searchGifs(query: String) {
        Log.d(gif_view_model_tag, "searchGifs(query: $query)")
        viewModelScope.launch {
            _query.emit(query)
        }
    }

    fun getCachedGifs() {
        Log.d(gif_view_model_tag, "getCachedGifs")
        CoroutineScope(Dispatchers.IO).launch {
            val savedGifs = getSavedGifsUseCase()
            _cachedGifs.postValue(savedGifs.filter { gif ->
                !isGifDeletedUseCase(gif.id)
            })
        }
    }

    fun deleteGif(gifId: String) {
        Log.d(gif_view_model_tag, "deleteGif(gifId: $gifId)")
        viewModelScope.launch {
            deleteGifUseCase(gifId)
            getCachedGifs()
        }
    }

    fun selectGif(gif: GifEntityUi) {
        Log.d(gif_view_model_tag, "selectGif(gif: ${Gson().toJson(gif)})")
        _selectedGif.value = gif
    }
}
