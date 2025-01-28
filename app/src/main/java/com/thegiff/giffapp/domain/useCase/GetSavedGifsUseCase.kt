package com.thegiff.giffapp.domain.useCase

import com.thegiff.giffapp.data.model.mapper.toGifEntityUi
import com.thegiff.giffapp.domain.repository.GifRepository
import com.thegiff.giffapp.presentation.model.GifEntityUi
import javax.inject.Inject

class GetSavedGifsUseCase @Inject constructor(private val gifRepository: GifRepository) {
    suspend operator fun invoke():
            List<GifEntityUi> {
        val gifsUi = mutableListOf<GifEntityUi>()
        val savedGifs = gifRepository.getSavedGifs()
        for (gifData in savedGifs) {
            gifsUi.add(gifData.toGifEntityUi())
        }
        return gifsUi
    }
}