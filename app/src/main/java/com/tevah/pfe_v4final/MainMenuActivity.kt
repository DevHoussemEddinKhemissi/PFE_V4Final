package com.tevah.pfe_v4final

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Models.UserRegisterResponce
import com.tevah.pfe_v4final.Models.UserRetrieve
import com.tevah.pfe_v4final.SQLDB.Database
import retrofit2.Call
import retrofit2.Response

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
      /*  val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val value = sharedPref.getString("Token", "")
        Log.d("RetriveLogin", value.toString())

        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        retrofit.GetUSER(value.toString()).enqueue(
            object : retrofit2.Callback<UserRetrieve>{
                override fun onResponse(
                    call: Call<UserRetrieve>,
                    response: Response<UserRetrieve>
                ) {

                    Log.d("RetriveLogin", response.body().toString())
                }

                override fun onFailure(call: Call<UserRetrieve>, t: Throwable) {
                    Log.d("RetriveLoginFail", "No connection to server")
                }


            }
        )*/

        val database = Database(this)
        val db = database.writableDatabase


        val isDatabaseCreated = getDatabasePath("Tevah.db").exists()
        if (isDatabaseCreated) {

          //  Toast.makeText(this, "Database created successfully", Toast.LENGTH_SHORT).show()

            // Perform any additional operations or queries here
        } else {

           // Toast.makeText(this, "Failed to create database", Toast.LENGTH_SHORT).show()


        }
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val navigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        supportFragmentManager.beginTransaction().replace(R.id.body_container, HomeFragment()).commit()
        navigationView.setSelectedItemId(R.id.nac_home)
        navigationView.setOnItemSelectedListener { item ->
            val fragment: Fragment? = when (item.itemId) {
                R.id.nac_home -> HomeFragment()
                R.id.nac_like -> ProductsFragment()
                R.id.nac_seach -> AccountFragment()
                R.id.nac_shop -> WishListFragment()
                else -> null
            }
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.body_container, fragment).commit()
            }
            true
        }

    }
}