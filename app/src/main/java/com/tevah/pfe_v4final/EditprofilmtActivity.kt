package com.tevah.pfe_v4final

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Adapters.RoundedTransformation
import com.tevah.pfe_v4final.Models.UserUpdateResponse
import com.tevah.pfe_v4final.Models.UserX
import com.tevah.pfe_v4final.Models.UserXX
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import java.io.InputStream

class EditprofilmtActivity : AppCompatActivity() {
    private lateinit var context: Context
    var PICK_IMAGE_REQUEST = 58585
    var newFileUri: Uri? = null
    lateinit var profileName: TextView
    lateinit var oldpasswordTextView: TextView
    lateinit var newPasswordTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofilmt)
        context = this
        profileName = findViewById<TextView>(R.id.editTextTextPersonName5)
        oldpasswordTextView =  findViewById<TextView>(R.id.editTextTextPassword2)
        newPasswordTextView= findViewById<TextView>(R.id.editTextTextPassword3)
        val gson = Gson()
        val sharedPreferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val user =  sharedPreferences.getString("user", null)?.let {
            val parsedUser = gson.fromJson(it, UserX::class.java)
            val profileImage = findViewById<ImageView>(R.id.imageView8)
            Picasso.get()
                .load(parsedUser.getImagePath()) // Replace "image_name" with the name of your image file in the drawable folder
                .transform(RoundedTransformation())
                .into(profileImage)
            profileName.text = parsedUser.name
        }

        val editImageButton = findViewById<ImageView>(R.id.imageView9)
        editImageButton.setOnClickListener{
            openGallery()
        }
        val saveButton = findViewById<TextView>(R.id.textView39)
        saveButton.setOnClickListener{
            saveChanges()
        }


    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun saveChanges(){

        val newName = profileName.text.toString()
        val oldPassword = oldpasswordTextView.text.toString()
        val newPassword = newPasswordTextView.text.toString()
        var errorText = ""
        if (newName.length < 3) {
            errorText = "* Le nouveau nom doit comporter plus de 3 caractères\n"
        }
        if ((oldPassword.length != 0 || newPassword.length != 0) && (oldPassword.length == 0 || newPassword.length ==0)) {
            errorText += "* Pour changer le mot de passe, remplissez l'anciens et le nouveaux champs\n"
        }
        if (errorText.length > 0) {
            val popup = PopupDisclaimer(context)
            popup.setup("Vérifier les entrées !",
                errorText,
                "Annuler") {
                popup.dismiss()
            }
            popup.show()
        }else {
            callUpdateApi(newName.trim(),oldPassword.trim(),newPassword.trim())
        }
    }

    private fun callUpdateApi(name: String, oldPass:String, newPass: String){
        Toast.makeText(this,"Start updating",Toast.LENGTH_SHORT).show()
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        val sharedPreferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val token = sharedPreferences.getString("Token","")!!

        val nameRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val oldPasswordRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), oldPass)
        val newPasswordRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), newPass)

        var imagePart: MultipartBody.Part? = null
        newFileUri?.let {
            val inputStream = contentResolver.openInputStream(it)
            val imageRequestBody = inputStream?.use { input ->
                input.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
            }

            imagePart= imageRequestBody?.let { request->
                MultipartBody.Part.createFormData("image", "image.jpg", request)
            }
        }
        val popup = PopupDisclaimer(this)
        val call = retrofit.updateUser(token,nameRequestBody, newPasswordRequestBody,oldPasswordRequestBody,imagePart)
        call.enqueue(object : retrofit2.Callback<UserUpdateResponse> {
            override fun onResponse(call: Call<UserUpdateResponse>, response: retrofit2.Response<UserUpdateResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { setNewUser(it.user) }

                    popup.setup("Mis à jour !",
                        "Profil mis à jour",
                        "Ok") {
                        popup.dismiss()
                    }
                    popup.show()
                }else {

                    popup.setup("Oops !",
                        "Ancienne mot de passe incompatible",
                        "Ok") {
                        popup.dismiss()
                    }
                    popup.show()
                }
            }

            override fun onFailure(call: Call<UserUpdateResponse>, t: Throwable) {

                popup.setup("Oops !",
                    t.toString(),
                    "Ok") {
                    popup.dismiss()
                }
                popup.show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            this.newFileUri = data.data
            val imageView = findViewById<ImageView>(R.id.imageView8)

            // Set the selected image in the ImageView
            val imageStream: InputStream? = contentResolver.openInputStream(this.newFileUri!!)
            val selectedImageBitmap = BitmapFactory.decodeStream(imageStream)
            imageView.setImageBitmap(selectedImageBitmap)

        }
    }

    private fun setNewUser(userX: UserXX) {
        val userjson = Gson().toJson(userX)
        val sharedPreferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user", userjson)
        editor.apply()
    }
}