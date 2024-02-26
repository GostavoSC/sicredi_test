package gstv.sicredi.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import gstv.sicredi.R
import gstv.sicredi.utils.BaseTestUI
import gstv.sicredi.utils.waitFor
import gstv.sicredi.view.adapters.EventAdapter
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest : BaseTestUI() {

    @Test
    fun checkingIfHasAToolbarAndRecyclerView() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(isRoot()).perform(waitFor(1000))
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<EventAdapter.ViewHolder>(
                2,
                click()
            )
        )
    }

    @Test
    fun checkingIfHasDetailsFields() {
        checkingIfHasAToolbarAndRecyclerView()
        onView(withId(R.id.event_title))
            .check(matches(isDisplayed()))

        onView(withId(R.id.event_description))
            .check(matches(isDisplayed()))

        onView(withId(R.id.event_localization))
            .check(matches(isDisplayed()))

        onView(withId(R.id.checkin_button))
            .check(matches(isDisplayed()))

        onView(withId(R.id.ic_share))
            .check(matches(isDisplayed()))
    }
}