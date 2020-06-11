package com.corona_ui.bluetoothlocator.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClientPhp {

    companion object {
        // var PhpBaseUrl: String = "http://avbrh.gearhostpreview.com/"
        var PhpBaseUrl: String = "http://dmimsdu.in/web/"
        var retrofit: Retrofit? = null

        fun getClient(): Retrofit {
            if (retrofit == null) {

                val gson = GsonBuilder()
                    .setLenient()
                    .create()
//                val connectionPool = ConnectionPool(5, 60, TimeUnit.SECONDS)
                var okHttpClient = OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
//                    .connectionPool(connectionPool)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(50, TimeUnit.SECONDS)
                    .writeTimeout(50, TimeUnit.SECONDS)
                    .build()

//                val interceptor = HttpLoggingInterceptor()
//                interceptor.level = HttpLoggingInterceptor.Level.BODY
//                val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()


                retrofit = Retrofit.Builder()

                    .baseUrl(PhpBaseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }


    }
}