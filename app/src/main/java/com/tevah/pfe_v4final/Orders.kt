package com.tevah.pfe_v4final

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Adapters.OrdersAdapater
import com.tevah.pfe_v4final.Adapters.ProduitAdapter
import com.tevah.pfe_v4final.Models.FetchAllProductResponse
import com.tevah.pfe_v4final.Models.ListOrderResponce
import com.tevah.pfe_v4final.Models.OrderX
import retrofit2.Call
import retrofit2.Response

class Orders : AppCompatActivity() {

    private lateinit var orders: MutableList<OrderX>
    private lateinit  var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        recyclerView = findViewById(R.id.listOrder)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        orders = ArrayList()
        recyclerView.adapter = OrdersAdapater(orders)
        fetchAllOrders()
    }

    private fun fetchAllOrders(){
        var sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("Token", "")!!
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        retrofit.getAllOrders(token).enqueue(
            object : retrofit2.Callback<ListOrderResponce>{
                override fun onResponse(
                    call: Call<ListOrderResponce>,
                    response: Response<ListOrderResponce>
                ) {
                    response.body()?.let {
                        orders.clear()
                        orders.addAll(it.orders)
                        recyclerView.adapter?.notifyDataSetChanged()

                    }

                }

                override fun onFailure(call: Call<ListOrderResponce>, t: Throwable) {
                }
            }
        )
    }
}