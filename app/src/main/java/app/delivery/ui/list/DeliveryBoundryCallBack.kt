package app.delivery.ui.list

import androidx.paging.PagedList
import app.delivery.model.DeliveriesData
import app.delivery.repository.network.NetworkRepository

class DeliveryBoundryCallBack(private val appRepository: NetworkRepository) :
    PagedList.BoundaryCallback<DeliveriesData>() {


    override fun onZeroItemsLoaded() {
        appRepository.getDataFromApi(true, 0)
    }

    override fun onItemAtEndLoaded(itemAtEnd: DeliveriesData) {
        appRepository.getDataFromApi(false, itemAtEnd.id + 1)
    }
}