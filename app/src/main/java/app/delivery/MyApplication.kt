package app.delivery

import android.app.Application
import app.delivery.di.module.appModule
import app.delivery.di.module.dbModule
import app.delivery.di.module.networkModule
import app.delivery.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * base application class
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG)
                androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
            modules(dbModule)
            modules(networkModule)
            modules(viewModelModule)
        }
    }
}