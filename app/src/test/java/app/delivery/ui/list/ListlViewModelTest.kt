package app.delivery.ui.list

import TestUtil
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import app.delivery.BuildConfig
import app.delivery.constants.DataState
import app.delivery.db.dao.DeliveriesDao
import app.delivery.model.DeliveriesData
import app.delivery.repository.network.ApiInterface
import app.delivery.repository.network.NetworkRepository
import app.delivery.ui.base.ViewModelTest
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
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ListlViewModelTest : ViewModelTest<ListViewModel>() {
    @Mock
    lateinit var deliveriesDao: DeliveriesDao
    @Mock
    lateinit var listData: DataSource.Factory<Int, DeliveriesData>
    @Mock
    lateinit var appRepository: NetworkRepository
    @Mock
    lateinit var listResult: LiveData<PagedList<DeliveriesData>>
    private lateinit var mockServer: MockWebServer
    private lateinit var apiInterface: ApiInterface
    private lateinit var randomData: DeliveriesData

    override fun createViewModel(): ListViewModel {
        MockitoAnnotations.initMocks(this)
        val boundryCallBack: DeliveryBoundryCallBack = mock()
        Mockito.`when`(deliveriesDao.getAllDelieveries()).thenReturn(listData)
        return ListViewModel(deliveriesDao, boundryCallBack, appRepository)
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
    fun testCheckAndInitialize() {
        data.mResult = null
        data.checkAndInitialize()
        Assert.assertNotNull(data.mResult)
    }

    @Test
    fun testDataState() {
        var state = DataState.LOADING
        data.mResult?.dataState?.value = state
        Assert.assertEquals(state, data.mResult?.dataState?.value)

        state = DataState.LOADED
        data.mResult?.dataState?.value = state
        Assert.assertEquals(data.mResult?.dataState?.value, state)

        state = DataState.NETWORKERROR
        data.mResult?.dataState?.value = state
        Assert.assertEquals(data.mResult?.dataState?.value, state)
    }

    @Test
    fun testErrorMessage() {
        val message = DataState.NETWORKERROR.name
        data.mResult?.errorMessage?.value = message
        Assert.assertEquals(data.mResult?.errorMessage?.value, message)
    }

    @Test
    fun testData() {
        val listData = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        val mockPagedList = mockPagedList(listData)
        Assert.assertEquals(mockPagedList[0], listData[0])
        Assert.assertTrue(mockPagedList.size == listData.size)
    }

    private fun mockPagedList(list: List<DeliveriesData>): PagedList<DeliveriesData> {
        val pagedList = mock<PagedList<DeliveriesData>>()
        Mockito.`when`(pagedList[any()]).then { list[it.arguments.first() as Int] }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }

    @Test
    fun resetData() {
        Mockito.`when`(appRepository.getDataFromApi(true,0)).then { mockResponseList() }
        data.resetData()
        Assert.assertNotNull(data.mResult?.data?.value)
        Assert.assertTrue(data.mResult?.data?.value?.size!! > 0)
        Assert.assertEquals(data.mResult?.data?.value?.last()?.id, randomData.id)
    }

    @Test
    fun retry() {
        Mockito.`when`(appRepository.getDataFromApi(false,0)).then { mockResponseList() }
        data.retry()
        Assert.assertNotNull(data.mResult?.data?.value)
        Assert.assertTrue(data.mResult?.data?.value?.size!! > 0)
        Assert.assertEquals(data.mResult?.data?.value?.last()?.id, randomData.id)
    }

    @Test
    fun retry_withExisting_data() {
        mockResponseList()
        Mockito.`when`(appRepository.getDataFromApi(false,0)).then { mockResponseList() }
        data.retry()
        Assert.assertNotNull(data.mResult?.data?.value)
        Assert.assertTrue(data.mResult?.data?.value?.size!! > 0)
        Assert.assertEquals(data.mResult?.data?.value?.last()?.id, randomData.id)
    }

    private fun mockResponseList() {
        val listResponse = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        randomData = listResponse.last()
        mockServer.enqueue(MockResponse().setBody(Gson().toJson(listResponse)))
        val list = apiInterface.getList(0, BuildConfig.NETWORK_PAGE_SIZE)
        val execute = list.execute()
        val body = execute.body()
        val pagedList = body?.let { mockPagedList(it) }
        data.mResult?.data = listResult
        Mockito.`when`(data.mResult?.data?.value).thenReturn(pagedList)
    }
}