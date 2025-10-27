package com.sergokuzneczow.bottom_sheet_picture_info.impl

import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery

internal sealed interface PictureInformationIntent {
    data class SavingPicture(val picturePath: String) : PictureInformationIntent
    data class FailedSavePicture(val picturePath: String) : PictureInformationIntent
    data class SuccessSavePicture(val picturePath: String, val uri: String) : PictureInformationIntent
    data class SearchPageKey(val pageQuery: PageQuery, val pageFilter: PageFilter, val completedBlock: (pageKey: Long) -> Unit) : PictureInformationIntent
}