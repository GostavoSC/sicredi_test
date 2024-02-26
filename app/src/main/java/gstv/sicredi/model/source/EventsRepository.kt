package gstv.sicredi.model.source

import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.model.domain.Event

interface EventsRepository {
    suspend fun getAllEvents(): ResultWrapper<List<Event>>
    suspend fun getEventDetails(id: String): ResultWrapper<Event>
    suspend fun sendCheckIn(): ResultWrapper<Unit>
}