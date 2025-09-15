package com.sergokuzneczow.suitable_pictures.di

import dagger.Component

@Component(
    dependencies = [SuitablePicturesFeatureDependencies::class]
)
internal interface SuitablePicturesFeatureComponent {

    @Component.Builder
    interface Builder {
        fun setDependencies(d: SuitablePicturesFeatureDependencies): Builder
        fun build(): SuitablePicturesFeatureComponent
    }
}