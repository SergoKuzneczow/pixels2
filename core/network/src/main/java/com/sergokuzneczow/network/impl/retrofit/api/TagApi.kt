package com.sergokuzneczow.network.impl.retrofit.api

import com.sergokuzneczow.network.impl.retrofit.models.TagWrapperRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path

internal interface TagApi {

    @GET("v1/tag/{id}")
    suspend fun get(@Path("id") id: Int): TagWrapperRemoteModel
}