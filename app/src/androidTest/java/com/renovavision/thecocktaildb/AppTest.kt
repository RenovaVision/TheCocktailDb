package com.renovavision.thecocktaildb

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.android.material.tabs.TabLayout
import com.renovavision.thecocktaildb.activity.MainActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsAnything.anything

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@LargeTest
@RunWith(AndroidJUnit4::class)
class AppTest {

    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun endToEndTest() {
        Thread.sleep(2000)
        onView(ViewMatchers.withText("Categories")).check(matches(anything()))
        onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(1))
    }
}

private fun selectTabAtPosition(tabIndex: Int): ViewAction {
    return object : ViewAction {
        override fun getDescription() = "with tab at index $tabIndex"

        override fun getConstraints() =
            CoreMatchers.allOf(
                ViewMatchers.isDisplayed(),
                ViewMatchers.isAssignableFrom(TabLayout::class.java)
            )

        override fun perform(uiController: UiController, view: View) {
            val tabLayout = view as TabLayout
            val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                ?: throw PerformException.Builder()
                    .withCause(Throwable("No tab at index $tabIndex"))
                    .build()

            tabAtIndex.select()
        }
    }
}