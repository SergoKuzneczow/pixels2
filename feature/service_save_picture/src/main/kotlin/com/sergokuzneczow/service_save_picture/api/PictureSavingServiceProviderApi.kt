package com.sergokuzneczow.service_save_picture.api

public interface PictureSavingServiceProviderApi {

    public fun execute(
        picturePath: String,
        applicationNotificationChanelId: String = "",
    )
}