package gstv.sicredi.model.service

import gstv.sicredi.model.source.remote.CheckInRequestBody
import gstv.sicredi.model.source.remote.EventResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsService {
    @GET("events")
    suspend fun getAllEvents(): List<EventResponse>

    @GET("events/{eventId}")
    suspend fun getEventDetails(@Path("eventId") eventId: String): EventResponse

    @POST("checkin")
    suspend fun sendCheckIn(@Body body: CheckInRequestBody )

}