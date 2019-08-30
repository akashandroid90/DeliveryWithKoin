package app.delivery.ui.list

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.delivery.BuildConfig
import app.delivery.R
import app.delivery.ui.base.AbstractBaseActivityTest
import app.delivery.utils.EspressoTestUtil
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListActivityTest : AbstractBaseActivityTest<ListActivity>(ListActivity::class.java) {

    @Test
    fun pullToRefresh() {
        val launchActivity = launchActivity()
        Espresso.onView(ViewMatchers.withId(R.id.srl_data))
            .perform(SwipeRefreshLayoutActions.setRefreshing())
        Assert.assertTrue(launchActivity.findViewById<SwipeRefreshLayout>(R.id.srl_data).isRefreshing)
    }

    @Test
    fun testDataLoaded() {
        launchActivity()
        val scrollToPosition =
            RecyclerViewActions.scrollToPosition<ListViewHolder>(BuildConfig.PAGE_PREFETCH_DISTANCE)
        Espresso.onView(ViewMatchers.withId(R.id.rv_list))
            .perform(scrollToPosition).check(ViewAssertions.matches(scrollToPosition.constraints))
    }

    @Test
    fun testListItemClick() {
        val launchActivity = launchActivity()
        releaseResource()
        EspressoTestUtil.disableAnimationOnView(launchActivity.findViewById(R.id.rv_list))
        val action = RecyclerViewActions.actionOnItemAtPosition<ListViewHolder>(
            BuildConfig.PAGE_PREFETCH_DISTANCE,
            ViewActions.click()
        )
        val check = Espresso.onView(ViewMatchers.withId(R.id.rv_list)).perform(action)
        idleStateResource.setState(false)
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        releaseResourceWithTime(1000)
        Espresso.pressBack()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        check.check(ViewAssertions.matches(action.constraints))
    }
}