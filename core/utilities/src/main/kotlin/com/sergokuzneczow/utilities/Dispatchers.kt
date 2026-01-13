package com.sergokuzneczow.utilities

import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

public interface DispatchersApi {

    public val io: CoroutineDispatcher

    public val default: CoroutineDispatcher

    public val main: CoroutineDispatcher
}


public class DispatchersProvider @Inject constructor() : DispatchersApi {

    override val io: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.IO

    override val default: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.Default

    override val main: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.Main
}