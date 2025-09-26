package com.sergokuzneczow.bottom_sheet_page_filter.impl.di

import com.sergokuzneczow.bottom_sheet_page_filter.impl.BottomSheetPageFilterViewModel
import dagger.Component

@Component(
    dependencies = [BottomSheetPageFilterDependencies::class]
)
internal interface BottomSheetPageFilterComponent {

    fun inject(destination: BottomSheetPageFilterViewModel)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: BottomSheetPageFilterDependencies): Builder
        fun build(): BottomSheetPageFilterComponent
    }
}