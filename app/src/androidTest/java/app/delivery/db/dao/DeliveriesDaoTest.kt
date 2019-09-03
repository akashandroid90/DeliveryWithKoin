package app.delivery.db.dao

import TestUtil
import app.delivery.BuildConfig
import org.junit.Assert
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class DeliveriesDaoTest : KoinTest {
    private val deliveryDao: DeliveriesDao by inject()

    @Test
    @Throws(Exception::class)
    fun insertAllData() {
        val data = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        val insertAll = deliveryDao.insertAll(data)
        Assert.assertNotNull(insertAll)
        Assert.assertTrue(insertAll.isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun insertData() {
        val data = TestUtil.getRandomData()
        val insert = deliveryDao.insert(data)
        Assert.assertNotNull(insert)
    }

    @Test
    @Throws(Exception::class)
    fun deleteData() {
        val data = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        deliveryDao.insertAll(data)
        Assert.assertTrue(deliveryDao.deleteAll() > 0)
    }

    @Test
    @Throws(Exception::class)
    fun countRowData() {
        val data = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        deliveryDao.insertAll(data)
        Assert.assertTrue(deliveryDao.getCount() > 0)
    }

    @Test
    @Throws(Exception::class)
    fun deleteByIdCondition() {
        val data = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        deliveryDao.insertAll(data)
        Assert.assertTrue(deliveryDao.deleteByIdCondition(data.first().id) > 0)
    }

    @Test
    @Throws(Exception::class)
    fun queryData() {
        val data = TestUtil.getData(0, BuildConfig.NETWORK_PAGE_SIZE)
        deliveryDao.insertAll(data)
        Assert.assertNotNull(deliveryDao.getDelieveries())
        Assert.assertTrue(deliveryDao.getDelieveries().isNotEmpty())
    }
}