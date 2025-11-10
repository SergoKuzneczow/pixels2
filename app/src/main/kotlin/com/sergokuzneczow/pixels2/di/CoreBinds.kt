package com.sergokuzneczow.pixels2.di

import com.sergokuzneczow.database.api.PixelsDatabaseDataSourceApi
import com.sergokuzneczow.database.impl.PixelsDatabaseDataSourceImpl
import com.sergokuzneczow.datastore_picture.api.StoragePictureApi
import com.sergokuzneczow.datastore_picture.impl.StoragePictureImpl
import com.sergokuzneczow.network.api.PixelsNetworkDataSourceApi
import com.sergokuzneczow.network.impl.PixelsNetworkDataSourceImpl
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.NetworkMonitorApi
import com.sergokuzneczow.repository.api.PageRepositoryApi
import com.sergokuzneczow.repository.api.PictureRepositoryApi
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import com.sergokuzneczow.repository.api.TagRepositoryApi
import com.sergokuzneczow.repository.impl.ImageLoaderImpl
import com.sergokuzneczow.repository.impl.NetworkMonitorImpl
import com.sergokuzneczow.repository.impl.PageRepositoryImpl
import com.sergokuzneczow.repository.impl.settings_repository_impl.SettingsRepositoryImpl
import com.sergokuzneczow.repository.impl.StorageRepositoryImpl
import com.sergokuzneczow.repository.impl.TagRepositoryImpl
import com.sergokuzneczow.repository.impl.picture_repository_impl.PictureRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
internal interface CoreBinds {

    @Binds
    fun toPixelsDatabaseDataSourceApi(i: PixelsDatabaseDataSourceImpl): PixelsDatabaseDataSourceApi

    @Binds
    fun toStoragePictureApi(i: StoragePictureImpl): StoragePictureApi

    @Binds
    fun toPixelsNetworkDataSourceApi(i: PixelsNetworkDataSourceImpl): PixelsNetworkDataSourceApi

    @Binds
    fun toPageRepositoryApi(i: PageRepositoryImpl): PageRepositoryApi

    @Binds
    fun toPictureRepositoryApi(i: PictureRepositoryImpl): PictureRepositoryApi

    @Binds
    fun toStorageRepositoryApi(i: StorageRepositoryImpl): StorageRepositoryApi

    @Binds
    fun toTagRepositoryApi(i: TagRepositoryImpl): TagRepositoryApi

    @Binds
    fun toNetworkMonitorApi(i: NetworkMonitorImpl): NetworkMonitorApi

    @Binds
    fun toImageLoaderApi(i: ImageLoaderImpl): ImageLoaderApi

    @Binds
    fun toSettingsRepositoryApi(i: SettingsRepositoryImpl): SettingsRepositoryApi
}