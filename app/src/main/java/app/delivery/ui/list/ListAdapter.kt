package app.delivery.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.delivery.R
import app.delivery.constants.AppConstant
import app.delivery.constants.DataState
import app.delivery.model.DeliveriesData
import app.delivery.ui.detail.DetailActivity


class ListAdapter (private val inflater: LayoutInflater) :
    PagedListAdapter<DeliveriesData, RecyclerView.ViewHolder>(COMPARATOR), View.OnClickListener {
    private var dataState: DataState? = null
    private val LISTVIEW = 1
    private val NETWORKVIEW = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LISTVIEW)
            return ListViewHolder(
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.adapter_list_item,
                    parent,
                    false
                )
            )
        else return NetworkViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.adapter_network,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            return NETWORKVIEW
        } else LISTVIEW
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListViewHolder) {
            val item = getItem(position)
            holder.bind(item)
            holder.itemView.tag = position
            holder.itemView.setOnClickListener(this)
        } else if (holder is NetworkViewHolder)
            holder.bind(dataState as DataState)
    }

    private fun hasExtraRow() = dataState != null && dataState == DataState.LOADING

    fun setNetworkState(newNetworkState: DataState?) {
        val previousState = dataState
        val hadExtraRow = hasExtraRow()
        dataState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun onClick(v: View?) {
        val tag = v?.tag as Int
        val item = getItem(tag)
        val context = (v.parent as View).context
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(AppConstant.DATA, item)
        context.startActivity(intent)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<DeliveriesData>() {
            override fun areItemsTheSame(
                oldItem: DeliveriesData,
                newItem: DeliveriesData
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: DeliveriesData,
                newItem: DeliveriesData
            ): Boolean {
                val result = newItem.compareTo(oldItem)
                return result == 0
            }
        }
    }
}