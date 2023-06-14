package com.tevah.pfe_v4final

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.tevah.pfe_v4final.SQLDB.Database

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
    private fun deleteAllData() {
        Database(context = this).deleteAllData()
        // Handle any additional logic after deleting all data
    }
    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
                val popup = PopupDisclaimer(this)
                popup.setup("Payment Stripe",
                    "Annulation de la transaction",
                    "Ok") {
                    popup.dismiss()

                }

            }
            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
                val popup = PopupDisclaimer(this)
                popup.setup("Payment Stripe",
                    "votre paiement a échoué ",
                    "Ok") {
                    popup.dismiss()

                }
                popup.show()

            }
            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen

                deleteAllData()
                val popup = PopupDisclaimer(this)
                popup.setup("Payment Stripe",
                    "Votre Paiement est passé avec succès",
                    "Ok") {
                    popup.dismiss()
                    this.finish()
                }
                popup.show()


            }
        }

    }

}
