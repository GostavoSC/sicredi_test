package gstv.sicredi.source

import gstv.sicredi.core.utils.Mapper
import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.core.utils.mapWith
import gstv.sicredi.core.utils.safeApiCall
import gstv.sicredi.domain.Event
import gstv.sicredi.service.EventsService
import gstv.sicredi.source.remote.EventResponse

class EventsRepositoryImpl(
    private val eventsResponseMapper: Mapper<EventResponse, Event>,
    private val service: EventsService
) : EventsRepository {

    override suspend fun getAllEvents(): ResultWrapper<List<Event>> = safeApiCall {
        service.getAllEvents().mapWith(eventsResponseMapper)
    }

}