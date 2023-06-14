package com.tevah.pfe_v4final

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Adapters.ProduitGridLayoutAdapter
import com.tevah.pfe_v4final.Adapters.RoundedCornersTransformation
import com.tevah.pfe_v4final.Adapters.RoundedTransformation
import com.tevah.pfe_v4final.Models.FetchAllProductResponse
import com.tevah.pfe_v4final.Models.Produit
import com.tevah.pfe_v4final.Models.ShopProductsResponse
import com.tevah.pfe_v4final.Models.ShopWithDistance
import retrofit2.Call
import retrofit2.Response

class RestaurantDetailsActivity : AppCompatActivity() {
    private lateinit var productGridView: GridView
    private lateinit var productAdapter: ProduitGridLayoutAdapter
    private  var productList: List<Produit> = ArrayList()
    private lateinit var mDialog: ProgressDialog
    private lateinit var showWithDistance: ShopWithDistance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)
        // searchButton = view.findViewById(R.id.searchButton)
        productGridView = findViewById(R.id.productGridView)
        productAdapter = ProduitGridLayoutAdapter(this, productList)
        productGridView.adapter = productAdapter
        showWithDistance = (intent.getSerializableExtra("shop") as? ShopWithDistance)!!
        showProgress()
        fetchAllProducts()

        val title = findViewById<TextView>(R.id.textView29)
        title.text = showWithDistance.shop.name
        val address = findViewById<TextView>(R.id.textView30)
        address.text = showWithDistance.shop.adress
        val shopImage = findViewById<ImageView>(R.id.imageView5)
        Picasso
            .get()
            .load(showWithDistance.shop.getImagePath())
            .transform( RoundedCornersTransformation(16, 0))
            .fit()

            .into(shopImage)
        val distanceText = findViewById<TextView>(R.id.textView34)
        distanceText.text = ""+(showWithDistance.distance/1000).toString()+" km"

    }

    private fun showProgress(){
        mDialog = ProgressDialog(this)
        mDialog.setMessage("Please wait...")
        mDialog.setCancelable(false)
        mDialog.show()
    }

    private fun fetchAllProducts(){
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        retrofit.getShopProducts(showWithDistance.shop.id).enqueue(
            object : retrofit2.Callback<ShopProductsResponse>{
                override fun onResponse(
                    call: Call<ShopProductsResponse>,
                    response: Response<ShopProductsResponse>
                ) {

                    mDialog.hide()
                    response.body()?.let {
                        productList = it.shop.products
                        productAdapter.updateList(productList)
                    }

                }

                override fun onFailure(call: Call<ShopProductsResponse>, t: Throwable) {
                    mDialog.hide()
                }
            }
        )
    }
}