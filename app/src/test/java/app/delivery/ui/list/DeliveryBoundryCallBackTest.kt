package app.delivery.ui.list

import TestUtil
import androidx.paging.PagedList
import app.delivery.BuildConfig
import app.delivery.db.dao.DeliveriesDao
import app.delivery.model.DeliveriesData
import app.delivery.repository.network.ApiInterface
import app.delivery.repository.network.NetworkRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
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
    lateinit var boundryCallBack: DeliveryBoundryCallBack
    lateinit var mockServer: MockWebServer
    lateinit var apiInterface: ApiInterface
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
        Mockito.`when`(appRepository.getDataFromApi(0)).then { mockResponseList() }
        boundryCallBack.onZeroItemsLoaded()
        val delieveries = deliveriesDao.getDelieveries()
        Assert.assertNotNull(delieveries)
        Assert.assertTrue(delieveries.size > 0)
    }

    @Test
    fun onItemAtEndLoaded() {
        val randomData = TestUtil.getRandomData()
        Mockito.`when`(appRepository.getDataFromApi(randomData.id + 1)).then { mockResponseList() }
        boundryCallBack.onItemAtEndLoaded(randomData)
        val delieveries = deliveriesDao.getDelieveries()
        Assert.assertNotNull(delieveries)
        Assert.assertTrue(delieveries.size > 0)
    }

    fun mockResponseList() {
        val listResponse = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        mockServer.enqueue(MockResponse().setBody(Gson().toJson(listResponse)))
        val list = apiInterface.getList(0, BuildConfig.NETWORK_PAGE_SIZE)
        val execute = list.execute()
        val body = execute.body()
        Mockito.doReturn(body).`when`(deliveriesDao).getDelieveries()
    }

    fun mockPagedList(list: List<DeliveriesData>): PagedList<DeliveriesData> {
        val pagedList = mock<PagedList<DeliveriesData>>()
        Mockito.`when`(pagedList.get(any())).then { list[it.arguments.first() as Int] }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }
}