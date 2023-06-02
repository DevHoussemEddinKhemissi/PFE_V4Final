package com.tevah.pfe_v4final

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener

class TestActivity : AppCompatActivity() {
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso!!)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            Log.d("token", acct.idToken.toString()) //token google
        }


        // Get the current user's access token
        val accessToken = AccessToken.getCurrentAccessToken()

// Check if the user is logged in
        if (accessToken != null && !accessToken.isExpired) {
            // Get the user's token
            val token = accessToken.token
        Log.d("token",token) //token facebook
            // Use the user's token
            // ...
        } else {
            // User is not logged in or the access token is expired
            // ...
        }

        val boutonLogoutFacebook = findViewById<Button>(R.id.logoutFacebook)
        boutonLogoutFacebook.setOnClickListener {
            LoginManager.getInstance().logOut()
            finish()
        }

        val boutonLogoutGoogle = findViewById<Button>(R.id.logoutGoogle)
        boutonLogoutGoogle.setOnClickListener {
            gsc!!.signOut().addOnCompleteListener(OnCompleteListener<Void?> {
                finish()
            })
        }





    }
}