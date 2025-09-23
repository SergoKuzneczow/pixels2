package com.sergokuzneczow.dialog_page_filter.impl.di

import com.sergokuzneczow.dialog_page_filter.impl.DialogPageFilterViewModel
import dagger.Component

@Component(
    dependencies = [DialogPageFilterDependencies::class]
)
internal interface DialogPageFilterComponent {
    fun inject(destination: DialogPageFilterViewModel)
    @Component.Builder
    interface Builder {
        fun setDependencies(d: DialogPageFilterDependencies): Builder
        fun build(): DialogPageFilterComponent
    }
}