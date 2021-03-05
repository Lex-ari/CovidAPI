package com.example.covidapi.services

import com.example.covidapi.models.MyCountry
import retrofit2.Call
import retrofit2.http.GET

interface CountryService {
    @GET("countries")
    fun getAffectedCountryList () : Call<List<MyCountry>>
}