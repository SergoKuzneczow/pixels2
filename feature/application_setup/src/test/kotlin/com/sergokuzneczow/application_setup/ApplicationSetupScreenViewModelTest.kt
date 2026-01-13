package com.sergokuzneczow.application_setup

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenAction
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenIntent
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenState
import com.sergokuzneczow.application_setup.impl.ApplicationSetupScreenState.ShowSelectedTheme
import com.sergokuzneczow.application_setup.impl.view_model.ApplicationSetupScreenViewModel
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.repository.impl.settings_repository_impl.SettingsRepositoryFakeImpl
import com.sergokuzneczow.utilities.DispatchersApi
import kotlinx.coroutines.CoroutineDispatcher
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

    private val dispatchersApi: DispatchersApi = object : DispatchersApi {
        override val io: CoroutineDispatcher
            get() = StandardTestDispatcher()
        override val default: CoroutineDispatcher
            get() = StandardTestDispatcher()
        override val main: CoroutineDispatcher
            get() = StandardTestDispatcher()
    }

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
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl()
        applicationSetupScreenViewModel = ApplicationSetupScreenViewModel(dispatchersApi, settingsRepositoryFakeApi)

        applicationSetupScreenViewModel.onState().test {
            val loadingState: ApplicationSetupScreenState = awaitItem()
            assertThat(loadingState).isInstanceOf(ApplicationSetupScreenState.Loading::class.java)
        }
    }

    @Test
    fun `after the Loading state, the ShowSelectedTheme state should be received with default parameters`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl()
        applicationSetupScreenViewModel = ApplicationSetupScreenViewModel(dispatchersApi, settingsRepositoryFakeApi)

        applicationSetupScreenViewModel.onState().test {
            skipItems(1) // skip Loading state

            val showSelectedTheme: ApplicationSetupScreenState = awaitItem()
            assertThat(showSelectedTheme).isInstanceOf(ShowSelectedTheme::class.java)
            assertThat((showSelectedTheme as ShowSelectedTheme).themeState).isEqualTo(ApplicationSettings.DEFAULT.systemSettings.themeState)
        }
    }

    @Test
    fun `after the Loading state, the ShowSelectedTheme state should be received with new parameters`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl()
        applicationSetupScreenViewModel = ApplicationSetupScreenViewModel(dispatchersApi, settingsRepositoryFakeApi)

        applicationSetupScreenViewModel.onState().test {
            skipItems(2) // skip Loading state and ShowSelectedTheme state
            applicationSetupScreenViewModel.updateIntent(ApplicationSetupScreenIntent.ChangeTheme(ApplicationSettings.SystemSettings.ThemeState.DARK))

            val showSelectedTheme: ApplicationSetupScreenState = awaitItem()
            assertThat(showSelectedTheme).isInstanceOf(ShowSelectedTheme::class.java)
            assertThat((showSelectedTheme as ShowSelectedTheme).themeState).isEqualTo(ApplicationSettings.SystemSettings.ThemeState.DARK)
        }
    }

    @Test
    fun `the Completed action must be received after the Done intent has been sent`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl()
        applicationSetupScreenViewModel = ApplicationSetupScreenViewModel(dispatchersApi, settingsRepositoryFakeApi)

        applicationSetupScreenViewModel.onState().test {
            skipItems(2) // skip Loading state and ShowSelectedTheme state
            applicationSetupScreenViewModel.updateIntent(ApplicationSetupScreenIntent.Done)

            applicationSetupScreenViewModel.onAction().test {
                val action: ApplicationSetupScreenAction = awaitItem()
                assertThat(action).isInstanceOf(ApplicationSetupScreenAction.Completed::class.java)
            }
        }
    }
}