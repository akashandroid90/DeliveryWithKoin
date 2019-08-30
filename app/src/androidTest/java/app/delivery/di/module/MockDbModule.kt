package app.delivery.di.module

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.delivery.db.MockAppDataBase
import app.delivery.db.dao.DeliveriesDao
import app.delivery.model.ThreadModel
import app.delivery.repository.database.DbRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class MockDbModule {
    @Provides
    @Singleton
    fun provideDatabase(): MockAppDataBase {
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MockAppDataBase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideDeliveriesDao(database: MockAppDataBase): DeliveriesDao {
        return database.deliveriesDao()
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        thread: ThreadModel,
        deliveriesDao: DeliveriesDao
    ): DbRepository {
        return DbRepository(thread.dbThread, deliveriesDao)
    }
}