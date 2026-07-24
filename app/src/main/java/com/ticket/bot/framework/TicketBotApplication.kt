package com.ticket.bot.framework

import android.app.Application
import timber.log.Timber

/**
 * Application entry point with DI initialization
 */
class TicketBotApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLogging()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.i("TicketBotApplication initialized")
    }
}
