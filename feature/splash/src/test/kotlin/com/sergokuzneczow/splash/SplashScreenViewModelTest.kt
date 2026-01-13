package com.sergokuzneczow.splash

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sergokuzneczow.models.ApplicationSettings
import com.sergokuzneczow.repository.api.SettingsRepositoryApi
import com.sergokuzneczow.repository.impl.settings_repository_impl.SettingsRepositoryFakeImpl
import com.sergokuzneczow.splash.impl.SplashScreenAction
import com.sergokuzneczow.splash.impl.SplashScreenState
import com.sergokuzneczow.splash.impl.view_model.SplashScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
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
    fun `start state must be CheckingFirstLaunch`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl(getSettingsReturn = { null })
        splashScreenViewModel = SplashScreenViewModel(settingsRepositoryApi = settingsRepositoryFakeApi)

        splashScreenViewModel.onState().test {
            val loadingState: SplashScreenState = awaitItem()
            assertThat(loadingState).isInstanceOf(SplashScreenState.CheckingFirstLaunch::class.java)
        }
    }

    @Test
    fun `must return action IsFirstLaunch if the user has not previously opened the application`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl(getSettingsReturn = { null })
        splashScreenViewModel = SplashScreenViewModel(settingsRepositoryApi = settingsRepositoryFakeApi)

        splashScreenViewModel.onState().test {
            splashScreenViewModel.onAction().test {
                val action: SplashScreenAction = awaitItem()
                assertThat(action).isInstanceOf(SplashScreenAction.IsFirstLaunch::class.java)
            }

            skipItems(1) // skip start state CheckingFirstLaunch
        }
    }

    @Test
    fun `must return action IsNotFirstLaunch if the user not previously opened the application`(): TestResult = runTest {
        settingsRepositoryFakeApi = SettingsRepositoryFakeImpl(getSettingsReturn = { ApplicationSettings.DEFAULT })
        splashScreenViewModel = SplashScreenViewModel(settingsRepositoryApi = settingsRepositoryFakeApi)

        splashScreenViewModel.onState().test {
            splashScreenViewModel.onAction().test {
                val action: SplashScreenAction = awaitItem()
                assertThat(action).isInstanceOf(SplashScreenAction.IsNotFirstLaunch::class.java)
                assertThat((action as SplashScreenAction.IsNotFirstLaunch).settings).isEqualTo(ApplicationSettings.DEFAULT)
            }

            skipItems(1) // skip start state CheckingFirstLaunch
        }
    }
}