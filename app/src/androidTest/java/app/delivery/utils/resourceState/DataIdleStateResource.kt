package app.delivery.utils.resourceState

import androidx.test.espresso.IdlingResource

class DataIdleStateResource : IdlingResource, IdlingResourceCallback {
    private var idleState = false
    private var callback: IdlingResource.ResourceCallback? = null
    override fun getName(): String {
        return javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return idleState
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    override fun setState(state: Boolean) {
        idleState = state
        if (idleState)
            callback?.onTransitionToIdle()
    }
}