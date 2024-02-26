package gstv.sicredi.presentation.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gstv.sicredi.utils.MainDispatcherRule
import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.model.domain.Event
import gstv.sicredi.model.source.EventsRepository
import gstv.sicredi.view_model.DetailsViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class DetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val repository: EventsRepository = mockk()
    private val viewModel = DetailsViewModel(repository)

    @Test
    fun `should get success when fetch event details`() {
        val event = Event(
            id = "1",
            date = "10/01/2024",
            description = "test 123",
            imageUrl = "",
            longitude = 10.0F,
            latitude = 10.3F,
            price = 10.5F,
            title = "Evento teste"
        )
        coEvery { repository.getEventDetails(any()) } returns ResultWrapper.Success(event)

        viewModel.fetchDetails("1")
        viewModel.eventDetails.observeForever {
            it shouldBe event
        }
    }

    @Test
    fun `should get error when fetch event details`() {
        coEvery { repository.getEventDetails(any()) } returns ResultWrapper.NetworkError

        viewModel.fetchDetails("1")
        viewModel.onError.observeForever {
            it shouldBe null
        }
    }

    @Test
    fun `should get error with a string like a message returning from api when fetch event details`() {
        val message = "Estamos em manuntenção"
        coEvery { repository.getEventDetails(any()) } returns ResultWrapper.GenericError(
            error = message
        )

        viewModel.fetchDetails("1")
        viewModel.onError.observeForever {
            it shouldBe message
        }
    }

    @Test
    fun `should get success when send check-in`() {
        coEvery { repository.sendCheckIn() } returns ResultWrapper.Success(Unit)

        viewModel.sendCheckIn()
        viewModel.onCheckInDone.observeForever {
            it shouldBe Unit
        }
    }

    @Test
    fun `should get error when send check-in`() {
        coEvery { repository.sendCheckIn() } returns ResultWrapper.NetworkError

        viewModel.sendCheckIn()
        viewModel.onError.observeForever {
            it shouldBe null
        }
    }

    @Test
    fun `should get error with a string like a message returning from api when send check-in`() {
        val message = "Estamos em manuntenção"
        coEvery { repository.sendCheckIn() } returns ResultWrapper.GenericError(
            error = message
        )

        viewModel.sendCheckIn()
        viewModel.onError.observeForever {
            it shouldBe message
        }
    }
}