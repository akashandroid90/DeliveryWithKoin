package app.delivery.db.dao

import TestUtil
import app.delivery.BuildConfig
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DeliveryDaoTest {
    @Mock
    lateinit var deliveryDao: DeliveriesDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @Throws(Exception::class)
    fun insertAllData() {
        val data = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        Mockito.doReturn(LongArray(BuildConfig.NETWORK_PAGE_SIZE)).`when`(deliveryDao)
            .insertAll(data)
        val insertAll = deliveryDao.insertAll(data)
        Assert.assertNotNull(insertAll)
        Assert.assertTrue(insertAll.isNotEmpty())
        Assert.assertEquals(BuildConfig.NETWORK_PAGE_SIZE, insertAll.size)
    }

    @Test
    @Throws(Exception::class)
    fun insertData() {
        val data = TestUtil.getRandomData()
        Mockito.doReturn(BuildConfig.NETWORK_PAGE_SIZE.toLong()).`when`(deliveryDao).insert(data)
        val insert = deliveryDao.insert(data)
        Assert.assertNotNull(insert)
    }

    @Test
    @Throws(Exception::class)
    fun deleteData() {
        Mockito.doReturn(1).`when`(deliveryDao).deleteAll()
        Assert.assertTrue(deliveryDao.deleteAll() > 0)
    }

    @Test
    @Throws(Exception::class)
    fun countRowData() {
        Mockito.doReturn(BuildConfig.NETWORK_PAGE_SIZE).`when`(deliveryDao).getCount()
        Assert.assertTrue(deliveryDao.getCount() > 0)
    }

    @Test
    @Throws(Exception::class)
    fun deleteByIdCondition() {
        Mockito.doReturn(BuildConfig.NETWORK_PAGE_SIZE).`when`(deliveryDao)
            .deleteByIdCondition(BuildConfig.NETWORK_PAGE_SIZE)
        Assert.assertTrue(deliveryDao.deleteByIdCondition(BuildConfig.NETWORK_PAGE_SIZE) > 0)
    }

    @Test
    @Throws(Exception::class)
    fun queryData() {
        val data = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        Mockito.doReturn(data).`when`(deliveryDao).getDelieveries()
        Assert.assertNotNull(deliveryDao.getDelieveries())
        Assert.assertTrue(deliveryDao.getDelieveries().isNotEmpty())
    }
}