package com.tevah.pfe_v4final


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit

import com.tevah.pfe_v4final.Models.*
import com.tevah.pfe_v4final.SQLDB.Database

import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response
import kotlin.math.log


class DetailProduit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produit)
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        val nametext = intent.getStringExtra("key")
        val name = "marhouga" // Set the desired name her
        val obj = ProductDetailGet(
            name = nametext.toString(),

            )
        // Create your JSON object with the desired parameters

        val call = retrofit.getData(obj)
        call.enqueue(object : Callback<ProduitDetailsPageResponce> {
            override fun onResponse(call: Call<ProduitDetailsPageResponce>, response: Response<ProduitDetailsPageResponce>) {
                if (response.isSuccessful) {
                    val responceBody: ProduitDetailsPageResponce? = response.body()
                    if (responceBody != null) {
                        val product: Product? = responceBody.product?.get(0) // Assuming there is only one product in the list
                        if (product != null) {
                            val stock = product.stock
                            // Use the stock value as needed
                            Log.d("reponce", "onResponse: "+stock.toString()+product.prix+product.name+product.description)

                        }
                    }
                } else {
                    // Handle the error
                    val errorBody = response.errorBody()?.string()
                    Log.d("err", "onResponse: "+errorBody.toString())
                }
            }

            override fun onFailure(call: Call<ProduitDetailsPageResponce>, t: Throwable) {
                // Handle the network failure
                Log.d("fail", "onResponse: "+t.toString())
            }
        })

       // Log.d("putextra", nametext.toString())
        // Initialize the DatabaseHelper
        //val database= Database(this)

        // Sample ArrayList
      /*  val productList = arrayListOf<Produit>()
        productList.add(Produit("Product 1", "image1.jpg", "9.99"))*/


        // Button click listener
        val addButton: Button = findViewById(R.id.AjouterWishlist)
        addButton.setOnClickListener {
            // Insert data into the database


            /* val db: SQLiteDatabase = database.writableDatabase
             for (product in productList) {
                 val values = ContentValues().apply {
                     put(ProductContract.ProductEntry.COLUMN_NAME, product.name)
                     put(ProductContract.ProductEntry.COLUMN_IMAGE, product.image)
                     put(ProductContract.ProductEntry.COLUMN_PRIX, product.prix)
                 }
                 db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values)
             }
             db.close()*/
        }
    }}


