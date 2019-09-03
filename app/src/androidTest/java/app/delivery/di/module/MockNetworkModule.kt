package app.delivery.di.module

import app.delivery.network.MockNetworkRepository
import app.delivery.repository.network.ApiInterface
import app.delivery.repository.network.NetworkRepository
import app.delivery.utils.resourceState.DataIdleStateResource
import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val mockNetworkModule = module {
    single { MockWebServer() }
    single { DataIdleStateResource() }
    single {
        val server: MockWebServer = get()
        Retrofit.Builder()
            .baseUrl(server.url("/").toString())
//            .baseUrl("https://d9b1126c-0b31-4346-87fd-7f0a46eb6ad9.mock.pstmn.io")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(ApiInterface::class.java)
    }

    single {
        MockNetworkRepository(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        ) as NetworkRepository
    }
}