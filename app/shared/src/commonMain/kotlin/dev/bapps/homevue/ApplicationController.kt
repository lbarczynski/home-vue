package dev.bapps.homevue

import dev.bapps.homevue.core.Platform
import dev.bapps.homevue.core.logger.DebugLogger
import dev.bapps.homevue.core.logger.Logger

class ApplicationController internal constructor(
    private val platform: Platform = Platform.current
) {

    fun onApplicationStart() {
        if (platform.isDebug) {
            Logger.initialize(DebugLogger())
        }
    }

    companion object {
        private val instance = ApplicationController()
        fun get(): ApplicationController = instance
    }
}
