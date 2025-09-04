package com.sergokuzneczow.network.impl.retrofit.api

import com.sergokuzneczow.network.impl.retrofit.RetrofitHandler
import com.sergokuzneczow.network.impl.retrofit.models.PictureWrapperRemoteModel
import com.sergokuzneczow.network.impl.retrofit.models.TagWrapperRemoteModel.TagRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path


internal interface PictureApi {

    @GET("v1/w/{id}")
    suspend fun get(@Path("id") id: String): PictureWrapperRemoteModel
}

internal class PictureApiImpl private constructor(
    private val pictureApi: PictureApi,
) {
    internal constructor() : this(RetrofitHandler.providePictureApi())

    internal suspend fun getPicture(pictureKey: String): PictureWrapperRemoteModel.PictureRemoteModel = pictureApi.get(pictureKey).data

    internal suspend fun getPictureTags(pictureKey: String): List<TagRemoteModel> = pictureApi.get(pictureKey).data.tags
}