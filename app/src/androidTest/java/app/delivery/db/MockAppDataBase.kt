package app.delivery.db

import androidx.room.Database
import app.delivery.BuildConfig
import app.delivery.model.DeliveriesData

@Database(
    entities = [DeliveriesData::class],
    version = BuildConfig.DB_VERSION,
    exportSchema = false
)
abstract class MockAppDataBase : AppDataBase()