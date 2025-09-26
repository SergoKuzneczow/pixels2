package com.sergokuzneczow.bottom_sheet_picture_info.impl.di

import com.sergokuzneczow.bottom_sheet_picture_info.impl.BottomSheetPictureInfoViewModel
import dagger.Component

@Component(
    dependencies = [BottomSheetPictureInformationFeatureDependencies::class]
)
internal interface BottomSheetPictureInformationFeatureComponent {

    fun inject(destination: BottomSheetPictureInfoViewModel)

    @Component.Builder
    interface Builder {
        fun setDep(d: BottomSheetPictureInformationFeatureDependencies): Builder
        fun build(): BottomSheetPictureInformationFeatureComponent
    }
}