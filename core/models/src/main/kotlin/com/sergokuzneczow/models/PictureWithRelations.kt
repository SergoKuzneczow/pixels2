package com.sergokuzneczow.models

public data class PictureWithRelations(
    val picture: Picture,
    val tags: List<Tag>,
    val colors: List<Color>,
)
