package com.example.myapplication.data.datasource.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private lateinit var retrofit: Retrofit
    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .setLenient()
        .create()



    fun getClient(baseUrl: String): Retrofit {

        if(!this::retrofit.isInitialized){
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
//            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val client = OkHttpClient.Builder().addInterceptor(interceptor).addNetworkInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent","Android")
                    .addHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdXRoIjoiUk9MRV9VU0VSIiwic2Nob29sIjp7InNjaG9vbElkIjoxLCJzY2hvb2xOYW1lIjoiV2FzZWRhIFVuaXZlcnNpdHkiLCJyZWdleCI6IlxcYlteXFxzXStAd2FzZWRhXFwuanBcXGIifSwidXNlck5pY2tuYW1lIjoiYWRtaW4yIiwidXNlckVtYWlsIjoibHNqcGpzMWRkZGRAd2FzZWRhLmpwIiwidXNlclR5cGUiOjAsInVzZXJJZCI6ImdnZyIsInVzZXJJbmZvSWQiOjE5Miwibm90aWZpY2F0aW9uVG9rZW4iOiJyaWdodENhc2UifQ.ZwLJOaLo40ovtDj_ramn_0KT_iPrFuEafWDTsKnv7QRjSoaiEZMlZ5Koxmhdj_oPrEIogwmCiXAw-vf7y-ZMig")
                chain.proceed(requestBuilder.build())
            }.build()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)

                //.addConverterFactory(ScalarsConverterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create(gson))
                //.addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson)) //210811 merge issue, comment 정상적으로 오게끔 하는게 이거임.
                .build()
        }
        return retrofit
    }
}