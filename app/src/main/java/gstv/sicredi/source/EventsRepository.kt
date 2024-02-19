package gstv.sicredi.source

import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.domain.Event

interface EventsRepository {
    suspend fun getAllEvents(): ResultWrapper<List<Event>>
}