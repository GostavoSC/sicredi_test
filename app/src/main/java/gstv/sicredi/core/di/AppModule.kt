package gstv.sicredi.core.di


import gstv.sicredi.core.utils.BASE_URL
import gstv.sicredi.core.utils.SSLPinning
import gstv.sicredi.presentation.view_model.HomeViewModel
import gstv.sicredi.service.EventsService
import gstv.sicredi.source.EventsRepository
import gstv.sicredi.source.EventsRepositoryImpl
import gstv.sicredi.source.remote.mapper.EventResponseMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    mapperFactory { EventResponseMapper() }

    single<EventsRepository> {
        EventsRepositoryImpl(getMapperOf(), get())
    }
    viewModel { HomeViewModel(get()) }
}

val retrofitModule = module {
    factory { provideOkHttpClient() }
    factory { provideForecastApi(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
    val ssl = SSLPinning()
    ssl.builderCallback(client)

    return client.build()
}

fun provideForecastApi(retrofit: Retrofit): EventsService =
    retrofit.create(EventsService::class.java)
