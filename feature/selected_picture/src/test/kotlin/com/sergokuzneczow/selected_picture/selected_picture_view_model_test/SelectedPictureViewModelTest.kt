package com.sergokuzneczow.selected_picture.selected_picture_view_model_test

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sergokuzneczow.domain.get_picture_with_relations_2_use_case.GetPictureWithRelations2FakeUseCase
import com.sergokuzneczow.selected_picture.impl.SelectedPictureIntent
import com.sergokuzneczow.selected_picture.impl.SelectedPictureUiState
import com.sergokuzneczow.selected_picture.impl.SelectedPictureUiState.Loading
import com.sergokuzneczow.selected_picture.impl.SelectedPictureUiState.Success
import com.sergokuzneczow.selected_picture.impl.view_model.SelectedPictureViewModel
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

@OptIn(ExperimentalCoroutinesApi::class)
internal class SelectedPictureViewModelTest {

    private lateinit var fakeGetPictureWithRelations2Case: GetPictureWithRelations2FakeUseCase

    private lateinit var selectedPictureViewModel: SelectedPictureViewModel

    @Before
    fun beforeTest() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeGetPictureWithRelations2Case = GetPictureWithRelations2FakeUseCase()
        selectedPictureViewModel = SelectedPictureViewModel(
            pictureKey = "",
            getPictureWithRelations2UseCase = fakeGetPictureWithRelations2Case,
        )
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun `must handle cached data before new data`(): TestResult = runTest {
        selectedPictureViewModel.uiState.test {
            val loadingState: SelectedPictureUiState = awaitItem()
            assertThat(loadingState).isInstanceOf(Loading::class.java)

            val cachedKey = "cached"
            fakeGetPictureWithRelations2Case.emitSuccess(cachedKey)
            val successWithCachedData: SelectedPictureUiState = awaitItem()
            assertThat(successWithCachedData).isInstanceOf(Success::class.java)
            assertThat((successWithCachedData as Success).pictureKey).isEqualTo(cachedKey)

            val newKey = "new"
            fakeGetPictureWithRelations2Case.emitSuccess(newKey)
            val successWithNewData: SelectedPictureUiState = awaitItem()
            assertThat(successWithNewData).isInstanceOf(Success::class.java)
            assertThat((successWithNewData as Success).pictureKey).isEqualTo(newKey)
        }
    }

    @Test
    fun `handling an exception during the Loading state`(): TestResult = runTest {
        selectedPictureViewModel.uiState.test {
            val loadingState: SelectedPictureUiState = awaitItem()
            assertThat(loadingState).isInstanceOf(Loading::class.java) // current state is Success

            val message = "Message"
            fakeGetPictureWithRelations2Case.emitFailure(message)
            val loadingStateWithException: SelectedPictureUiState = awaitItem()
            assertThat(loadingStateWithException).isInstanceOf(Loading::class.java)
            assertThat(loadingStateWithException.exceptionMessage).isEqualTo(message)
        }
    }

    @Test
    fun `handling an exception during the Success state`(): TestResult = runTest {
        selectedPictureViewModel.uiState.test {
            skipItems(1) // skip Loading state

            val cachedKey = "cached"
            fakeGetPictureWithRelations2Case.emitSuccess(cachedKey)
            skipItems(1) // skip Success state

            val message = "Exception message"
            fakeGetPictureWithRelations2Case.emitFailure(message)
            val successStateWithException: SelectedPictureUiState = awaitItem()
            assertThat(successStateWithException).isInstanceOf(Success::class.java)
            assertThat(successStateWithException.exceptionMessage).isEqualTo(message)
        }
    }

    @Test
    fun `exception message must be deleted after success answer`(): TestResult = runTest {
        selectedPictureViewModel.uiState.test {
            skipItems(1) // skip Loading state

            val cachedKey = "cached"
            fakeGetPictureWithRelations2Case.emitSuccess(cachedKey)
            skipItems(1) // skip Success state

            val message = "Exception message"
            fakeGetPictureWithRelations2Case.emitFailure(message)
            skipItems(1) // skip Success state with exception message

            val newKey = "new"
            fakeGetPictureWithRelations2Case.emitSuccess(newKey)
            val successWithNewData: SelectedPictureUiState = awaitItem()
            assertThat(successWithNewData).isInstanceOf(Success::class.java)
            assertThat((successWithNewData as Success).pictureKey).isEqualTo(newKey)
            assertThat((successWithNewData as Success).exceptionMessage).isEqualTo(null)
        }
    }

    @Test
    fun `handle intent CHANGE_VISIBLE_CURTAIN`(): TestResult = runTest {
        selectedPictureViewModel.uiState.test {
            skipItems(1) // skip Loading state

            val cachedKey = "cached"
            fakeGetPictureWithRelations2Case.emitSuccess(cachedKey)
            val successState: SelectedPictureUiState = awaitItem()
            assertThat(successState).isInstanceOf(Success::class.java)
            assertThat((successState as Success).curtainVisible).isFalse()
            assertThat(successState.infoFabVisible).isTrue()

            selectedPictureViewModel.setIntent(SelectedPictureIntent.CHANGE_VISIBLE_CURTAIN)
            val curtainIsVisible: SelectedPictureUiState = awaitItem()
            assertThat(curtainIsVisible).isInstanceOf(Success::class.java)
            assertThat((curtainIsVisible as Success).curtainVisible).isTrue()
            assertThat(curtainIsVisible.infoFabVisible).isFalse()

            selectedPictureViewModel.setIntent(SelectedPictureIntent.CHANGE_VISIBLE_CURTAIN)
            val curtainIsHide: SelectedPictureUiState = awaitItem()
            assertThat(curtainIsHide).isInstanceOf(Success::class.java)
            assertThat((curtainIsHide as Success).curtainVisible).isFalse()
            assertThat(curtainIsHide.infoFabVisible).isTrue()
        }
    }
}