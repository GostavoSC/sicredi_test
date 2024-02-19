package gstv.sicredi.service

import gstv.sicredi.source.remote.EventResponse
import retrofit2.http.GET

interface EventsService {
    @GET("events")
    suspend fun getAllEvents(): List<EventResponse>

}