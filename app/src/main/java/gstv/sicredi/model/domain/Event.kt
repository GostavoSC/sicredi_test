package gstv.sicredi.model.domain

data class Event(
    val id: String,
    val date: String,
    val description: String,
    val imageUrl: String?,
    val longitude: Float,
    val latitude: Float,
    val price: String,
    val title: String?,
//  val people: List<*>  Api returning a empty list to see and use here
)
