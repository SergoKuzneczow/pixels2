package com.sergokuzneczow.network.impl.retrofit

import com.sergokuzneczow.network.impl.retrofit.api.API_KEY
import com.sergokuzneczow.network.impl.retrofit.api.AUTH_NAME
import com.sergokuzneczow.network.impl.retrofit.api.PictureApi
import com.sergokuzneczow.network.impl.retrofit.api.PicturesPageApi
import com.sergokuzneczow.network.impl.retrofit.api.TagApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Для получения API_KEY необходимо:
 * 1) зарегестрироваться или авторизироваться на [wallhawent](https://wallhaven.cc/login).
 * 2) перейти на страницу пользователя (нажав на логин аккаунта в правом верхнем углу);
 * 3) перейти на страницу настроек аккаунта пользователя (нажав на иконку шестеренки в правом верхнем углу);
 * 4) скопировать содержимое строки API Key и вставить в значение свойства API_KEY*/

// public const val AUTH_NAME: String = "X-API-Key"
// public const val API_KEY: String = "your api key"

internal object RetrofitHandler {

    private const val BASE_URL = "https://wallhaven.cc/api/"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val authRequest = chain.request().newBuilder()
                .addHeader(AUTH_NAME, API_KEY)
                .build()
            chain.proceed(authRequest)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val picturesPageApi: PicturesPageApi by lazy { retrofit.create(PicturesPageApi::class.java) }
    private val pictureApi: PictureApi by lazy { retrofit.create(PictureApi::class.java) }
    private val tagApi: TagApi by lazy { retrofit.create(TagApi::class.java) }

    internal fun providePicturesPageApi(): PicturesPageApi = picturesPageApi

    internal fun providePictureApi(): PictureApi = pictureApi

    internal fun provideTagApi(): TagApi = tagApi
}