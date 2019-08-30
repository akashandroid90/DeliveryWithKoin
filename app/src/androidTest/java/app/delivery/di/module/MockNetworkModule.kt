package app.delivery.di.module

import android.content.Context
import android.os.Handler
import app.delivery.model.ThreadModel
import app.delivery.network.MockNetworkRepository
import app.delivery.repository.database.DbRepository
import app.delivery.repository.network.ApiInterface
import app.delivery.repository.network.NetworkRepository
import app.delivery.utils.NetworkConnectionUtil
import app.delivery.utils.resourceState.DataIdleStateResource
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class MockNetworkModule {
    @Provides
    @Singleton
    fun providesMockServer() = MockWebServer()

    @Provides
    @Singleton
    fun providesDataIdleStateResource() = DataIdleStateResource()

    @Provides
    @Singleton
    fun provideApiInterface(server: MockWebServer): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(server.url("/").toString())
//            .baseUrl("https://d9b1126c-0b31-4346-87fd-7f0a46eb6ad9.mock.pstmn.io")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        dbRepo: DbRepository,
        context: Context,
        connection: ApiInterface, server: MockWebServer, thread: ThreadModel,
        networkConnectionUtil: NetworkConnectionUtil,
        handler: Handler,
        idleStateResource: DataIdleStateResource
    ): NetworkRepository {
        return MockNetworkRepository(
            dbRepo,
            context,
            connection,
            server,
            thread,
            networkConnectionUtil,
            handler, idleStateResource
        )
    }
}