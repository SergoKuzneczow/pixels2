package com.sergokuzneczow.bottom_sheet_page_filter.impl.di

import android.app.Application
import android.content.Context
import com.sergokuzneczow.bottom_sheet_page_filter.impl.view_model.BottomSheetPageDependenciesViewModel
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.utilities.DispatchersApi
import dagger.Component

@Component(
    dependencies = [BottomSheetPageFilterFeatureDependencies::class]
)
internal interface BottomSheetPageFilterComponent {
    fun inject(model: BottomSheetPageDependenciesViewModel)

    @Component.Builder
    interface Builder {
        fun setDependencies(d: BottomSheetPageFilterFeatureDependencies): Builder
        fun build(): BottomSheetPageFilterComponent
    }
}

public interface BottomSheetPageFilterFeatureDependencies {
    public val dispatchersApi: DispatchersApi
    public val getFirstPageKeyUseCase: GetFirstPageKeyUseCase
    public val getPage: GetPage

    public interface Contract {

        public fun bottomSheetPageFilterDependenciesProvider(): BottomSheetPageFilterFeatureDependencies
    }
}

internal val Context.dependencies: BottomSheetPageFilterFeatureDependencies
    get() = when (this) {
        is BottomSheetPageFilterFeatureDependencies.Contract -> this.bottomSheetPageFilterDependenciesProvider()
        is Application -> throw IllegalArgumentException("Application must implement BottomSheetPageFilterDependencies.Contract.")
        else -> this.applicationContext.dependencies
    }