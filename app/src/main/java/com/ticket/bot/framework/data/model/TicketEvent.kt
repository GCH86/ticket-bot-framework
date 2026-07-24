package com.ticket.bot.framework.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Ticket event entity for Room database
 */
@Entity(tableName = "ticket_events")
@JsonClass(generateAdapter = true)
data class TicketEvent(
    @PrimaryKey
    @Json(name = "event_id")
    @ColumnInfo(name = "event_id")
    val eventId: String,

    @Json(name = "title")
    @ColumnInfo(name = "title")
    val title: String,

    @Json(name = "status")
    @ColumnInfo(name = "status")
    val status: String,

    @Json(name = "available_tickets")
    @ColumnInfo(name = "available_tickets")
    val availableTickets: Int,

    @Json(name = "price")
    @ColumnInfo(name = "price")
    val price: Float = 0f,

    @Json(name = "timestamp")
    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)
