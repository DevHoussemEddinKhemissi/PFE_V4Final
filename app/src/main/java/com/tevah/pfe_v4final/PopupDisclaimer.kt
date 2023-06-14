package com.tevah.pfe_v4final

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.tevah.pfe_v4final.Models.UserRegisterResponce
import com.tevah.pfe_v4final.Models.UserUpdateResponse
import retrofit2.Callback

class PopupDisclaimer(context: Context) : Dialog(context) {
    constructor(fragment: Fragment) : this(fragment.requireContext())
    fun setup(
        title: String = "Hello",
        contentText: String = "Content description",
        buttonText: String = "Accept",
        listener: () -> Unit
    ) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_disclaimer)
        val disclaimerTitle = findViewById<TextView>(R.id.disclaimer_title_text)
        disclaimerTitle.text = title
        val disclaimerContent = findViewById<TextView>(R.id.disclaimer_content_text)
        disclaimerContent.text = contentText
        val acceptButton = findViewById<Button>(R.id.accept_button)
        acceptButton.text = buttonText
        acceptButton.setOnClickListener {
            // Perform actions when the user accepts the disclaimer.
            listener()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
