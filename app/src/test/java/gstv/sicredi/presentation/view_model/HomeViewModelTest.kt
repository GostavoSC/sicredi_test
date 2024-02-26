package gstv.sicredi.presentation.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gstv.sicredi.utils.MainDispatcherRule
import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.model.domain.Event
import gstv.sicredi.model.source.EventsRepository
import gstv.sicredi.view_model.HomeViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val repository: EventsRepository = mockk()
    private val viewModel = HomeViewModel(repository)
    private val mockedEventList = listOf(
        Event(
            id = "1",
            date = "10/01/2024",
            description = "test 123",
            imageUrl = "",
            longitude = 10.0F,
            latitude = 10.3F,
            price = 10.5F,
            title = "Evento teste"
        ),
        Event(
            id = "2",
            date = "10/01/2024",
            description = "test 432",
            imageUrl = "",
            longitude = 10.0F,
            latitude = 10.3F,
            price = 10.5F,
            title = "Evento Sicredi"
        )
    )

    @Test
    fun `should get success when get all events`() {
        coEvery { repository.getAllEvents() } returns ResultWrapper.Success(mockedEventList)

        viewModel.getAllEvents()
        viewModel.eventsList.observeForever {
            it shouldBe mockedEventList
        }
    }



    @Test
    fun `should get error when fetch event details`() {
        coEvery { repository.getAllEvents() } returns ResultWrapper.NetworkError

        viewModel.getAllEvents()
        viewModel.onError.observeForever {
            it shouldBe null
        }
    }

    @Test
    fun `should get error with a string like a message returning from api when fetch event details`() {
        val message = "Estamos em manuntenção"
        coEvery { repository.getAllEvents() } returns ResultWrapper.GenericError(
            error = message
        )

        viewModel.getAllEvents()
        viewModel.onError.observeForever {
            it shouldBe message
        }
    }

}