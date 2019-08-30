package app.delivery.repository.network

import android.content.Context
import app.delivery.BuildConfig
import app.delivery.R
import app.delivery.constants.DataState
import app.delivery.model.DataResult
import app.delivery.model.DeliveriesData
import app.delivery.repository.database.DbRepository
import app.delivery.utils.NetworkConnectionUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *  perform network operations
 */
open class NetworkRepository (
    val dbRepo: DbRepository,
    private val context: Context,
    private val connection: ApiInterface,
    private val networkConnectionUtil: NetworkConnectionUtil
) {
    private var isRequestInProgress = false
    var result: DataResult? = null

    /**
     *  Fetch delivery data from network and pass to {@link DbRepository} to perform database operations
     */
    open fun getDataFromApi(isReset:Boolean,offset: Int) {
        if (isRequestInProgress) return
        if (networkConnectionUtil.isInternetAvailable(context)) {
            isRequestInProgress = true
            val list = connection.getList(offset, BuildConfig.NETWORK_PAGE_SIZE)
            result?.dataState?.value = DataState.LOADING
            list.enqueue(object : Callback<ArrayList<DeliveriesData>> {
                override fun onResponse(
                    call: Call<ArrayList<DeliveriesData>>,
                    response: Response<ArrayList<DeliveriesData>>
                ) {
                    result?.dataState?.value = DataState.LOADED
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body is ArrayList<DeliveriesData> && body.size > 0)
                            dbRepo.insertDeliveryData(isReset,body)
                    } else {
                        result?.dataState?.value = DataState.NETWORKERROR
                        result?.errorMessage?.value = response.errorBody()?.string()
                    }
                    isRequestInProgress = false
                }

                override fun onFailure(call: Call<ArrayList<DeliveriesData>>, t: Throwable) {
                    isRequestInProgress = false
                    result?.dataState?.value = DataState.NETWORKERROR
                    result?.errorMessage?.value = (t.message as String)
                }
            })
        } else {
            result?.dataState?.value = DataState.NETWORKERROR
            result?.errorMessage?.value = (context.getString(R.string.check_connection))
        }
    }
}