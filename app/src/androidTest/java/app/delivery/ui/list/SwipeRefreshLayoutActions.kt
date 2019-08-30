package app.delivery.ui.list

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Matcher

object SwipeRefreshLayoutActions {
    fun setRefreshing(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(SwipeRefreshLayout::class.java)
            }

            override fun getDescription(): String {
                return "Set SwipeRefreshLayout refreshing state"
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()

                val swipeRefreshLayout = view as SwipeRefreshLayout
                swipeRefreshLayout.isRefreshing = true
            }
        }
    }
}
