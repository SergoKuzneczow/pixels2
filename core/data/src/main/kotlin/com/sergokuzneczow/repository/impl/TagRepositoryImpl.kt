package com.sergokuzneczow.repository.impl

import com.sergokuzneczow.repository.api.TagRepositoryApi
import jakarta.inject.Inject

public class TagRepositoryImpl @Inject constructor(
//    private val tagLocalSource: ITagLocalSource,
//    private val tagRemoteSource: ITagRemoteSource,
) : TagRepositoryApi {

//    override fun getPictureTagsByPictureKey(pictureKey: String): Flow<List<Tag>> = tagLocalSource.getPictureTagsByPictureKey(pictureKey)
//        .map { it.toTags() }
//
//    override suspend fun syncPictureTagsByPictureKey(pictureKey: String) {
//        val actualPictureTags: List<TagWrapperRemoteModel.TagRemoteModel> = tagRemoteSource.getPictureTags(pictureKey)
//        tagLocalSource.setPictureTags(pictureKey, actualPictureTags.toTagLocalModels())
//    }
//
//    private fun TagLocalModel.toTag(): Tag = Tag(
//        id = id,
//        name = name,
//        alias = alias,
//        categoryId = categoryId,
//        categoryName = categoryName,
//        purity = purity,
//        createdAt = createdAt,
//    )
//
//    private fun TagWrapperRemoteModel.TagRemoteModel.toTagLocalModel(): TagLocalModel = TagLocalModel(
//        id = id,
//        name = name,
//        alias = alias,
//        categoryId = categoryId,
//        categoryName = categoryName,
//        purity = purity,
//        createdAt = createdAt,
//    )
//
//    private fun List<TagLocalModel>.toTags(): List<Tag> = this.map { it.toTag() }
//
//    private fun List<TagWrapperRemoteModel.TagRemoteModel>.toTagLocalModels(): List<TagLocalModel> = this.map { it.toTagLocalModel() }
}