package com.thegiff.giffapp.domain.useCase

import com.thegiff.giffapp.domain.repository.GifRepository
import javax.inject.Inject

class DeleteGifUseCase @Inject constructor(
    private val gifRepository: GifRepository
) {
    suspend operator fun invoke(gifId: String) {
        gifRepository.deleteGif(gifId)
    }
}