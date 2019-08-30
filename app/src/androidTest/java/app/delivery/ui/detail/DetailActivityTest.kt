package app.delivery.ui.detail

import TestUtil
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.delivery.model.DeliveriesData
import app.delivery.ui.base.AbstractBaseActivityTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityTest : AbstractBaseActivityTest<DetailActivity>(DetailActivity::class.java) {
    private lateinit var value: DeliveriesData

    override fun getIntent(): Intent {
        val intent = super.getIntent()
        value = TestUtil.getRandomData()
        intent.putExtra("data", value)
        return intent
    }

    @Test
    fun testMapLoaded() {
        launchActivity()
        releaseResource()
        Espresso.onView(ViewMatchers.withContentDescription("Google Map"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testPIPMode() {
        val launchActivity = launchActivity()
        launchActivity.testPIPMode()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        Assert.assertTrue(launchActivity.isInPictureInPictureMode)
    }
}