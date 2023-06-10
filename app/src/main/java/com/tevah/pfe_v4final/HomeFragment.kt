package com.tevah.pfe_v4final

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Adapters.ProduitAdapter
import com.tevah.pfe_v4final.Adapters.ShopAdapter
import com.tevah.pfe_v4final.Models.Produit
import com.tevah.pfe_v4final.Models.Shop
import com.tevah.pfe_v4final.Models.UserRetrieve
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: RecyclerView.Adapter<*>
    private lateinit var recyclerViewCategoryList: RecyclerView
    private lateinit var recyclerViewShopList: RecyclerView
    private lateinit var dataholder1: ArrayList<Produit>
    private lateinit var dataholder2: ArrayList<Shop>
    private lateinit var imageSlider: ImageSlider
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
    private fun getValueFromSharedPreferences(): String? {
        // Retrieve the value using the key
        return sharedPreferences.getString("key", null)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val value = sharedPreferences.getString("Token", "")
        Log.d("RetriveLogin", value.toString())
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        retrofit.GetUSER(value.toString()).enqueue(
            object : retrofit2.Callback<UserRetrieve>{
                override fun onResponse(
                    call: Call<UserRetrieve>,
                    response: Response<UserRetrieve>
                ) {
                    var textview = view.findViewById<TextView>(R.id.textView)
                    textview.setText("Bonjour "+response.body()?.user?.name.toString())
                    Log.d("RetriveLogin", response.body().toString())
                }

                override fun onFailure(call: Call<UserRetrieve>, t: Throwable) {
                    Log.d("RetriveLoginFail", "No connection to server")
                }


            }
        )

        recyclerViewCategoryList = view.findViewById(R.id.recyclerView3)
        recyclerViewCategoryList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        dataholder1 = ArrayList()
        val c1 = Produit(R.drawable.exempleplat, "Poulet Biryani ", "Hôtel Ambrosia  \n & Restaurant")
        val c2 = Produit(R.drawable.exempleplat1, "Sauce Tonkatsu", "Handi-Restaurant, \n Chittagong")
        val c3 = Produit(R.drawable.exempleplat2, "Poulet Katsu", "Hôtel Ambrosia  \n & Restaurant")
        val c4 = Produit(R.drawable.exempleplat, "12km", "12min")
        val c5 = Produit(R.drawable.exempleplat, "50km", "30min")
        dataholder1.add(c1)
        dataholder1.add(c2)
        dataholder1.add(c3)
        dataholder1.add(c4)
        dataholder1.add(c5)
        recyclerViewCategoryList.adapter = ProduitAdapter(dataholder1)

        recyclerViewShopList = view.findViewById(R.id.recyclerView1)
        recyclerViewShopList.layoutManager = LinearLayoutManager(context)
        dataholder2 = ArrayList()
        val s1 = Shop(R.drawable.exmepleresto, "Ambroisie Hôtel & Restaurant", "kazi Deiry, col du \n Taiger Chittagong")
        val s2 = Shop(R.drawable.exmepleresto1, "Restaurant Tava", " Surson Rue, \n Chittagong")
        val s3 = Shop(R.drawable.exmepleresto, "10km", "10min")
        val s4 = Shop(R.drawable.exmepleresto, "12km", "12min")
        val s5 = Shop(R.drawable.exmepleresto, "50km", "30min")
        dataholder2.add(s1)
        dataholder2.add(s2)
        dataholder2.add(s3)
        dataholder2.add(s4)
        dataholder2.add(s5)
        recyclerViewShopList.adapter = ShopAdapter(dataholder2)

        imageSlider = view.findViewById(R.id.image_slider)
        val imagelist = ArrayList<SlideModel>()
        imagelist.add(SlideModel(imagePath = R.drawable.testpub2, scaleType = null))
        imagelist.add(SlideModel(imagePath = R.drawable.testpub1, scaleType = null))
        imageSlider.setImageList(imagelist)

        return view
    }

    companion object {


        fun newInstance(param1: String, param2: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}