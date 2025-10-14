package com.sergokuzneczow.selected_picture.impl.di

import com.sergokuzneczow.selected_picture.impl.view_model.SelectedPictureViewModel
import com.sergokuzneczow.selected_picture.impl.view_model.SelectedPictureViewModelFactory
import dagger.Component

@Component(
    dependencies = [SelectedPictureFeatureDependencies::class]
)
internal interface SelectedPictureFeatureComponent {

    fun inject(model: SelectedPictureViewModel)

    fun inject(model: SelectedPictureViewModelFactory)

    @Component.Builder
    interface Builder {
        fun setDependencties(d: SelectedPictureFeatureDependencies): Builder
        fun build(): SelectedPictureFeatureComponent
    }

    object Instance {

        private var selectedPictureFeatureComponent: SelectedPictureFeatureComponent? = null

        internal fun get(d: SelectedPictureFeatureDependencies): SelectedPictureFeatureComponent? {
            if (selectedPictureFeatureComponent == null) {
                selectedPictureFeatureComponent = DaggerSelectedPictureFeatureComponent.builder()
                    .setDependencties(d)
                    .build()
            }
            return selectedPictureFeatureComponent
        }

        internal fun clear() {
            selectedPictureFeatureComponent = null
        }
    }
}