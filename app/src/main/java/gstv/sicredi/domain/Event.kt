package gstv.sicredi.domain

data class Event(
    val id: String,
    val date: String,
    val description: String,
    val imageUrl: String?,
    val longitude: Float,
    val latitude: Float,
    val price: Float,
    val title: String?,
//  val people: List<*>  Api returning a empty list to see and use here
)
