package gstv.sicredi.source.remote

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("id") val id: String,
    @SerializedName("date") val date: Long,
    @SerializedName("description") val description: String,
    @SerializedName("image") val imageUrl: String?,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("price") val price: Float,
    @SerializedName("title") val title: String?,
//    @SerializedName("people") val people: List<*>  Api returning a empty list to see and use here
)
