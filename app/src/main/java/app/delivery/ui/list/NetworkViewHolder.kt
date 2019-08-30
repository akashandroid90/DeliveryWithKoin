package app.delivery.ui.list

import androidx.recyclerview.widget.RecyclerView
import app.delivery.constants.DataState
import app.delivery.databinding.AdapterNetworkBinding
import app.delivery.ui.base.ViewHolderCallback

class NetworkViewHolder(val binding: AdapterNetworkBinding) : RecyclerView.ViewHolder(binding.root),
    ViewHolderCallback<DataState> {

    override fun bind(value: DataState) {
        binding.data = value
        binding.executePendingBindings()
    }
}