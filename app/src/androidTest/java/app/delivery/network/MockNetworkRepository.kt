package app.delivery.network

import TestUtil
import android.content.Context
import android.os.Handler
import app.delivery.BuildConfig
import app.delivery.model.DeliveriesData
import app.delivery.model.ThreadModel
import app.delivery.repository.database.DbRepository
import app.delivery.repository.network.ApiInterface
import app.delivery.repository.network.NetworkRepository
import app.delivery.utils.NetworkConnectionUtil
import app.delivery.utils.resourceState.IdlingResourceCallback
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Inject


class MockNetworkRepository @Inject constructor(
    dbRepo: DbRepository,
    context: Context,
    private val connection: ApiInterface,
    private val server: MockWebServer,
    private val thread: ThreadModel,
    networkConnectionUtil: NetworkConnectionUtil,
    val handler: Handler,
    val idleStateResource: IdlingResourceCallback
) : NetworkRepository(dbRepo, context, connection, networkConnectionUtil) {

    override fun getDataFromApi(offset: Int) {
        server.enqueue(MockResponse().setBody(TestUtil.getDataAsString()))
        val list = connection.getList(offset, offset + BuildConfig.NETWORK_PAGE_SIZE)
        idleStateResource.setState(false)
        thread.networkThread.execute {
            val execute = list.execute()
            val body = execute.body()
            dbRepo.insertDeliveryData(body as List<DeliveriesData>)
            handler.postDelayed({
                idleStateResource.setState(true)
            }, 3000)
        }
    }
}