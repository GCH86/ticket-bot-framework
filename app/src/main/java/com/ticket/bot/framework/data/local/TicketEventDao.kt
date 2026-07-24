package com.ticket.bot.framework.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ticket.bot.framework.data.model.TicketEvent
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for TicketEvent
 */
@Dao
interface TicketEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ticketEvent: TicketEvent)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<TicketEvent>)

    @Query("SELECT * FROM ticket_events WHERE event_id = :eventId LIMIT 1")
    suspend fun getEvent(eventId: String): TicketEvent?

    @Query("SELECT * FROM ticket_events ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentEvents(limit: Int = 50): Flow<List<TicketEvent>>

    @Query("SELECT * FROM ticket_events WHERE status = :status ORDER BY timestamp DESC")
    fun getEventsByStatus(status: String): Flow<List<TicketEvent>>

    @Query("DELETE FROM ticket_events WHERE timestamp < :expiryTime")
    suspend fun deleteExpiredEvents(expiryTime: Long)

    @Query("SELECT COUNT(*) FROM ticket_events")
    suspend fun getCount(): Int
}
