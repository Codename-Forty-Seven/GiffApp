package com.thegiff.giffapp.domain.useCase

import androidx.paging.PagingData
import com.thegiff.giffapp.domain.repository.GifRepository
import com.thegiff.giffapp.presentation.model.GifEntityUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchGifsUseCase @Inject constructor(private val gifRepository: GifRepository){
    operator fun invoke(query: String): Flow<PagingData<GifEntityUi>> {
        return gifRepository.getGifs(query)
    }
}