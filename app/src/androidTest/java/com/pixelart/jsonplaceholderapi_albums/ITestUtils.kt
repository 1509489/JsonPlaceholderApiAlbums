package com.pixelart.jsonplaceholderapi_albums

import android.content.Context
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Assert
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


object ITestUtils {

    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)

        val observer = object : Observer<T> {
            override fun onChanged(@Nullable o: T) {
                data[0] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }

    @Throws(IOException::class)
    internal fun getStringFromFile(context: Context, filepath: String): String {
        val inputStream = context.resources.assets
            .open(filepath)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()

        val line: String = bufferedReader.readLine()

        while (line  != null) {
            stringBuilder.append(line)
        }

        bufferedReader.close()
        inputStream.close()

        return stringBuilder.toString()
    }

    fun atPosition(position: Int, matcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("Has Item at Position: $position ")
                matcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView
                    .findViewHolderForAdapterPosition(position)
                return viewHolder != null && matcher.matches(viewHolder.itemView)
            }
        }
    }

    class RecyclerViewItemCountAssertion(private val itemCount: Int) : ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val adapter = (view as RecyclerView)
                .adapter

            Assert.assertThat(
                adapter!!.itemCount,
                org.hamcrest.Matchers.`is`(itemCount)
            )
        }
    }
}