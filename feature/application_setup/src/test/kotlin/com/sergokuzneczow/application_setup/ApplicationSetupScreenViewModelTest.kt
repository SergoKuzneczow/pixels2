package com.sergokuzneczow.application_setup

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenIntent
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenUiState
import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenViewModel
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.repository.impl.settings_repository_impl.SettingsRepositoryFakeImpl
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
class ApplicationSetupScreenViewModelTest {

    private lateinit var applicationSetupScreenViewModel: ApplicationSetupScreenViewModel

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
    fun `start ui state must be Loading`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl(getSettingsReturn = { null })
        applicationSetupScreenViewModel = ApplicationSetupScreenViewModel(settingsRepositoryFakeApi)

        applicationSetupScreenViewModel.uiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(ApplicationSetupScreenUiState.Loading::class.java)
        }
    }

    @Test
    fun `must return SelectingTheme state after Loading state, when getSettings() return null`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl(getSettingsReturn = { null })
        applicationSetupScreenViewModel = ApplicationSetupScreenViewModel(settingsRepositoryFakeApi)

        applicationSetupScreenViewModel.uiState.test {
            skipItems(1) // skip Loading state

            val selectingTheme: ApplicationSetupScreenUiState = awaitItem()
            assertThat(selectingTheme).isInstanceOf(ApplicationSetupScreenUiState.SelectingTheme::class.java)
        }
    }

    @Test
    fun `must return SelectingTheme state after Loading state, when getSettings() return not null`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl(getSettingsReturn = { ApplicationSettings.DEFAULT })
        applicationSetupScreenViewModel = ApplicationSetupScreenViewModel(settingsRepositoryFakeApi)

        applicationSetupScreenViewModel.uiState.test {
            skipItems(1) // skip Loading state

            val selectingTheme: ApplicationSetupScreenUiState = awaitItem()
            assertThat(selectingTheme).isInstanceOf(ApplicationSetupScreenUiState.SelectingTheme::class.java)
        }
    }
}