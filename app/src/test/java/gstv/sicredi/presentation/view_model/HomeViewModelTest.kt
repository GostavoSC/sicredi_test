package gstv.sicredi.presentation.view_model

import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.model.source.EventsRepository
import gstv.sicredi.utils.TestExtension
import gstv.sicredi.view_model.HomeViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test

class HomeViewModelTest : TestExtension() {

    private val repository: EventsRepository = mockk()
    private val viewModel = HomeViewModel(repository)
    private val mockedEventList = listOf(
        getEvent(), getEvent(id = "2")
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
            it shouldBe ResultWrapper.NetworkError
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
            (it as ResultWrapper.GenericError).error shouldBe message
        }
    }

}