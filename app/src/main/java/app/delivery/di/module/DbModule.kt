package app.delivery.di.module

import androidx.room.Room
import app.delivery.db.AppDataBase
import app.delivery.model.ThreadModel
import app.delivery.repository.database.DbRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * provides instances of objects used for performing database operations
 */
val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDataBase::class.java,
            "app_database.db"
        ).build().deliveriesDao()
    }
    single {
        val thread: ThreadModel = get()
        DbRepository(thread.dbThread, get())
    }
}