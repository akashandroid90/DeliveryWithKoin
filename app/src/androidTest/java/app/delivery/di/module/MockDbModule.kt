package app.delivery.di.module

import androidx.room.Room
import app.delivery.db.MockAppDataBase
import app.delivery.model.ThreadModel
import app.delivery.repository.database.DbRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mockDbModule = module {
    single {
        Room.inMemoryDatabaseBuilder(
            androidContext(),
            MockAppDataBase::class.java
        ).build().deliveriesDao()
    }
    single {
        val thread: ThreadModel = get()
        DbRepository(thread.dbThread, get())
    }
}