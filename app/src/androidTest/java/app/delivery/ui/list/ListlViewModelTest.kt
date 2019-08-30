//package app.delivery.ui.list
//
//import TestUtil
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.paging.PagedList
//import androidx.room.Room
//import androidx.test.core.app.ApplicationProvider
//import app.delivery.BuildConfig
//import app.delivery.constants.DataState
//import app.delivery.db.AppDataBase
//import app.delivery.model.DeliveriesData
//import app.delivery.repository.network.NetworkRepository
//import com.nhaarman.mockitokotlin2.any
//import com.nhaarman.mockitokotlin2.mock
//import com.nhaarman.mockitokotlin2.whenever
//import org.junit.*
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//
//@RunWith(JUnit4::class)
//class ListlViewModelTest {
//    lateinit var data: ListViewModel
//    lateinit var appDatabase: AppDataBase
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun createViewModel() {
//        val boundryCallBack: DeliveryBoundryCallBack = mock()
//        val appRepository: NetworkRepository = mock()
//        appDatabase = Room.inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            AppDataBase::class.java
//        ).build()
//        data = ListViewModel(appDatabase.deliveriesDao(), boundryCallBack, appRepository)
//    }
//
//    @After
//    fun tearDown() = appDatabase.close()
//
//    @Test
//    fun testDataState() {
//        var state = DataState.LOADING
//        data.mResult?.dataState?.value = state
//        Assert.assertEquals(state, data.mResult?.dataState?.value)
//
//        state = DataState.LOADED
//        data.mResult?.dataState?.value = state
//        Assert.assertEquals(data.mResult?.dataState?.value, state)
//
//        state = DataState.NETWORKERROR
//        data.mResult?.dataState?.value = state
//        Assert.assertEquals(data.mResult?.dataState?.value, state)
//    }
//
//    @Test
//    fun testErrorMessage() {
//        val message = DataState.NETWORKERROR.name
//        data.mResult?.errorMessage?.value = message
//        Assert.assertEquals(data.mResult?.errorMessage?.value, message)
//    }
//
//    @Test
//    fun testData() {
//        val listData = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
//        val mockPagedList = mockPagedList(listData)
//        Assert.assertEquals(mockPagedList[0], listData[0])
//        Assert.assertTrue(mockPagedList.size == listData.size)
//    }
//
//    fun mockPagedList(list: List<DeliveriesData>): PagedList<DeliveriesData> {
//        val pagedList = mock<PagedList<DeliveriesData>>()
//        whenever(pagedList.get(any())).then { list[it.arguments.first() as Int] }
//        whenever(pagedList.size).thenReturn(list.size)
//        return pagedList
//    }
//}