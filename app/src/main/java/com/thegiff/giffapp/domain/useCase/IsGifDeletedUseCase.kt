package com.thegiff.giffapp.domain.useCase

import com.thegiff.giffapp.domain.repository.GifRepository
import javax.inject.Inject

class IsGifDeletedUseCase @Inject constructor(private val gifRepository: GifRepository) {
    suspend operator fun invoke(gifId: String): Boolean {
        return gifRepository.isGifDeleted(gifId)
    }
}