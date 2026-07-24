package com.ticket.bot.framework.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Optimized OkHttp client factory for < 100ms response time
 */
object HttpClientFactory {

    fun create(enableLogging: Boolean = false): OkHttpClient {
        val builder = OkHttpClient.Builder()
            // Connection pool for connection reuse
            .connectionPool(okhttp3.ConnectionPool(8, 5, TimeUnit.MINUTES))
            // Aggressive timeouts for fast failure
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            // DNS optimization
            .dns { hostname ->
                try {
                    java.net.InetAddress.getAllByName(hostname).asList()
                } catch (e: Exception) {
                    emptyList()
                }
            }
            // Enable HTTP/2 by default for multiplexing
            .protocols(listOf(
                okhttp3.Protocol.HTTP_2,
                okhttp3.Protocol.HTTP_1_1
            ))
            // Request/Response caching
            .cache(null)  // Disable disk cache for speed (use in-memory cache instead)

        if (enableLogging) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }
}
