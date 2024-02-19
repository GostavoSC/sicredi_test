package gstv.sicredi.core

import gstv.sicredi.service.EventsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"

class NetworkUtils {

    companion object {
        private fun getRetrofitInstance(): Retrofit {
            val interceptor = HttpLoggingInterceptor()

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val apiService: EventsService = getRetrofitInstance().create(EventsService::class.java)
    }
}