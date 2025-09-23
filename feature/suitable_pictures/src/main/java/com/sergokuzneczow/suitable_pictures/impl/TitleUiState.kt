package com.sergokuzneczow.suitable_pictures.impl

internal sealed interface TitleUiState {

    val title: String

    class Loading private constructor(override val title: String) : TitleUiState {
        constructor() : this("Loading")
    }

    data class Success(override val title: String) : TitleUiState
}