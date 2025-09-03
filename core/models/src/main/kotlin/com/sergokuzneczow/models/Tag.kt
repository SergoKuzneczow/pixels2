package com.sergokuzneczow.models

public data class Tag(
    public val id: Int,
    public val name: String,
    private val alias: String,
    private val categoryId: Int,
    private val categoryName: String,
    private val purity: String,
    private val createdAt: String,
)