package com.sergokuzneczow.splash

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.repository.impl.settings_repository_impl.SettingsRepositoryFakeImpl
import com.sergokuzneczow.splash.impl.SplashScreenUiState
import com.sergokuzneczow.splash.impl.view_model.SplashScreenViewModel
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
class SplashScreenViewModelTest {

    private lateinit var splashScreenViewModel: SplashScreenViewModel

    private lateinit var settingsRepositoryFakeApi: SettingsRepositoryApi

    @Before
    fun beforeTest() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun `start state must be Loading`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl(getSettingsReturn = { null })
        splashScreenViewModel = SplashScreenViewModel(settingsRepositoryApi = settingsRepositoryFakeApi)

        splashScreenViewModel.uiState.test {
            val loadingState: SplashScreenUiState = awaitItem()
            assertThat(loadingState).isInstanceOf(SplashScreenUiState.Loading::class.java)
        }
    }

    @Test
    fun `must return Success with hasSettings=true, when application has settings`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl(getSettingsReturn = { ApplicationSettings.DEFAULT })
        splashScreenViewModel = SplashScreenViewModel(settingsRepositoryApi = settingsRepositoryFakeApi)

        splashScreenViewModel.uiState.test {
            skipItems(1) // skip LoadingState

            val success: SplashScreenUiState = awaitItem()
            assertThat(success).isInstanceOf(SplashScreenUiState.Success::class.java)
            assertThat((success as SplashScreenUiState.Success).hasSettings).isEqualTo(true)
        }
    }

    @Test
    fun `must return Success with hasSettings=false, when application hasn't settings`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl(getSettingsReturn = { null })
        splashScreenViewModel = SplashScreenViewModel(settingsRepositoryApi = settingsRepositoryFakeApi)

        splashScreenViewModel.uiState.test {
            skipItems(1) // skip LoadingState

            val success: SplashScreenUiState = awaitItem()
            assertThat(success).isInstanceOf(SplashScreenUiState.Success::class.java)
            assertThat((success as SplashScreenUiState.Success).hasSettings).isEqualTo(false)
        }
    }
}