package com.ticket.bot.framework.network

import okhttp3.Connection
import okhttp3.OkHttpClient
import timber.log.Timber
import java.net.InetAddress
import java.net.Socket
import java.util.concurrent.ConcurrentHashMap

/**
 * Network optimization utilities for sub-100ms response times
 */
object NetworkOptimizer {

    private val dnsCache = ConcurrentHashMap<String, Long>()
    private const val DNS_CACHE_DURATION_MS = 5 * 60 * 1000  // 5 minutes

    /**
     * Warm up DNS cache before making network requests
     */
    fun warmupDns(hostnames: List<String>) {
        hostnames.forEach { hostname ->
            try {
                val startTime = System.currentTimeMillis()
                InetAddress.getAllByName(hostname)
                val elapsed = System.currentTimeMillis() - startTime
                dnsCache[hostname] = System.currentTimeMillis()
                Timber.d("DNS resolved $hostname in ${elapsed}ms")
            } catch (e: Exception) {
                Timber.e(e, "Failed to resolve DNS for $hostname")
            }
        }
    }

    /**
     * Check if DNS cache is valid
     */
    fun isDnsCacheValid(hostname: String): Boolean {
        val cachedTime = dnsCache[hostname] ?: return false
        return (System.currentTimeMillis() - cachedTime) < DNS_CACHE_DURATION_MS
    }

    /**
     * Clear expired DNS cache entries
     */
    fun clearExpiredCache() {
        val currentTime = System.currentTimeMillis()
        dnsCache.entries.removeIf { (_, cachedTime) ->
            (currentTime - cachedTime) > DNS_CACHE_DURATION_MS
        }
    }
}
