package app.delivery.di.component

import app.delivery.MockApplication
import app.delivery.di.module.*
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidInjectionModule::class, AppModule::class, MockDbModule::class, MockNetworkModule::class,
        ActivityModule::class, ViewModelModule::class]
)
interface MockAppComponent : AppComponent {
    fun inject(app: MockApplication)
}