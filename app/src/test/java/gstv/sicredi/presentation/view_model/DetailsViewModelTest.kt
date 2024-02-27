package gstv.sicredi.presentation.view_model

import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.model.source.EventsRepository
import gstv.sicredi.utils.TestExtension
import gstv.sicredi.view_model.DetailsViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test


class DetailsViewModelTest : TestExtension() {
    private val repository: EventsRepository = mockk()
    private val viewModel = DetailsViewModel(repository)

    @Before
    fun before() {
        coEvery { repository.getEventDetails(any()) } returns ResultWrapper.Success(getEvent())
    }

    @Test
    fun `should get success when fetch event details`() {
        val event = getEvent()

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
            it shouldBe ResultWrapper.NetworkError
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
            (it as ResultWrapper.GenericError).error shouldBe message
        }
    }

    @Test
    fun `should get success when send check-in`() {
        coEvery { repository.sendCheckIn(any()) } returns ResultWrapper.Success(Unit)

        viewModel.sendCheckIn()
        viewModel.onCheckInDone.observeForever {
            it shouldBe Unit
        }
    }

    @Test
    fun `should get error when send check-in`() {
        coEvery { repository.sendCheckIn(any()) } returns ResultWrapper.NetworkError

        viewModel.fetchDetails("1")
        viewModel.sendCheckIn()
        viewModel.onError.observeForever {
            it shouldBe ResultWrapper.NetworkError
        }
    }

    @Test
    fun `should get error with a string like a message returning from api when send check-in`() {
        val message = "Estamos em manuntenção"
        coEvery { repository.sendCheckIn(any()) } returns ResultWrapper.GenericError(
            error = message
        )
        viewModel.fetchDetails("1")
        viewModel.sendCheckIn()
        viewModel.onError.observeForever {
            (it as ResultWrapper.GenericError).error shouldBe message
        }
    }
}