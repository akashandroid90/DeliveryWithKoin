package app.delivery.di.module

import app.delivery.BuildConfig
import app.delivery.model.ThreadModel
import app.delivery.repository.network.ApiInterface
import app.delivery.repository.network.NetworkRepository
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * provides instance of objects used for performing network operations
 */
val networkModule = module {
    single {
        val thread: ThreadModel = get()
        val okHttpClientBuilder = OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                            .method(original.method(), original.body())
//                            .addHeader("Authorization", mAuth)
                            .build()
                    chain.proceed(request)
                }.dispatcher(Dispatcher(thread.networkThread))

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(logging)
        }
        val build = okHttpClientBuilder.build()

        Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(build)
                .build().create(ApiInterface::class.java)
    }

    single { NetworkRepository(get(), get(), get(), get()) }
}