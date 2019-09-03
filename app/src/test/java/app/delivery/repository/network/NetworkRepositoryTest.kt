package app.delivery.repository.network

import TestUtil
import app.delivery.BuildConfig
import app.delivery.db.dao.DeliveriesDao
import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepositoryTest {
    private lateinit var mockServer: MockWebServer
    private lateinit var apiInterface: ApiInterface
    @Mock
    private lateinit var network: NetworkRepository
    @Mock
    private lateinit var deliveries: DeliveriesDao

    @Before
    @Throws(Exception::class)
    fun setup() {
        mockServer = MockWebServer()
        apiInterface = Retrofit.Builder()
            .baseUrl(mockServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(ApiInterface::class.java)
        MockitoAnnotations.initMocks(this)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun getDataFromApi() {
        Mockito.`when`(network.getDataFromApi(true, 0)).then { apiCallData() }
        network.getDataFromApi(true, 0)
        Assert.assertTrue(!deliveries.getDelieveries().isNullOrEmpty())
    }

    private fun apiCallData() {
        mockServer.enqueue(MockResponse().setBody(TestUtil.getDataAsString()))
        val list = apiInterface.getList(0, BuildConfig.NETWORK_PAGE_SIZE)
        val execute = list.execute()
        val body = execute.body()
        Mockito.`when`(deliveries.getDelieveries()).thenReturn(body)
    }

    @Test
    fun getDataFromApi_success() {
        mockServer.enqueue(MockResponse().setBody(TestUtil.getDataAsString()))
        val list = apiInterface.getList(0, BuildConfig.NETWORK_PAGE_SIZE)
        val execute = list.execute()
        val body = execute.body()
        Assert.assertTrue(!body.isNullOrEmpty())
    }

    @Test
    fun getDataFromApi_error() {
        mockServer.enqueue(MockResponse().setResponseCode(400))
        val list = apiInterface.getList(0, BuildConfig.NETWORK_PAGE_SIZE)
        val execute = list.execute()
        Assert.assertNotNull(execute.errorBody())
    }
}