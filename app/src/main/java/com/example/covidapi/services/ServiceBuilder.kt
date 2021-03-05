package com.example.covidapi.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object is kotlin equivalent of a static class
object ServiceBuilder {
    private const val URL = "https://disease.sh/v2/"
    private val okHttp =OkHttpClient.Builder()

    private val builder = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService (serviceType :Class<T>):T{
        return retrofit.create(serviceType)
    }
}