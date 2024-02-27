package gstv.sicredi.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import gstv.sicredi.model.domain.Event
import org.junit.Rule
import org.junit.rules.TestRule

open class TestExtension {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    fun getEvent(id: String = "1") = Event(
        id = id,
        date = "10/01/2024",
        description = "test 123",
        imageUrl = "",
        longitude = 10.0F,
        latitude = 10.3F,
        price = "R$10.50",
        title = "Evento teste")
}