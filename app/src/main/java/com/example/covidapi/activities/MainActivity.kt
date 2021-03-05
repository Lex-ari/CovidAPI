package com.example.covidapi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidapi.R
import com.example.covidapi.helpers.CountriesAdapter
import com.example.covidapi.models.MyCountry
import com.example.covidapi.services.CountryService
import com.example.covidapi.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCountries()
    }


    private fun loadCountries() {
        //initiate the service
        val destinationService  = ServiceBuilder.buildService(CountryService::class.java)
        val requestCall =destinationService.getAffectedCountryList()

        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<MyCountry>>{
            override fun onResponse(call: Call<List<MyCountry>>, response: Response<List<MyCountry>>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val countryList  = response.body()!!
                    Log.d("Response", "countrylist size : ${countryList.size}")
                    country_recycler.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@MainActivity,2)
                        adapter = CountriesAdapter(countryList)
                    }
                }else{
                    Toast.makeText(this@MainActivity, "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<MyCountry>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })

        //any code after enqueue will be executed immediately after the requestCall is initiated, but necessarily after we get response
    }
}