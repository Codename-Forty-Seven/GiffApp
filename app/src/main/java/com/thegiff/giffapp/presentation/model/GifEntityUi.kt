package com.thegiff.giffapp.presentation.model

data class GifEntityUi(
    val id: String,
    val url: String,
    val title: String,
    val isDeleted: Boolean = false
)
