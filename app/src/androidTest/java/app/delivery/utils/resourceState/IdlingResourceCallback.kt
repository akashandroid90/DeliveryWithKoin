package app.delivery.utils.resourceState

interface IdlingResourceCallback {
    fun setState(state: Boolean)
}