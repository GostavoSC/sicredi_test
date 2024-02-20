package gstv.sicredi.source.remote.mapper

import gstv.sicredi.core.utils.Mapper
import gstv.sicredi.domain.Event
import gstv.sicredi.source.remote.EventResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventResponseMapper : Mapper<EventResponse, Event> {
    override fun map(from: EventResponse) = Event(
        id = from.id,
        date = from.date.convertToDate(),
        description = from.description,
        imageUrl = from.imageUrl,
        longitude = from.longitude,
        latitude = from.latitude,
        price = from.price,
        title = from.title
    )
}

private fun Long.convertToDate(): String {
    try {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val netDate = Date(this)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}