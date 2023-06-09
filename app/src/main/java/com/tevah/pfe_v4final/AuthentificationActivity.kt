package com.tevah.pfe_v4final

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.CallbackManager.Factory.create
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Models.UserRegister
import com.tevah.pfe_v4final.Models.UserRegisterResponce
import com.tevah.pfe_v4final.Models.UserSignin
import com.tevah.pfe_v4final.Models.UserSigninResponce
import retrofit2.Call
import retrofit2.Response
import java.util.*

class AuthentificationActivity : AppCompatActivity() {

    var mGoogleSignInClient: GoogleSignInClient? = null
    var callbackManager: CallbackManager? = null
    var G_SIGN_IN = 0x111111
    var FB_SIGN_IN = 64206

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        val edittextemail = findViewById<EditText>(R.id.email)
        val edittextpassword = findViewById<EditText>(R.id.password)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val view = window.decorView
            val insetsController = view.windowInsetsController
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                insetsController.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {

            val view = window.decorView
            view.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }

        // get reference to button
        val Signup = findViewById(R.id.others) as TextView
        // set on-click listener
        Signup.setOnClickListener {
            Toast.makeText(this@AuthentificationActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, RegisterActivity()::class.java)
            startActivity(intent)
        }
        val btn_click_me = findViewById(R.id.logibtn) as Button
        // set on-click listener For Normal SignUP
        btn_click_me.setOnClickListener {
            Toast.makeText(this@AuthentificationActivity, "You clicked me.", Toast.LENGTH_SHORT).show()


            val obj = UserSignin(

                email = edittextemail.text.toString(),
                password = edittextpassword.text.toString(),

            )
            retrofit.LoginViaEmail(obj).enqueue(
                object : retrofit2.Callback<UserSigninResponce>{
                    override fun onResponse(call: Call<UserSigninResponce>, response: Response<UserSigninResponce>) {


                        val bat = response.body().toString()

                        Toast.makeText(this@AuthentificationActivity,bat, Toast.LENGTH_LONG).show()
                        Log.d("Success", bat)
                        if (response.body()?.message.toString()=="User Found."){
                            val battoken = response.body()?.accessToken.toString()
                            editor.putString("Token", battoken)
                            editor.apply()
                            Log.d("User Found", bat)
                            intent = Intent(applicationContext, MainMenuActivity()::class.java)
                                startActivity(intent)

                        }
                        else if (response.body()?.message.toString()=="User Not Found.") {
                            Log.d("User Not Found", bat)
                        }
                        else {
                            Log.d("Server issues", bat)
                        }


                    }

                    override fun onFailure(call: Call<UserSigninResponce>, t: Throwable) {
                        Toast.makeText(this@AuthentificationActivity,t.toString(), Toast.LENGTH_LONG).show()
                        Log.d("onFailure", t.toString())
                    }


                }
            )






            /*intent = Intent(applicationContext, MainMenuActivity()::class.java)
            startActivity(intent)*/

        }

        val btn_login_google = findViewById(R.id.loginGoogleButton) as ImageView
        // set on-click listener For Google
        btn_login_google.setOnClickListener {
            this.loginViaGoogle()
        }

        val btn_login_facebook = findViewById(R.id.loginFacebookButton) as ImageView
        // set on-click listener For Facebook
        btn_login_facebook.setOnClickListener {
            loginViaFacebook()
        }

    }

    fun loginViaFacebook() {
        //LoginManager.getInstance().logOut();
        callbackManager = create()
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

                override fun onSuccess(loginResult: LoginResult) {
                    // App code
                    startActivity(Intent(this@AuthentificationActivity, TestActivity::class.java))
                }

                override fun onCancel() {
                    // App code
                    Toast.makeText(applicationContext, "Login via Facebook canceled", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    Toast.makeText(applicationContext, "Login via Facebook failed", Toast.LENGTH_LONG).show()
                }
            })
        LoginManager.getInstance().logInWithReadPermissions(this@AuthentificationActivity, listOf("email"))
    }

    fun loginViaGoogle(){
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("806893257093-lh99treqd4vg9oaisa6h6li01ibokv9s.apps.googleusercontent.com")
            .requestId()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, G_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == G_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInGoogleResult(task)
        }
        if (requestCode == FB_SIGN_IN) {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInGoogleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            val intent = Intent(this@AuthentificationActivity, TestActivity::class.java)
            startActivity(intent)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
           Toast.makeText(this.applicationContext, "Login via Google failed with code ${e.statusCode}", Toast.LENGTH_LONG).show()
            
            //updateUI(null);
        }
    }
}
