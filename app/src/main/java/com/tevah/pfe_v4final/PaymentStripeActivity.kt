package com.tevah.pfe_v4final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Models.OrderResponce
import com.tevah.pfe_v4final.Models.OrdreSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class PaymentStripeActivity : AppCompatActivity() {
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        setContentView(R.layout.activity_payment_stripe)
        val button1 =findViewById<Button>(R.id.button8)
        button1.setOnClickListener{
            paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret,
                PaymentSheet.Configuration("Code Eseay"))
        }
        val button = findViewById<Button>(R.id.button5)
        button.setOnClickListener {
            val order = OrdreSet("",1,"sahem")// Create a new instance of the Project object with the required data

            val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)


            val call = retrofit.createPayment(order)
            call.enqueue(object : Callback<OrderResponce> {
                override fun onResponse(call: Call<OrderResponce>, response: Response<OrderResponce>) {
                    if (response.isSuccessful) {

                        Log.d("stripePublishableKey", "onResponse: "+ response.body()?.stripePublishableKey.toString())
                        Log.d("paymentIntentClientSecret", "onResponse: "+ response.body()?.paymentIntentClientSecret.toString())
                        customerConfig = PaymentSheet.CustomerConfiguration(
                            "billing name Robert jackson",
                            response.body()?.stripePublishableKey.toString()
                        )
                        paymentIntentClientSecret = response.body()?.paymentIntentClientSecret.toString()

                        val publishableKey = response.body()?.stripePublishableKey.toString()
                        PaymentConfiguration.init(context = this@PaymentStripeActivity, publishableKey)


                    } else {
                        Log.d("SERVER Problem", "onResponse: no")
                    }
                }

                override fun onFailure(call: Call<OrderResponce>, t: Throwable) {
                    Log.d("Network Problem", "onFailure: $t")
                }
            })


        }


    }
    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        // implemented in the next steps
        Log.d("test resultat", "onPaymentSheetResult: "+paymentSheetResult.toString())
    }
}