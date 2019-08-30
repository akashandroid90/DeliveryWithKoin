package app.delivery.ui.list

import TestUtil
import app.delivery.BuildConfig
import app.delivery.db.dao.DeliveriesDao
import app.delivery.repository.network.ApiInterface
import app.delivery.repository.network.NetworkRepository
import com.google.gson.Gson
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

class DeliveryBoundryCallBackTest {
    @Mock
    lateinit var appRepository: NetworkRepository
    private lateinit var boundryCallBack: DeliveryBoundryCallBack
    private lateinit var mockServer: MockWebServer
    private lateinit var apiInterface: ApiInterface
    @Mock
    lateinit var deliveriesDao: DeliveriesDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        boundryCallBack = DeliveryBoundryCallBack(appRepository)
    }

    @Before
    fun mockServerConnection() {
        mockServer = MockWebServer()
        apiInterface = Retrofit.Builder()
            .baseUrl(mockServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(ApiInterface::class.java)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun onZeroItemsLoaded() {
        Mockito.`when`(appRepository.getDataFromApi(true,0)).then { mockResponseList() }
        boundryCallBack.onZeroItemsLoaded()
        val delieveries = deliveriesDao.getDelieveries()
        Assert.assertNotNull(delieveries)
        Assert.assertTrue(delieveries.isNotEmpty())
    }

    @Test
    fun onItemAtEndLoaded() {
        val randomData = TestUtil.getRandomData()
        Mockito.`when`(appRepository.getDataFromApi(false,randomData.id + 1)).then { mockResponseList() }
        boundryCallBack.onItemAtEndLoaded(randomData)
        val delieveries = deliveriesDao.getDelieveries()
        Assert.assertNotNull(delieveries)
        Assert.assertTrue(delieveries.isNotEmpty())
    }

    private fun mockResponseList() {
        val listResponse = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        mockServer.enqueue(MockResponse().setBody(Gson().toJson(listResponse)))
        val list = apiInterface.getList(0, BuildConfig.NETWORK_PAGE_SIZE)
        val execute = list.execute()
        val body = execute.body()
        Mockito.doReturn(body).`when`(deliveriesDao).getDelieveries()
    }
}