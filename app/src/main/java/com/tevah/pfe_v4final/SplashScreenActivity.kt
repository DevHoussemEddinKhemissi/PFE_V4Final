package com.tevah.pfe_v4final

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.ArraySet
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.maps.DistanceMatrixApi
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DistanceMatrix
import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Models.FetchAllShopsResponse
import com.tevah.pfe_v4final.Models.Shop
import com.tevah.pfe_v4final.Models.ShopWithDistance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class Progressed {
    Api, DistanceInit, DistanceFinish
}

class SplashScreenActivity : AppCompatActivity() {

    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    var stepsToInit = ArraySet<Progressed>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var dataholder2: ArrayList<Shop>
    private var locations: List<LatLng> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        dataholder2 = ArrayList()
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)


        retrofit.fetchAllShops().enqueue(object : Callback<FetchAllShopsResponse> {
            override fun onResponse(call: Call<FetchAllShopsResponse>, response: Response<FetchAllShopsResponse>) {
                if (response.isSuccessful) {


                    response.body()?.let {
                        dataholder2.clear();
                        dataholder2.addAll(it.shop);
                        locations = dataholder2.map { shop ->
                            LatLng(shop.latitude.toDouble(), shop.langitude.toDouble())
                        }
                        Log.d("testretour shop", "onResponse: "+dataholder2.toString())
                    }

                    getLocationsDistance()
                } else {
                    // Handle the error here
                }
            }

            override fun onFailure(call: Call<FetchAllShopsResponse>, t: Throwable) {
                // Handle the failure here
            }
        })













        window.decorView.windowInsetsController!!.hide(
            android.view.WindowInsets.Type.statusBars()
                    or android.view.WindowInsets.Type.navigationBars()
        )




        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AuthentificationActivity::class.java)
            startActivity(intent)
            finish()
        }, 7000)
    }

    @SuppressLint("MissingPermission")
    fun getLocationsDistance() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            return
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            return
        }


        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Use the device's current location as the origin in the Directions API request
                    val origin = "${location.latitude},${location.longitude}"


                    // Initialize the SDK
                    val geoApiContext = GeoApiContext.Builder()
                        .apiKey("AIzaSyDNh_xiwmuAyIKTkXqMY8drG2k9SiucL2U")
                        .build()

                    val distanceMatrixApiRequest = DistanceMatrixApi.newRequest(geoApiContext)
                        .origins(origin)
                        .destinations(*locations.toTypedArray()) // Convert the list to an array
                        .mode(TravelMode.DRIVING)

                    val distanceMatrixApiResponse = distanceMatrixApiRequest.await()

                    // Loop through the rows and elements to get the distance and travel time
                    for ((index, row) in distanceMatrixApiResponse.rows.withIndex()) {
                        for ((jndex, element) in row.elements.withIndex()) {
                            val distance = element.distance
                            val duration = element.duration
                            Log.d("duration","Distance from $origin to ${locations[jndex]} is ${distance.inMeters} meters and takes ${duration.inSeconds} seconds.")
                        }

                    }

                    val sortedShops = ArrayList<ShopWithDistance>()

                    for ((index, row) in distanceMatrixApiResponse.rows.withIndex()) {
                        for ((jndex, element) in row.elements.withIndex()) {
                            val distance = element.distance.inMeters.toDouble()
                            val duration = element.duration

                            val shop = dataholder2[jndex]
                            val shopWithDistance = ShopWithDistance(shop, distance)

                            sortedShops.add(shopWithDistance)
                        }
                    }

                    val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val sortedShopsJson = Gson().toJson(sortedShops)
                    val editor = sharedPreferences.edit()
                    editor.putString("sortedShops", sortedShopsJson)
                    editor.apply()



                    Log.d("sortedlist", sortedShops.toString())


                }else {
                    Log.d("Duration", "Location is null")
                }
            }


    }




}