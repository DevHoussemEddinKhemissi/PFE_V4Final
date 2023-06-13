package com.tevah.pfe_v4final

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Models.OrderResponce
import com.tevah.pfe_v4final.Models.OrdreSet
import com.tevah.pfe_v4final.Models.ProductIDQuantity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class PaymentStripeActivity : AppCompatActivity() {
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val receivedString1 = intent.getStringExtra("customerConfig")
        val receivedString2 = intent.getStringExtra("paymentIntentClientSecret")
        val receivedString3 = intent.getStringExtra("publishableKey")

        customerConfig = PaymentSheet.CustomerConfiguration(
            "billing name Robert jackson", receivedString1.toString()
        )
        paymentIntentClientSecret = receivedString2.toString()

        val publishableKey = receivedString3.toString()

        PaymentConfiguration.init(context = this, publishableKey)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        setContentView(R.layout.activity_payment_stripe)

        val button1 =findViewById<Button>(R.id.button8)
        button1.setOnClickListener{
            paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret,
                PaymentSheet.Configuration("Code Eseay"))
        }




    }
    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
                Toast.makeText(baseContext, "Canceled", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
                Toast.makeText(baseContext, "Error"+paymentSheetResult, Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen
                Toast.makeText(baseContext, "Completed", Toast.LENGTH_SHORT).show()

            }
        }

    }
}