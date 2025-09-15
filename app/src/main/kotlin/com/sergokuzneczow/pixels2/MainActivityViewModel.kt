package com.sergokuzneczow.pixels2

import android.content.Context
import androidx.annotation.NonUiContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergokuzneczow.pixels2.di.applicationComponent
import com.sergokuzneczow.repository.api.NetworkMonitorApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class MainActivityViewModel(@NonUiContext context: Context) : ViewModel() {

    private var networkMonitorApi: NetworkMonitorApi? = null

    init {
        context.applicationComponent.inject(this)
    }

    @Inject
    internal fun injectDependencies(_networkMonitorApi: NetworkMonitorApi) {
        networkMonitorApi = _networkMonitorApi
    }

    internal fun getNetworkState(): Flow<Boolean> = networkMonitorApi?.networkStateFlow() ?: throw IllegalStateException("Property networkMonitorApi not implemented.")

    internal class Factory(internal val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                return MainActivityViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}