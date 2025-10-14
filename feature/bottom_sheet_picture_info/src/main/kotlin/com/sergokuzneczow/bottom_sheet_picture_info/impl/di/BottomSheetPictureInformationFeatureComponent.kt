package com.sergokuzneczow.bottom_sheet_picture_info.impl.di

import com.sergokuzneczow.bottom_sheet_picture_info.impl.view_model.BottomSheetPictureInfoViewModelFactory
import dagger.Component

@Component(
    dependencies = [BottomSheetPictureInformationFeatureDependencies::class]
)
internal interface BottomSheetPictureInformationFeatureComponent {

    fun inject(destination: BottomSheetPictureInfoViewModelFactory)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: BottomSheetPictureInformationFeatureDependencies): Builder
        fun build(): BottomSheetPictureInformationFeatureComponent
    }

    object Instance {

        private var component: BottomSheetPictureInformationFeatureComponent? = null

        internal fun get(d: BottomSheetPictureInformationFeatureDependencies): BottomSheetPictureInformationFeatureComponent {
            if (component == null) {
                component = DaggerBottomSheetPictureInformationFeatureComponent.builder()
                    .setDependencies(d)
                    .build()
            }
            return component ?: throw IllegalStateException("DaggerBottomSheetPictureInformationFeatureComponent must be initialize.")
        }

        internal fun clear() {
            component = null
        }
    }
}