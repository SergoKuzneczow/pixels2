package com.sergokuzneczow.service_save_picture.impl.di

import com.sergokuzneczow.service_save_picture.impl.PictureSavingService
import dagger.Component

@Component(
    dependencies = [ServiceSavePictureFeatureDependencies::class]
)
internal interface PictureSavingServiceFeatureComponent {

    fun inject(destination: PictureSavingService)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: ServiceSavePictureFeatureDependencies): Builder
        fun build(): PictureSavingServiceFeatureComponent
    }

    object Instance {

        private var component: PictureSavingServiceFeatureComponent? = null

        internal fun get(d: ServiceSavePictureFeatureDependencies): PictureSavingServiceFeatureComponent {
            if (component == null) {
                component = DaggerPictureSavingServiceFeatureComponent.builder()
                    .setDependencies(d)
                    .build()
            }
            return component ?: throw IllegalStateException("DaggerServiceSavePictureFeatureComponent must be initialize.")
        }

        internal fun clear() {
            component = null
        }
    }
}