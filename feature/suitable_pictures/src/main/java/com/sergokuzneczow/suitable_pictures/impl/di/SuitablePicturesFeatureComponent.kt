package com.sergokuzneczow.suitable_pictures.impl.di

import com.sergokuzneczow.suitable_pictures.impl.SuitablePicturesViewModel
import dagger.Component

@Component(
    dependencies = [SuitablePicturesFeatureDependencies::class]
)
internal interface SuitablePicturesFeatureComponent {
    fun inject(destination: SuitablePicturesViewModel)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: SuitablePicturesFeatureDependencies): Builder
        fun build(): SuitablePicturesFeatureComponent
    }
}