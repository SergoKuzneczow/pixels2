package com.sergokuzneczow.bottom_sheet_picture_info.bottom_sheet_picture_info_view_model_test

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sergokuzneczow.bottom_sheet_picture_info.impl.LikeThisButtonUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationIntent
import com.sergokuzneczow.bottom_sheet_picture_info.impl.PictureInformationUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.SavePictureButtonUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.TagsListUiState
import com.sergokuzneczow.bottom_sheet_picture_info.impl.view_model.BottomSheetPictureInfoViewModel
import com.sergokuzneczow.domain.get_first_page_key_use_case.GetFirstPageKeyFakeUseCase
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2FakeUseCase
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase.Answer.AnswerState.CACHED
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2UseCase.Answer.AnswerState.UPDATED
import com.sergokuzneczow.models.PageFilter
import com.sergokuzneczow.models.PageQuery
import com.sergokuzneczow.repository.api.ImageLoaderApi
import com.sergokuzneczow.repository.api.StorageRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
internal class BottomSheetPictureInfoViewModelTest {

    private val fakePictureKey: String = "fake_picture_key"

    private lateinit var getPictureWithRelations2FakeUseCase: GetPictureWithRelations2FakeUseCase

    private lateinit var getFirstPageKeyFakeUseCase: GetFirstPageKeyFakeUseCase

    @Mock
    private lateinit var imageLoaderApi: ImageLoaderApi

    @Mock
    private lateinit var storageRepositoryApi: StorageRepositoryApi

    private lateinit var bottomSheetPictureInfoViewModel: BottomSheetPictureInfoViewModel

    @Before
    internal fun beforeTest() {
        Dispatchers.setMain(StandardTestDispatcher())
        getPictureWithRelations2FakeUseCase = GetPictureWithRelations2FakeUseCase()
        getFirstPageKeyFakeUseCase = GetFirstPageKeyFakeUseCase()
        bottomSheetPictureInfoViewModel = BottomSheetPictureInfoViewModel(
            pictureKey = fakePictureKey,
            getPictureWithRelations2UseCase = getPictureWithRelations2FakeUseCase,
            getFirstPageKeyUseCase = getFirstPageKeyFakeUseCase,
            imageLoaderApi = imageLoaderApi,
            storageRepositoryApi = storageRepositoryApi,
        )
    }

    @After
    internal fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    internal fun `return Loading state when created`(): TestResult = runTest {
        bottomSheetPictureInfoViewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(PictureInformationUiState.Loading::class.java)
        }
    }

    @Test
    internal fun `return TagsListUiState is Loading and ColorsListUiState is Loading if received cached tags list and cached colors list are empty`(): TestResult = runTest {
        bottomSheetPictureInfoViewModel.uiState.test {
            skipItems(1) // skip PictureInformationUiState.Loading

            getPictureWithRelations2FakeUseCase.emitSuccess(tags = emptyList(), colors = emptyList(), answerState = CACHED)
            val state: PictureInformationUiState = awaitItem()
            assertThat(state).isInstanceOf(PictureInformationUiState.Success::class.java)
            val success: PictureInformationUiState.Success = state as PictureInformationUiState.Success
            assertThat(success.tagsListUiState).isInstanceOf(TagsListUiState.Loading::class.java)
            assertThat(success.tagsListUiState).isInstanceOf(TagsListUiState.Loading::class.java)
        }
    }

    @Test
    internal fun `return TagsListUiState is Empty and ColorsListUiState is Empty if received new tags list and cached colors list are empty`(): TestResult = runTest {
        bottomSheetPictureInfoViewModel.uiState.test {
            skipItems(1) // skip PictureInformationUiState.Loading

            getPictureWithRelations2FakeUseCase.emitSuccess(tags = emptyList(), colors = emptyList(), answerState = UPDATED)
            val state: PictureInformationUiState = awaitItem()
            assertThat(state).isInstanceOf(PictureInformationUiState.Success::class.java)
            val success: PictureInformationUiState.Success = state as PictureInformationUiState.Success
            assertThat(success.tagsListUiState).isInstanceOf(TagsListUiState.Empty::class.java)
            assertThat(success.tagsListUiState).isInstanceOf(TagsListUiState.Empty::class.java)
        }
    }

    @Test
    internal fun `return SavePictureButtonUiState is Prepared if received data has picture path`(): TestResult = runTest {
        bottomSheetPictureInfoViewModel.uiState.test {
            skipItems(1) // skip PictureInformationUiState.Loading

            getPictureWithRelations2FakeUseCase.emitSuccess(picturePath = "test_path")
            val state: PictureInformationUiState = awaitItem()
            assertThat(state).isInstanceOf(PictureInformationUiState.Success::class.java)
            val success: PictureInformationUiState.Success = state as PictureInformationUiState.Success
            assertThat(success.savePictureButtonUiState).isInstanceOf(SavePictureButtonUiState.Prepared::class.java)
        }
    }

    @Test
    internal fun `return LikeThisButtonUiState is Success if received data has picture key`(): TestResult = runTest {
        bottomSheetPictureInfoViewModel.uiState.test {
            skipItems(1) // skip PictureInformationUiState.Loading

            getPictureWithRelations2FakeUseCase.emitSuccess(pictureKey = "picture_key")
            val state: PictureInformationUiState = awaitItem()
            assertThat(state).isInstanceOf(PictureInformationUiState.Success::class.java)
            val success: PictureInformationUiState.Success = state as PictureInformationUiState.Success
            assertThat(success.likeThisButtonUiState).isInstanceOf(LikeThisButtonUiState.Success::class.java)
            assertThat((success.likeThisButtonUiState as LikeThisButtonUiState.Success).pictureKey).isEqualTo("picture_key")
        }
    }

    @Test
    internal fun `return page key when set intent PictureInformationIntent`(): TestResult = runTest {
        bottomSheetPictureInfoViewModel.uiState.test {
            skipItems(1) // skip PictureInformationUiState.Loading

            getPictureWithRelations2FakeUseCase.emitSuccess(pictureKey = "picture_key", answerState = UPDATED)
            skipItems(1) // skip PictureInformationUiState.Success

            getFirstPageKeyFakeUseCase.setReturnValue(0)
            bottomSheetPictureInfoViewModel.setIntent(
                PictureInformationIntent.SearchPageKey(
                    pageQuery = PageQuery.DEFAULT,
                    pageFilter = PageFilter.DEFAULT,
                    completedBlock = { pageKey -> assertThat(pageKey).isEqualTo(0) }
                )
            )
        }
    }
}