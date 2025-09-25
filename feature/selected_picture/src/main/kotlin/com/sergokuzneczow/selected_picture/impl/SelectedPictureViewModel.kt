package com.sergokuzneczow.selected_picture.impl

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sergokuzneczow.domain.get_first_page_key.GetFirstPageKey
import com.sergokuzneczow.domain.get_picture_with_relations_case.GetPictureWithRelationsCase
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.selected_picture.impl.di.DaggerSelectedPictureFeatureComponent
import com.sergokuzneczow.selected_picture.impl.di.SelectedPictureFeatureComponent
import com.sergokuzneczow.selected_picture.impl.di.dependenciesProvider
import com.sergokuzneczow.utilities.logger.log
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class SelectedPictureViewModel(
    @NonUiContext context: Context,
    private val pictureKey: String,
) : ViewModel() {

    @Inject
    lateinit var getPictureWithRelationsCase: GetPictureWithRelationsCase

    @Inject
    lateinit var getFirstPageKey: GetFirstPageKey

    private val selectedPictureFeatureComponent: SelectedPictureFeatureComponent = DaggerSelectedPictureFeatureComponent.builder()
        .setDep(context.dependenciesProvider.selectedPictureFeatureDependenciesProvider())
        .build()

    private val selectedPictureUiState: MutableStateFlow<SelectedPictureUiState> = MutableStateFlow(SelectedPictureUiState.Loading)

    init {
        selectedPictureFeatureComponent.inject(this)

        getPictureWithRelationsCase.execute(
            pictureKey = pictureKey,
            coroutineScope = viewModelScope,
        ).onEach {
            log(tag = "SelectedPictureViewModel") { "getPictureWithRelationsCase.execute().onEach().it=$it)" }
            it.onSuccess { pictureWithRelations ->
                selectedPictureUiState.emit(
                    SelectedPictureUiState.Success(
                        pictureKey = pictureWithRelations.picture.key,
                        picturePath = pictureWithRelations.picture.path,
                        tags = pictureWithRelations.tags,
                        colors = pictureWithRelations.colors,
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    internal fun getSelectedPictureUiState(): StateFlow<SelectedPictureUiState> = selectedPictureUiState.asStateFlow()

    internal fun getPageKey(pageQuery: PageQuery, pageFilter: PageFilter, completed: (pageKey: Long) -> Unit) {
        viewModelScope.launch {
            val pageKey: Long? = getFirstPageKey.execute(pageQuery = pageQuery, pageFilter = pageFilter)
            pageKey?.let { pageKey -> completed.invoke(pageKey) }
        }
    }

    internal class Factory(
        @NonUiContext private val context: Context,
        private val pictureKey: String,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SelectedPictureViewModel::class.java)) {
                return SelectedPictureViewModel(
                    context = context,
                    pictureKey = pictureKey,
                ) as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}