package app.delivery.repository.database

import app.delivery.db.dao.DeliveriesDao
import app.delivery.model.DeliveriesData
import java.util.concurrent.ExecutorService

/**
 * perform database operations
 */
class DbRepository (
    private val service: ExecutorService,
    private val deliveriesDao: DeliveriesDao
) {
    fun insertDeliveryData(isReset:Boolean,list: List<DeliveriesData>) {
        service.execute {
            deliveriesDao.insertAll(list)
            if (isReset){
                deliveriesDao.deleteByIdCondition(list.last().id)
            }
        }
    }

    fun deleteDeliveryData() {
        service.execute {
            deliveriesDao.deleteAll()
        }
    }
}