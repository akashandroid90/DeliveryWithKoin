package app.delivery.repository.network

import app.delivery.model.DeliveriesData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  provides methods to fetch data from network
 */

interface ApiInterface {
    @GET("/deliveries")
    fun getList(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<ArrayList<DeliveriesData>>
}
