package com.sergokuzneczow.repository.api

import com.sergokuzneczow.models.NetworkState
import kotlinx.coroutines.flow.SharedFlow

public interface NetworkMonitorApi {

    public fun networkStateFlow(): SharedFlow<Boolean>
}