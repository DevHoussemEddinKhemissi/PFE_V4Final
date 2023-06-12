package com.tevah.pfe_v4final


import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit

import com.tevah.pfe_v4final.Models.*
import com.tevah.pfe_v4final.SQLDB.Database
import com.tevah.pfe_v4final.SQLDB.DatabaseContract

import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response


class DetailProduit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produit)
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        var stock: Product? = null
        val nametext = intent.getStringExtra("key")

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
                        val product: Product? =
                            responceBody.product.get(0) // Assuming there is only one product in the list
                        if (product != null) {
                            stock = product
                            // Use the stock value as needed

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
        // You can access the stock value outside the callback function
        if (stock != null) {
            // Use the stock value here
            Log.d("reponce", "onResponse: "+ stock!!.image)
            var textView = findViewById<TextView>(R.id.tvDishName)
            textView.setText("Plat:"+ stock!!.name)
            var textView1 = findViewById<TextView>(R.id.tvDishDescription)
            textView1.setText("Description :"+ stock!!.description)
            var textView2 = findViewById<TextView>(R.id.tvDishPrice)
            textView2.setText("Prix :"+ stock!!.prix)
            var textView3 = findViewById<TextView>(R.id.tvStockQuantity)
            textView3.setText("quantité :"+stock.toString())
            // ivDishImage
            val profileImage1 = findViewById<ImageView>(R.id.ivDishImage)
            Picasso
                .get()
                .load(stock!!.image)
                .into(profileImage1)
        }

       // Log.d("putextra", nametext.toString())
        // Initialize the DatabaseHelper
        //val database= Database(this)

        // Sample ArrayList
      /*  val productList = arrayListOf<Produit>()
        productList.add(Produit("Product 1", "image1.jpg", "9.99"))*/


        // Button click listener
        val addButton: Button = findViewById(R.id.AjouterWishlist)
        addButton.setOnClickListener {
            val imageinsert = stock?.image.toString()
            val stockinsert = stock?.stock?.toInt()
            val validityinsert = stock?.valid
            val prixinsert= stock?.prix.toString()
            val database = Database(this)

            val db = database.writableDatabase

            val productName = stock?.name.toString()


            val selection = "${DatabaseContract.WishlistEntry.COLUMN_NAME} = ?"
            val selectionArgs = arrayOf(productName)
            val cursor = db.query(
                DatabaseContract.WishlistEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            if (cursor.count > 0) {

                Toast.makeText(this, "Product name already exists", Toast.LENGTH_SHORT).show()
            } else {

                val contentValues = ContentValues().apply {
                    put(DatabaseContract.WishlistEntry.COLUMN_NAME, productName)
                    put(DatabaseContract.WishlistEntry.COLUMN_IMAGE, imageinsert)
                    put(DatabaseContract.WishlistEntry.COLUMN_STOCK, stockinsert)
                    put(DatabaseContract.WishlistEntry.COLUMN_VALID, validityinsert )
                    put(DatabaseContract.WishlistEntry.COLUMN_PRIX, prixinsert)
                }

                val newRowId = db.insert(DatabaseContract.WishlistEntry.TABLE_NAME, null, contentValues)

                if (newRowId != -1L) {

                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show()
                }
            }

            cursor.close()
        }
    }}


