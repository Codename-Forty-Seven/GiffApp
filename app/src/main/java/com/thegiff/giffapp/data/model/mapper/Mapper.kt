package com.thegiff.giffapp.data.model.mapper

import com.thegiff.giffapp.data.model.GifEntityData
import com.thegiff.giffapp.data.model.remote.GifData
import com.thegiff.giffapp.presentation.model.GifEntityUi


fun GifEntityUi.toGifEntityData(): GifEntityData {
    return GifEntityData(
        id = null,
        gifId = this.id,
        url = this.url,
        title = this.title,
        isDeleted = this.isDeleted
    )
}

fun GifEntityData.toGifEntityUi(): GifEntityUi {
    return GifEntityUi(
        id = this.gifId,
        url = this.url,
        title = this.title,
        isDeleted = this.isDeleted
    )
}

fun GifData.toGifEntityUi(): GifEntityUi {
    return GifEntityUi(
        id = this.id,
        url = this.images.original.url,
        title = "",
        isDeleted = false
    )
}