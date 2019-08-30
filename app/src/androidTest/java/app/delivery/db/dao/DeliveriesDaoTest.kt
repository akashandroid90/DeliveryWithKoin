package app.delivery.db.dao

import TestUtil
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.delivery.BuildConfig
import app.delivery.db.MockAppDataBase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class DeliveriesDaoTest {
    private lateinit var deliveryDao: DeliveriesDao
    private lateinit var database: MockAppDataBase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MockAppDataBase::class.java
        ).build()
        deliveryDao = database.deliveriesDao()
    }

    @After
    @Throws(IOException::class)
    fun after() {
        database.close()
    }

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