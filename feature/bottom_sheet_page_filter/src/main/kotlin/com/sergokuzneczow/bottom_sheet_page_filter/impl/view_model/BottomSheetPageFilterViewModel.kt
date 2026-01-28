package com.sergokuzneczow.bottom_sheet_page_filter.impl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.bottom_sheet_page_filter.impl.BottomSheetPageFilterScreenIntent
import com.sergokuzneczow.bottom_sheet_page_filter.impl.BottomSheetPageFilterScreenSideEffect
import com.sergokuzneczow.bottom_sheet_page_filter.impl.BottomSheetPageFilterScreenState
import com.sergokuzneczow.bottom_sheet_page_filter.impl.BottomSheetPageFilterScreenState.Success
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.asPageFilterItemPictureCategories
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.asPageFilterItemPictureOrder
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.asPageFilterItemPicturePurities
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.asPageFilterItemPictureSorting
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.asPageFilterPictureCategories
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.asPageFilterPictureOrder
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.asPageFilterPicturePurities
import com.sergokuzneczow.bottom_sheet_page_filter.impl.model.asPageFilterPictureSorting
import com.sergokuzneczow.domain.getPage.GetPage
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyUseCase
import com.sergokuzneczow.models.Page
import com.sergokuzneczow.utilities.DispatchersApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container

internal class BottomSheetPageFilterViewModel(
    private val pageKey: Long,
    dispatchersApi: DispatchersApi,
    private val getFirstPageKeyUseCase: GetFirstPageKeyUseCase,
    private val getPage: GetPage,
) : ViewModel(), ContainerHost<BottomSheetPageFilterScreenState, BottomSheetPageFilterScreenSideEffect> {

    override val container: Container<BottomSheetPageFilterScreenState, BottomSheetPageFilterScreenSideEffect> =
        (viewModelScope + dispatchersApi.default).container(BottomSheetPageFilterScreenState.Loading)

    init {
        viewModelScope.launch(dispatchersApi.io) {
            val page: Page = getPage.execute(pageKey)
            dispatch(
                BottomSheetPageFilterScreenIntent.SetStartInformation(
                    sorting = page.filter.pictureSorting.asPageFilterItemPictureSorting,
                    order = page.filter.pictureOrder.asPageFilterItemPictureOrder,
                    purities = page.filter.picturePurities.asPageFilterItemPicturePurities,
                    categories = page.filter.pictureCategories.asPageFilterItemPictureCategories,
                )
            )
        }
    }

    @OptIn(OrbitExperimental::class)
    fun dispatch(intent: BottomSheetPageFilterScreenIntent) {
        when (intent) {
            is BottomSheetPageFilterScreenIntent.SetStartInformation -> intent {
                reduce { Success(intent.sorting, intent.order, intent.purities, intent.categories) }
            }

            is BottomSheetPageFilterScreenIntent.ChangeSelectedSorting -> intent {
                runOn<Success> { reduce { state.copy(sorting = intent.sorting) } }
            }

            is BottomSheetPageFilterScreenIntent.ChangeSelectedOrder -> intent {
                runOn<Success> { reduce { state.copy(order = intent.order) } }
            }

            is BottomSheetPageFilterScreenIntent.ChangeSelectedPurities -> intent {
                runOn<Success> { reduce { state.copy(purities = intent.purities) } }
            }

            is BottomSheetPageFilterScreenIntent.ChangeSelectedCategories -> intent {
                runOn<Success> { reduce { state.copy(categories = intent.categories) } }
            }

            is BottomSheetPageFilterScreenIntent.Done -> intent {
                val page: Page = getPage.execute(pageKey)
                runOn<Success> {
                    val newPageFilter = page.filter.copy(
                        pictureSorting = state.sorting.asPageFilterPictureSorting,
                        pictureOrder = state.order.asPageFilterPictureOrder,
                        picturePurities = state.purities.asPageFilterPicturePurities,
                        pictureCategories = state.categories.asPageFilterPictureCategories,
                    )
                    getFirstPageKeyUseCase.execute(page.query, newPageFilter)?.let { pageKey -> postSideEffect(BottomSheetPageFilterScreenSideEffect.OnDone(pageKey)) }
                }
            }
        }
    }

    internal class Factory(
        private val pageKey: Long,
        private val dispatchersApi: DispatchersApi,
        private val getFirstPageKeyUseCase: GetFirstPageKeyUseCase,
        private val getPage: GetPage,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(BottomSheetPageFilterViewModel::class.java))
                BottomSheetPageFilterViewModel(pageKey, dispatchersApi, getFirstPageKeyUseCase, getPage) as T
            else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}