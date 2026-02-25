package com.example.yelp

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject;

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
        Log.d("response","$response")
        if (response.isSuccessful && !responseBody.isNullOrEmpty()){
            val yelps=mutableListOf<YelpBusiness>()
            val json= JSONObject(responseBody)
            val businesses=json.getJSONArray("businesses")
            for (i in 0 until businesses.length()){
                val currentBusiness=businesses.getJSONObject(i)
                val name=currentBusiness.getString("name")
                val rating=currentBusiness.getDouble("rating")
                val icon=currentBusiness.getString("image_url")
                val categories=currentBusiness.getJSONArray("categories")
                val currentCategory=categories.getJSONObject(0)
                val title=currentCategory.getString("title")
                val url=currentBusiness.getString("url")

                val yelp=YelpBusiness(
                    restaurantName = name,
                    category=title,
                    rating=rating,
                    icon=icon,
                    url=url
                )
                yelps.add(yelp)

            }
            return yelps
        }else{
            return listOf()
        }
    }
}