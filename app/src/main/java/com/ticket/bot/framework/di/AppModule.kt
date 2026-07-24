package com.ticket.bot.framework.di

import android.content.Context
import com.ticket.bot.framework.data.local.AppDatabase
import com.ticket.bot.framework.network.TicketApiClient
import com.ticket.bot.framework.domain.TicketRepository
import com.ticket.bot.framework.util.CacheManager
import com.ticket.bot.framework.util.PerformanceMonitor

/**
 * Dependency Injection container for app components
 * TODO: Replace with Dagger for larger projects
 */
object AppModule {

    private lateinit var context: Context
    private lateinit var database: AppDatabase
    private lateinit var apiClient: TicketApiClient
    private lateinit var repository: TicketRepository
    private val cacheManager = CacheManager()
    private val performanceMonitor = PerformanceMonitor()

    fun initialize(appContext: Context) {
        this.context = appContext
        this.database = AppDatabase.getInstance(appContext)
        this.apiClient = TicketApiClient()
        this.repository = TicketRepository(apiClient, database)
    }

    fun getDatabase(): AppDatabase = database
    fun getApiClient(): TicketApiClient = apiClient
    fun getRepository(): TicketRepository = repository
    fun getCacheManager(): CacheManager = cacheManager
    fun getPerformanceMonitor(): PerformanceMonitor = performanceMonitor
}
