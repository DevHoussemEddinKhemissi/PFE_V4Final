package com.tevah.pfe_v4final

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.view.CardInputWidget
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Adapters.CardAdapter
import com.tevah.pfe_v4final.Models.*
import com.tevah.pfe_v4final.SQLDB.Database
import com.tevah.pfe_v4final.SQLDB.DatabaseContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WishListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WishListFragment : Fragment() {
    lateinit var sharedPref: SharedPreferences
    private lateinit var database: Database
    private lateinit var recyclerViewCardList: RecyclerView
    private lateinit var dataholder3: ArrayList<Card>
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    private var paymentIntentClientSecret: String? = null
    var totalPrice: Double = 0.0
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wish_list, container, false)
        //val stripe = Stripe(requireContext(), "YOUR_PUBLISHABLE_KEY to complete")

        database = Database(requireContext())

        database = Database(requireContext())

        val wishlistDataList: ArrayList<WishlistItem> = ArrayList()

        val wishlistData = getAllWishlistData()
        if (wishlistData.isNotEmpty()) {

            wishlistDataList.addAll(wishlistData)


            for (item in wishlistDataList) {

                Log.d("WishlistItem", item.toString())



            }
        } else {
            // Wishlist table is empty, handle the scenario accordingly
        }



        recyclerViewCardList = view.findViewById(R.id.recyclerView3)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewCardList.layoutManager = layoutManager

        dataholder3 = ArrayList()


        if (wishlistData.isNotEmpty()) {
            wishlistDataList.addAll(wishlistData)

            for (item in wishlistDataList) {
                val card = Card(item.image, item.name, item.stock, item.price)
                dataholder3.add(card)



            }
        } else {
            Toast.makeText(requireContext(), "Wishlist is empty", Toast.LENGTH_SHORT).show()
        }

        recyclerViewCardList.adapter = CardAdapter(dataholder3)
        val button = view.findViewById<Button>(R.id.button7)
        button.setOnClickListener {


            sharedPref = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)


                val products = listOf(ProductIDQuantity(5,1),ProductIDQuantity(6,1))
                val order = OrdreSet("10.000", products = products)// Create a new instance of the Project object with the required data

                val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)

                val userToken = sharedPref.getString("Token","")
                val call = retrofit.createPayment(userToken!!,order)
                call.enqueue(object : Callback<OrderResponce> {
                    override fun onResponse(call: Call<OrderResponce>, response: Response<OrderResponce>) {
                        if (response.isSuccessful) {

                            Log.d("stripePublishableKey", "onResponse: "+ response.body()?.stripePublishableKey.toString())
                            Log.d("paymentIntentClientSecret", "onResponse: "+ response.body()?.paymentIntentClientSecret.toString())

                            val string1 = response.body()?.stripePublishableKey.toString()
                            val string2 = response.body()?.paymentIntentClientSecret.toString()
                            val string3 = response.body()?.stripePublishableKey.toString()

                            val intenti = Intent(requireContext(), PaymentStripeActivity::class.java).apply {
                                putExtra("customerConfig", string1)
                                putExtra("paymentIntentClientSecret", string2)
                                putExtra("publishableKey", string3)
                            }
                            startActivity(intenti)


                        } else {
                            Log.d("SERVER Problem", "onResponse: no")
                        }
                    }

                    override fun onFailure(call: Call<OrderResponce>, t: Throwable) {
                        Toast.makeText(requireContext(),"Insufussient stock quantity", Toast.LENGTH_LONG)
                    }
                })






        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WishListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WishListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun getAllWishlistData(): List<WishlistItem> {
        val wishlistData = mutableListOf<WishlistItem>()

        val db = database.readableDatabase

        val projection = arrayOf(
            DatabaseContract.WishlistEntry.COLUMN_ID,
            DatabaseContract.WishlistEntry.COLUMN_NAME,
            DatabaseContract.WishlistEntry.COLUMN_IMAGE,
            DatabaseContract.WishlistEntry.COLUMN_STOCK,
            DatabaseContract.WishlistEntry.COLUMN_VALID,
            DatabaseContract.WishlistEntry.COLUMN_PRIX
        )

        val cursor = db.query(
            DatabaseContract.WishlistEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        totalPrice = 0.0
        while (cursor.moveToNext()) {
            val itemId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_ID))
            val itemName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_NAME))
            val itemImage = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_IMAGE))
            val itemStock = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_STOCK))
            val itemValid = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_VALID))
            val itemPrice = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_PRIX))

            val wishlistItem = WishlistItem(itemId, itemName, itemImage, itemStock, itemValid, itemPrice)
            wishlistData.add(wishlistItem)

            totalPrice += itemPrice.toDoubleOrNull() ?: 0.0

        }

        cursor.close()

        Log.d("WishlistItem", totalPrice.toString())
        return wishlistData
    }

}