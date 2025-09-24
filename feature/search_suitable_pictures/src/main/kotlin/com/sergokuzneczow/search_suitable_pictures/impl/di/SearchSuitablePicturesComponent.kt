package com.sergokuzneczow.search_suitable_pictures.impl.di

import com.sergokuzneczow.search_suitable_pictures.impl.SearchSuitablePicturesViewModel
import dagger.Component

@Component(
    dependencies = [SearchSuitablePicturesDependencies::class]
)
internal interface SearchSuitablePicturesComponent {

    fun inject(destination: SearchSuitablePicturesViewModel)

    @Component.Builder
    interface Builder {
        fun setDep(d: SearchSuitablePicturesDependencies): Builder
        fun build(): SearchSuitablePicturesComponent
    }
}