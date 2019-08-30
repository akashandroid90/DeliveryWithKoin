package app.delivery.ui.base

import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.rule.ActivityTestRule
import app.delivery.MockApplication
import app.delivery.utils.EspressoTestUtil
import app.delivery.utils.resourceState.DataIdleStateResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

abstract class AbstractBaseActivityTest<T : AppCompatActivity>(name: Class<T>) {
    @get:Rule
    val testRule: ActivityTestRule<T> =
        ActivityTestRule(name, false, false) // do not launch the app

    lateinit var idleStateResource: DataIdleStateResource
    lateinit var handler: Handler

    @Before
    fun init() {
        Intents.init()
        val applicationContext = ApplicationProvider.getApplicationContext<Context>()
        if (applicationContext is MockApplication) {
            idleStateResource = applicationContext.idleStateResource
            handler = applicationContext.handler
        }
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
        handler.postDelayed({
            idleStateResource.setState(true)
        }, time)
    }
}