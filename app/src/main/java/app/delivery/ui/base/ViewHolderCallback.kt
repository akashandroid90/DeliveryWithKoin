package app.delivery.ui.base

interface ViewHolderCallback<O : Any?> {
    fun bind(value: O)
}