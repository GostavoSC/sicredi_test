package gstv.sicredi.utils

import androidx.test.ext.junit.rules.ActivityScenarioRule
import gstv.sicredi.view.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseTestUI {
    private val mockWebServer = MockWebServer()

    @get : Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        mockWebServer.start()
        CountingIdlingResourceSingleton.increment()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        CountingIdlingResourceSingleton.decrement()
    }
}