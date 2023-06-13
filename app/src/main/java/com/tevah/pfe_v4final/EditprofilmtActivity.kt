package com.tevah.pfe_v4final

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.tevah.pfe_v4final.Adapters.RoundedTransformation
import com.tevah.pfe_v4final.Models.UserX

class EditprofilmtActivity : AppCompatActivity() {
    var PICK_IMAGE_REQUEST = 58585
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofilmt)
        val gson = Gson()
        val sharedPreferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val user =  sharedPreferences.getString("user", null)?.let {
            val parsedUser = gson.fromJson(it, UserX::class.java)
            val profileImage = findViewById<ImageView>(R.id.imageView8)
            Picasso.get()
                .load(parsedUser.getImagePath()) // Replace "image_name" with the name of your image file in the drawable folder
                .transform(RoundedTransformation())
                .into(profileImage)
            val profileName = findViewById<TextView>(R.id.editTextTextPersonName5)
            profileName.text = parsedUser.name
        }

        val editImageButton = findViewById<ImageView>(R.id.imageView9)
        editImageButton.setOnClickListener{
            openGallery()
        }








      /*  val editText = findViewById<EditText>(R.id.editTextTextPersonName5)
        val redLineDrawable = ContextCompat.getDrawable(this, R.drawable.edittext_red_line)
        editText.background = redLineDrawable
        val editText = findViewById<EditText>(R.id.editTextTextPassword2)
        val redLineDrawable = ContextCompat.getDrawable(this, R.drawable.edittext_red_line)
        editText.background = redLineDrawable
        val editText = findViewById<EditText>(R.id.editTextTextPassword3)
        val redLineDrawable = ContextCompat.getDrawable(this, R.drawable.edittext_red_line)
        editText.background = redLineDrawable*/


    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data

            // Do something with the selected image URI, such as displaying it in an ImageView
        }
    }
}