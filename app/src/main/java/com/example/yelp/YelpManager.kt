package com.example.yelp

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

class YelpManager {

    val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(loggingInterceptor)

        okHttpClient = builder.build()
    }

    suspend fun retrieveYelps(lat: Double, lon: Double, apiKey: String): List<YelpBusiness>{
        val request = Request.Builder()
            .url("https://api.yelp.com/v3/businesses/search?latitude=$lat&longitude=$lon")
            .get()
            .addHeader("authorization", "Bearer $apiKey")
            .build()

        val response  = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        Log.d("http response", "response is $response and body is $responseBody")
        return listOf()
    }
}