package com.ticket.bot.framework.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ticket.bot.framework.data.local.AppDatabase
import com.ticket.bot.framework.network.TicketApiClient
import com.ticket.bot.framework.util.PerformanceMonitor
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var apiClient: TicketApiClient
    private lateinit var adapter: TicketEventAdapter
    private lateinit var performanceMonitor: PerformanceMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(android.R.layout.activity_main)
        
        initializeComponents()
        observeTicketEvents()
    }

    private fun initializeComponents() {
        database = AppDatabase.getInstance(this)
        apiClient = TicketApiClient()
        adapter = TicketEventAdapter()
        performanceMonitor = PerformanceMonitor()
    }

    private fun observeTicketEvents() {
        lifecycleScope.launch {
            database.ticketEventDao().getRecentEvents(50).collect { events ->
                adapter.submitList(events)
            }
        }
    }
}
