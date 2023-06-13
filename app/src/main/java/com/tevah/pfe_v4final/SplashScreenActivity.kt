package com.tevah.pfe_v4final

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.ArraySet
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.maps.DistanceMatrixApi
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.model.DistanceMatrix
import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode

enum class Progressed {
    Api, DistanceInit, DistanceFinish
}

class SplashScreenActivity : AppCompatActivity() {

    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    var stepsToInit = ArraySet<Progressed>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locations =  listOf(
        LatLng(36.899229,10.1874636),
        LatLng(36.8991569,10.1818463),
        LatLng(36.89147,10.1687499),
        LatLng(36.8878833,10.164503),
        LatLng(36.8900354,10.1777686),
        LatLng(36.805729,10.109200)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.decorView.windowInsetsController!!.hide(
            android.view.WindowInsets.Type.statusBars()
                    or android.view.WindowInsets.Type.navigationBars()
        )


        getLocationsDistance() //wait

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AuthentificationActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
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

                }else {
                    Log.d("Duration", "Location is null")
                }
            }


    }


}