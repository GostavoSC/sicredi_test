package gstv.sicredi.model.source

import gstv.sicredi.model.service.EventsService
import gstv.sicredi.model.source.remote.CheckInRequestBody
import gstv.sicredi.model.source.remote.mapper.EventResponseMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test


class EventsRepositoryImplTest {
    private val service: EventsService = mockk()
    private val mapper: EventResponseMapper = mockk()
    private val repositoryImpl = EventsRepositoryImpl(mapper, service)

    @Test
    fun `should pass just one time on get all events`() {
        coEvery { service.getAllEvents() } returns listOf()

        runBlocking {
            repositoryImpl.getAllEvents()
        }

        coVerify(exactly = 1) { service.getAllEvents() }
    }

    @Test
    fun `should pass just one time on get one event`() {
        coEvery { service.sendCheckIn(any()) } returns mockk()

        runBlocking {
            repositoryImpl.sendCheckIn("1")
        }

        coVerify(exactly = 1) { service.sendCheckIn(CheckInRequestBody(eventId = "1")) }
    }
}