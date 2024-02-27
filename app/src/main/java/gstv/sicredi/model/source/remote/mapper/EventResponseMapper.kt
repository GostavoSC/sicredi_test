package gstv.sicredi.model.source.remote.mapper

import gstv.sicredi.core.utils.Mapper
import gstv.sicredi.model.domain.Event
import gstv.sicredi.model.source.remote.EventResponse
import java.text.NumberFormat
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
        price = from.price.convertToMoney(),
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

private fun Float.convertToMoney(): String {
    val ptBr = Locale("pt", "BR")
    val price: String = NumberFormat.getCurrencyInstance(ptBr).format(this)
    return price
}