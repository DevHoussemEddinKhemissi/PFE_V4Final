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
import android.widget.ImageView
import android.widget.TextView
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.tevah.pfe_v4final.Adapters.RoundedTransformation
import com.tevah.pfe_v4final.Models.UserX

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AccountFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var sharedPref: SharedPreferences
    private var initfrom = "tevah"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()

        val gson = Gson()
        val sharedPreferences = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val user = sharedPreferences.getString("user", null)?.let {
            val parsedUser = gson.fromJson(it, UserX::class.java)

            val imageProfile = view?.findViewById<ImageView>(R.id.profileImage)
            Picasso.get()
                .load(parsedUser.getImagePath())
                .transform(RoundedTransformation())
                .into(imageProfile)
            val profileName = view?.findViewById<TextView>(R.id.nameTextView)
            val email = view?.findViewById<TextView>(R.id.emailTextView)
            profileName?.text = parsedUser.name
            email?.text = parsedUser.email
            this.initfrom = parsedUser.initiatefrom
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        val boutonLogout = view.findViewById<TextView>(R.id.textView42)

        boutonLogout.setOnClickListener {
            val databaseName = "Tevah.db"
            val deleted = requireActivity().deleteDatabase(databaseName)
            if (deleted) {
                Log.d("Dropped Database ", "Database dropped ")
            } else {
                Log.d("Failed to drop ", "Database dropped ")
            }
            startLogout()
        }

        val boutonUpdate = view.findViewById<TextView>(R.id.textView39)
        boutonUpdate.setOnClickListener {
            if (initfrom == "tevah") {
                val intent = Intent(requireContext(), EditprofilmtActivity()::class.java)
                startActivity(intent)
            } else {
                /*val popup = PopupDisclaimer(requireContext())
                popup.setup("Non modifiable!",
                    "Ce compte est lié un outil externe, est non modifiable",
                    "Ok") {
                    popup.dismiss()
                }
                popup.show()*/
                val popup = PopupDisclaimer(requireContext())
                popup.setup(
                    "Enregistrement échoué",
                    "",
                    "Ok"
                ) {
                    popup.dismiss()
                }
            }
        }

        val boutonOrders = view.findViewById<TextView>(R.id.textView40)
        boutonOrders.setOnClickListener {
            val intent = Intent(requireContext(), Orders::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun startLogout() {
        LoginManager.getInstance().logOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val gsc = GoogleSignIn.getClient(requireContext(), gso)
        gsc.signOut().addOnCompleteListener {}
        finishLogout()
    }

    private fun finishLogout() {
        sharedPref = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().remove("Token").apply()
        sharedPref.edit().remove("user").apply()

        val intent = Intent(requireContext(), SplashScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
