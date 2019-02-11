package com.pixelart.jsonplaceholderapi_albums

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.pixelart.jsonplaceholderapi_albums.adapter.AlbumAdapter
import com.pixelart.jsonplaceholderapi_albums.ui.MainActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val FILE = "200_response"

    private val activityTestRule =  ActivityTestRule<MainActivity>(MainActivity::class.java)
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        val context = ApplicationProvider.getApplicationContext<Context>()
        try {
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(ITestUtils.getStringFromFile(context, FILE)))
        }catch (e: IOException) {
            e.printStackTrace()
        }

        //BASE_URL = mockWebServer.url("/").toString()
        activityTestRule.launchActivity(Intent())
    }

    @Test
    fun testDataLoadingAndRecyclerViewInteraction(){
        val countingIdlingResource: CountingIdlingResource = activityTestRule.activity.getIdlingResource()
        IdlingRegistry.getInstance().register(countingIdlingResource)

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .check(ITestUtils.RecyclerViewItemCountAssertion(100))

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(ViewAssertions.matches(ITestUtils.atPosition(5,
                ViewMatchers.hasDescendant(ViewMatchers.withText("aut aut architecto")))))

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AlbumAdapter.ViewHolder>(5, ViewActions.click()))

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(
            RecyclerViewActions.scrollToPosition<AlbumAdapter.ViewHolder>(85))
    }
}