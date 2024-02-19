package gstv.sicredi.core.di

import gstv.sicredi.presentation.view_model.HomeViewModel
import gstv.sicredi.source.EventsRepository
import gstv.sicredi.source.EventsRepositoryImpl
import gstv.sicredi.source.remote.mapper.EventResponseMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    mapperFactory { EventResponseMapper() }

    single<EventsRepository> {
        EventsRepositoryImpl(getMapperOf())
    }
    viewModel { HomeViewModel(get()) }
}