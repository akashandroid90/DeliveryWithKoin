package app.delivery.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.delivery.BuildConfig
import app.delivery.db.dao.DeliveriesDao
import app.delivery.model.DeliveriesData

/**
 * Database class used to provide database table dao class to perform db operations
 */
@Database(entities = [DeliveriesData::class], version = BuildConfig.DB_VERSION, exportSchema = true)
abstract class AppDataBase : RoomDatabase() {
    abstract fun deliveriesDao(): DeliveriesDao
}
