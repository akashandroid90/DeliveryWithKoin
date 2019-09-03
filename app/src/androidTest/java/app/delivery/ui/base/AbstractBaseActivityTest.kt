package app.delivery.ui.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.rule.ActivityTestRule
import app.delivery.utils.EspressoTestUtil
import app.delivery.utils.resourceState.DataIdleStateResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.concurrent.CountDownLatch

abstract class AbstractBaseActivityTest<T : AppCompatActivity>(name: Class<T>) : KoinTest {
    @get:Rule
    val testRule: ActivityTestRule<T> =
        ActivityTestRule(name, false, false) // do not launch the app

    val idleStateResource: DataIdleStateResource by inject()

    @Before
    fun init() {
        Intents.init()
        IdlingRegistry.getInstance().register(idleStateResource)
    }

    @After
    fun teardown() {
        Intents.release()
        IdlingRegistry.getInstance().unregister(idleStateResource)
    }

    fun launchActivity(): T {
        val launchActivity = testRule.launchActivity(getIntent())
        EspressoTestUtil.disableAnimations(testRule)
        return launchActivity
    }

    open fun getIntent(): Intent = Intent()

    @Test
    fun testScreenLoad() {
        val launchActivity = launchActivity()
        releaseResource()
        Intents.intended(IntentMatchers.hasComponent(launchActivity.javaClass.name))
    }

    fun releaseResource() {
        releaseResourceWithTime(3000)
    }

    fun releaseResourceWithTime(time: Long) {
        Thread.sleep(time)
        idleStateResource.setState(true)
    }
}