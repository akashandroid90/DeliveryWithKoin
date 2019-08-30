package app.delivery.ui.list

import androidx.recyclerview.widget.RecyclerView
import app.delivery.databinding.AdapterListItemBinding
import app.delivery.model.DeliveriesData
import app.delivery.ui.base.ViewHolderCallback

class ListViewHolder(val binding: AdapterListItemBinding) : RecyclerView.ViewHolder(binding.root),
    ViewHolderCallback<DeliveriesData?> {

    override fun bind(value: DeliveriesData?) {
        binding.data = value
        binding.executePendingBindings()
    }
}