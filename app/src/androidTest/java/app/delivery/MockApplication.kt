package app.delivery

import android.app.Application
import app.delivery.di.module.mockAppModule
import app.delivery.di.module.mockDbModule
import app.delivery.di.module.mockNetworkModule
import app.delivery.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MockApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        loadKoinModules(mockDbModule)
//        loadKoinModules(mockNetworkModule)
        startKoin {
            if (BuildConfig.DEBUG)
                androidLogger()
            androidContext(this@MockApplication)
            modules(mockAppModule)
            modules(mockDbModule)
            modules(mockNetworkModule)
            modules(viewModelModule)
        }
    }
}