package com.sergokuzneczow.database.impl

import android.content.Context
import androidx.annotation.NonUiContext
import com.sergokuzneczow.database.api.IPixelsDatabaseDataSource
import com.sergokuzneczow.database.impl.framework.RoomHandler
import com.sergokuzneczow.database.impl.framework.entities.createPictureWithRelation
import com.sergokuzneczow.database.impl.framework.entities.toPicture
import com.sergokuzneczow.database.impl.framework.entities.toPictureLocalModel
import com.sergokuzneczow.database.impl.framework.entities.toPictureLocalModelPictureWithRelations
import com.sergokuzneczow.database.impl.framework.impl.PageLocalSource
import com.sergokuzneczow.database.impl.framework.impl.PictureLocalSource
import com.sergokuzneczow.models.Color
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.models.Picture
import com.sergokuzneczow.models.Tag
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
public class PixelsDatabaseDataSourceImpl private constructor(
    private val roomHandler: RoomHandler,
) : IPixelsDatabaseDataSource {

    @Inject
    public constructor(@NonUiContext context: Context) : this(RoomHandler(context))

    private val pageLocalSource: PageLocalSource by lazy { PageLocalSource(roomHandler.providePageDao()) }

    private val pictureLocalSource: PictureLocalSource by lazy { PictureLocalSource(roomHandler.providePictureDao()) }

    override suspend fun setPageGetKey(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): Long? {
        return pageLocalSource.setPageGetKey(pageNumber, pageQuery, pageFilter)
    }

    override suspend fun getPageLoadTimeOrNull(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): Long? {
        return pageLocalSource.getPageLoadTimeOrNull(pageNumber, pageQuery, pageFilter)
    }

    override fun getPicturesPage(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): Flow<List<Picture>> {
        return pageLocalSource.getPicturesPage(pageNumber, pageQuery, pageFilter)
            .map { list -> list.map { item -> item.toPicture() } }
    }

    override fun getPicturesWithRelationsPage(
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ): Flow<List<Picture>> {
        return pageLocalSource.getPicturesWithRelationsPage(pageNumber, pageQuery, pageFilter)
            .map { list -> list.map { item -> item.toPicture() } }
    }

    override suspend fun deletePages(pageQuery: PageQuery, pageFilter: PageFilter) {
        pageLocalSource.deletePages(pageQuery, pageFilter)
    }

    override suspend fun clearAndInsertPictures(
        pictures: List<Picture>,
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ) {
        return pageLocalSource.clearAndInsertPictures(
            pictures = pictures.map { it.toPictureLocalModel() },
            pageNumber,
            pageQuery,
            pageFilter
        )
    }

    override suspend fun clearAndInsertPicturesWithRelations(
        pictureWithRelations: List<Picture>,
        pageNumber: Int,
        pageQuery: PageQuery,
        pageFilter: PageFilter
    ) {
        return pageLocalSource.clearAndInsertPicturesWithRelations(
            pictureWithRelations = pictureWithRelations.map { it.toPictureLocalModelPictureWithRelations() },
            pageNumber,
            pageQuery,
            pageFilter
        )
    }

    override fun getPictureByKey(pictureKey: String): Flow<List<Picture>> {
        return pictureLocalSource.getPictureByKey(pictureKey)
            .map { list -> list.map { item -> item.toPicture() } }
    }

    override fun getPictureWithRelationByKey(pictureKey: String): Flow<List<Picture>> {
        return pictureLocalSource.getPictureWithRelationByKey(pictureKey)
            .map { list -> list.map { item -> item.toPicture() } }
    }

    override suspend fun setPicture(picture: Picture) {
        pictureLocalSource.setPicture(picture.toPictureLocalModel())
    }

    override suspend fun setPicture(picture: Picture, tags: List<Tag>, colors: List<Color>) {
        pictureLocalSource.setPicture(
            pictureWithRelations = createPictureWithRelation(
                picture = picture,
                tags = tags,
                colors = colors
            )
        )
    }
}