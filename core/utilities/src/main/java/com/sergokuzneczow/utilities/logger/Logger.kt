package com.sergokuzneczow.utilities.logger

import android.util.Log
import com.sergokuzneczow.utilities.BuildConfig

public val loggingEnabled: Boolean = BuildConfig.DEBUG

public enum class Level(public val const: Int) {
    VERBOSE(Log.VERBOSE),
    DEBUG(Log.DEBUG),
    INFO(Log.INFO),
    WARN(Log.WARN),
    ERROR(Log.ERROR),
}

public fun Any.itTag(): String = this.javaClass.simpleName.toString()

public fun log(tag: String = "", level: Level = Level.DEBUG, messageBuilder: () -> String) {
    if (loggingEnabled) {
        when (level) {
            Level.VERBOSE -> Log.v(tag, "logging: " + messageBuilder())
            Level.DEBUG -> Log.d(tag, "logging: " + messageBuilder())
            Level.INFO -> Log.i(tag, "logging: " + messageBuilder())
            Level.WARN -> Log.w(tag, "logging: " + messageBuilder())
            Level.ERROR -> Log.e(tag, "logging: " + messageBuilder())
        }
    }
}