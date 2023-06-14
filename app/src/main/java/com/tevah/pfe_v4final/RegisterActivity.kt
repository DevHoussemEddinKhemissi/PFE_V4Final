package com.tevah.pfe_v4final

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Models.UserRegister
import com.tevah.pfe_v4final.Models.UserRegisterResponce
import retrofit2.Call
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_register)

        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        val edittextusername = findViewById<EditText>(R.id.username)
        val edittextemail = findViewById<EditText>(R.id.email)
        val edittextpassword = findViewById<EditText>(R.id.password)
        val buttonsignup = findViewById<Button>(R.id.signupbtn)

        buttonsignup.setOnClickListener {
            val obj = UserRegister(
                name = edittextusername.text.toString(),
                username = "housem",
                email = edittextemail.text.toString(),
                tel = "22522229",
                roles = listOf("user"),
                password = edittextpassword.text.toString(),
                image = ""
            )

            retrofit.CreateUser(obj).enqueue(object : retrofit2.Callback<UserRegisterResponce> {
                override fun onResponse(call: Call<UserRegisterResponce>, response: Response<UserRegisterResponce>) {
                    if (response.isSuccessful) {

                            val intent = Intent(applicationContext, AuthentificationActivity::class.java)
                            startActivity(intent)

                    } else {

                    }
                }

                override fun onFailure(call: Call<UserRegisterResponce>, t: Throwable) {
                    Log.d("onFailure", t.toString())

                }
            })
        }
    }
}