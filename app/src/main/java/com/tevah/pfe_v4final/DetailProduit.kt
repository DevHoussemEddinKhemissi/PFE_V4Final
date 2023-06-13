package com.tevah.pfe_v4final


import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.tevah.pfe_v4final.API.PathImages

import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Adapters.RoundedCornersTransformation

import com.tevah.pfe_v4final.Models.*
import com.tevah.pfe_v4final.SQLDB.Database
import com.tevah.pfe_v4final.SQLDB.DatabaseContract

import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response


class DetailProduit : AppCompatActivity() {
    val myPath = PathImages.STATIC_PATH
    var sto: List<ProductX>? = null
    var stoItem: ProductX? = null
    var shop: ShopXX? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produit)
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)

        val nametext = intent.getStringExtra("keyss")

        Log.d("test stock", "onCreate: "+nametext)
        val obj = FinAllDataOnProduct(
            name = nametext.toString(),

            )
        Log.d("test obj", "onCreate: "+obj.toString())

        val call = retrofit.tallDataGrip(obj)
        call.enqueue(object : Callback<AllDataOnProduct> {
            override fun onResponse(call: Call<AllDataOnProduct>, response: Response<AllDataOnProduct>) {
                Log.d("reponzi", "onResponse: "+response)
                if (response.isSuccessful) {
                    val responceBody: AllDataOnProduct? = response.body()
                    if (responceBody != null) {
                        val product: List<ProductX> =
                            responceBody.product
                        sto = product
                        stoItem = sto!![0]
                        shop = stoItem!!.shop
                        // Use the stock value here
                        Log.d("reponcesi", "onResponse: "+ stoItem!!.image)
                        var textView = findViewById<TextView>(R.id.tvDishName)
                        textView.setText("Plat:"+ stoItem!!.name)
                        var textView1 = findViewById<TextView>(R.id.tvDishDescription)
                        textView1.text = "Line 1\nLine 2\nLine 3"
                        textView1.setText("Description : "+ stoItem!!.description)
                        var textView2 = findViewById<TextView>(R.id.tvDishPrice)
                        textView2.setText("Prix :"+ stoItem!!.prix)
                        var textView3 = findViewById<TextView>(R.id.tvStockQuantity)
                        textView3.setText("quantité :"+ stoItem!!.stock.toString())
                        // ivDishImage
                        /*  val profileImage1 = findViewById<ImageView>(R.id.ivDishImage)
                        Picasso
                            .get()
                            .load(stock!!.image)
                            .into(profileImage1)*/
                        val profileImage1 = findViewById<ImageView>(R.id.ivDishImage)
                        Picasso
                            .get()
                            .load(myPath+stoItem!!.image)
                            .transform( RoundedCornersTransformation(16, 0))
                            .fit()
                            .into(profileImage1)

                        var textView7 = findViewById<TextView>(R.id.tvRestaurantName)
                        var textView8 = findViewById<TextView>(R.id.tvRestaurantDistance)
                        textView7.setText( shop!!.name)
                        textView8.setText( shop!!.adress)
                        val profileImage2 = findViewById<ImageView>(R.id.ivRestaurantImage)
                        Picasso
                            .get()
                            .load(myPath+shop!!.image)
                            .transform( RoundedCornersTransformation(16, 0))
                            .fit()
                            .into(profileImage2)





                        Log.d("erree", "onResponse: "+sto.toString())
                    }
                } else {
                    // Handle the error
                    val errorBody = response.errorBody()?.string()
                    Log.d("erree", "onResponse: "+errorBody.toString())
                }
            }

            override fun onFailure(call: Call<AllDataOnProduct>, t: Throwable) {
                // Handle the network failure
                Log.d("fail", "onResponse: "+t.toString())
            }
        })




        val addButton: Button = findViewById(R.id.AjouterWishlist)
        addButton.setOnClickListener {

            Log.d("teswtf", "onCreate: "+stoItem!!?.image.toString())
            val imageinsert = stoItem!!?.image.toString()
            val stockinsert = stoItem!!?.stock?.toInt()
            val validityinsert = stoItem!!?.valid
            val prixinsert= stoItem!!?.prix.toString()
            val database = Database(this)

            val db = database.writableDatabase

            val productName = stoItem!!?.name.toString()


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


