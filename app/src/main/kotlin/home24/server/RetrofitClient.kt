package home24.server

import home24.config.Parameters
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by sammy on 7/19/2018
 */
//Building the Retrofit Client
object RetrofitClient {

    fun getClient(): Retrofit {

        val client = OkHttpClient.Builder()
                .build()

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(Parameters.API_BASE_URL)
                .build()
    }
}