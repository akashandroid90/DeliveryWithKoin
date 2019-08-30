package app.delivery

import android.app.Application
import android.os.Handler
import app.delivery.di.component.DaggerMockAppComponent
import app.delivery.di.module.AppModule
import app.delivery.utils.resourceState.DataIdleStateResource
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MockApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var idleStateResource: DataIdleStateResource
    @Inject
    lateinit var handler: Handler

    override fun androidInjector(): AndroidInjector<Any> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerMockAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }
}