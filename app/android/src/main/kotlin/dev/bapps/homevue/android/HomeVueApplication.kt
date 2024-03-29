package dev.bapps.homevue.android

import android.app.Application
import android.os.Build
import dev.bapps.homevue.ApplicationController
import dev.bapps.homevue.core.logger.DebugLogger
import dev.bapps.homevue.core.logger.Logger

internal class HomeVueApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationController.get().onApplicationStart()
    }
}