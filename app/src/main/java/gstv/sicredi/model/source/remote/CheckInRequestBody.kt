package gstv.sicredi.model.source.remote

import com.google.gson.annotations.SerializedName

data class CheckInRequestBody(
    @SerializedName("eventId") var eventId: String,
    @SerializedName("name") val name: String = "Gustavo Stelle Cunha",
    @SerializedName("email") val email: String = "gustavostellec@gmail.com"
)
