package app.delivery.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import app.delivery.constants.DataState

class DataResult {
    var dataState: MutableLiveData<DataState> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    lateinit var data: LiveData<PagedList<DeliveriesData>>
}