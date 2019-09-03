package app.delivery.ui.list

import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import app.delivery.BuildConfig
import app.delivery.db.dao.DeliveriesDao
import app.delivery.model.DataResult
import app.delivery.repository.network.NetworkRepository


class ListViewModel(
    private val deliveriesDao: DeliveriesDao,
    private val boundryCallBack: DeliveryBoundryCallBack,
    private val appRepository: NetworkRepository
) : ViewModel() {
    var mResult: DataResult? = null

    init {
        initialize()
    }

    private fun initialize() {
        mResult = DataResult()
        mResult?.data = deliveriesDao.getAllDelieveries()
            .toLiveData(BuildConfig.PAGE_SIZE, BuildConfig.PAGE_PREFETCH_DISTANCE, boundryCallBack)
        appRepository.result = mResult
    }

    fun checkAndInitialize() {
        if (mResult == null)
            initialize()
    }

    fun resetData() {
        appRepository.getDataFromApi(true, 0)
    }

    fun retry() {
        var value = 0
        if (!mResult?.data?.value.isNullOrEmpty()) {
            val data = mResult?.data?.value?.last()
            if (data != null)
                value = data.id + 1
        }
        appRepository.getDataFromApi(false, value)
    }

    override fun onCleared() {
        mResult = null
        super.onCleared()
    }
}