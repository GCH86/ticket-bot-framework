package com.ticket.bot.framework.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * High-performance API client for ticket monitoring
 * Target: < 50ms network latency
 */
class TicketApiClient(
    private val httpClient: OkHttpClient = HttpClientFactory.create()
) {

    suspend fun fetchTicketEvents(
        eventId: String,
        retryCount: Int = 3
    ): TicketEventResponse? = withContext(Dispatchers.IO) {
        repeat(retryCount) { attempt ->
            try {
                val startTime = System.currentTimeMillis()

                val request = Request.Builder()
                    .url("https://api.damai.cn/events/$eventId")
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .addHeader("Accept", "application/json")
                    .build()

                val response = httpClient.newCall(request).execute()
                val elapsed = System.currentTimeMillis() - startTime

                Timber.d("API response in ${elapsed}ms")

                if (response.isSuccessful) {
                    response.body?.string()?.let { body ->
                        return@withContext parseResponse(body)
                    }
                } else if (attempt < retryCount - 1) {
                    val backoff = 100L * (attempt + 1)  // Exponential backoff
                    Timber.w("Request failed, retrying in ${backoff}ms")
                    Thread.sleep(backoff)
                }
            } catch (e: Exception) {
                Timber.e(e, "Network error on attempt ${attempt + 1}")
                if (attempt < retryCount - 1) {
                    Thread.sleep(100L * (attempt + 1))
                }
            }
        }
        null
    }

    private fun parseResponse(json: String): TicketEventResponse? {
        return try {
            // TODO: Implement JSON parsing with Moshi
            TicketEventResponse(
                eventId = "",
                title = "Sample Event",
                status = "available",
                availableTickets = 100,
                timestamp = System.currentTimeMillis()
            )
        } catch (e: Exception) {
            Timber.e(e, "Failed to parse response")
            null
        }
    }
}

data class TicketEventResponse(
    val eventId: String,
    val title: String,
    val status: String,
    val availableTickets: Int,
    val timestamp: Long
)
