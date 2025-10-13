package com.sergokuzneczow.selected_picture.impl.di

import com.sergokuzneczow.selected_picture.impl.SelectedPictureViewModel
import dagger.Component

@Component(
    dependencies = [SelectedPictureFeatureDependencies::class]
)
internal interface SelectedPictureFeatureComponent {

    fun inject(model:SelectedPictureViewModel)

    @Component.Builder
    interface Builder {
        fun setDependencties(d: SelectedPictureFeatureDependencies): Builder
        fun build(): SelectedPictureFeatureComponent
    }
}