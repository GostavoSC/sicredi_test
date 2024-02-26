package gstv.sicredi.model.source

import gstv.sicredi.core.utils.Mapper
import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.core.utils.mapWith
import gstv.sicredi.core.utils.safeApiCall
import gstv.sicredi.model.domain.Event
import gstv.sicredi.model.service.EventsService
import gstv.sicredi.model.source.remote.EventResponse

class EventsRepositoryImpl(
    private val eventsResponseMapper: Mapper<EventResponse, Event>,
    private val service: EventsService
) : EventsRepository {

    override suspend fun getAllEvents(): ResultWrapper<List<Event>> = safeApiCall {
        service.getAllEvents().mapWith(eventsResponseMapper)
    }

    override suspend fun getEventDetails(id: String): ResultWrapper<Event> = safeApiCall {
        service.getEventDetails(id).mapWith(eventsResponseMapper)
    }

    override suspend fun sendCheckIn(): ResultWrapper<Unit> = safeApiCall {
        service.sendCheckIn()
    }

}