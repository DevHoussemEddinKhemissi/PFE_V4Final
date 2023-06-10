package com.tevah.pfe_v4final

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.maps.model.Circle
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.tevah.pfe_v4final.API.RetrofitAPIInterface
import com.tevah.pfe_v4final.API.ServiceBuilderRetrofit
import com.tevah.pfe_v4final.Adapters.ProduitAdapter
import com.tevah.pfe_v4final.Adapters.ShopAdapter
import com.tevah.pfe_v4final.Models.*
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
                    val profileImage = view.findViewById<ImageView>(R.id.profileImageViewHome)
                    Picasso
                        .get()
                        .load(response.body()?.user?.image)
                        .transform(RoundedTransformation(50F))
                        .into(profileImage);
                }

                override fun onFailure(call: Call<UserRetrieve>, t: Throwable) {
                    Log.d("RetriveLoginFail", "No connection to server")
                }


            }
        )

        recyclerViewCategoryList = view.findViewById(R.id.recyclerView3)
        recyclerViewCategoryList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        dataholder1 = ArrayList()

        recyclerViewCategoryList.adapter = ProduitAdapter(dataholder1)

        recyclerViewShopList = view.findViewById(R.id.recyclerView1)
        recyclerViewShopList.layoutManager = LinearLayoutManager(context)
        dataholder2 = ArrayList()
        recyclerViewShopList.adapter = ShopAdapter(dataholder2)

        imageSlider = view.findViewById(R.id.image_slider)
        val imagelist = ArrayList<SlideModel>()
        imagelist.add(SlideModel(imagePath = R.drawable.testpub2, scaleType = null))
        imagelist.add(SlideModel(imagePath = R.drawable.testpub1, scaleType = null))
        imageSlider.setImageList(imagelist)
        fetchAllProducts()
        fetchAllShops()
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

    private fun fetchAllProducts(){
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        retrofit.fetchAllProducts().enqueue(
            object : retrofit2.Callback<FetchAllProductResponse>{
                override fun onResponse(
                    call: Call<FetchAllProductResponse>,
                    response: Response<FetchAllProductResponse>
                ) {
                    response.body()?.let {
                        dataholder1.clear(); // Assuming myDataList is the existing data source
                        dataholder1.addAll(it.product);
                        recyclerViewCategoryList.adapter?.notifyDataSetChanged()
                    }

                }

                override fun onFailure(call: Call<FetchAllProductResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed to load products", Toast.LENGTH_SHORT).show()
                }


            }
        )
    }

    private fun fetchAllShops(){
        val retrofit = ServiceBuilderRetrofit.buildService(RetrofitAPIInterface::class.java)
        retrofit.fetchAllShops().enqueue(
            object : retrofit2.Callback<FetchAllShopsResponse>{
                override fun onResponse(
                    call: Call<FetchAllShopsResponse>,
                    response: Response<FetchAllShopsResponse>
                ) {
                    response.body()?.let {
                        dataholder2.clear(); // Assuming myDataList is the existing data source
                        dataholder2.addAll(it.shop);
                        recyclerViewShopList.adapter?.notifyDataSetChanged()
                    }

                }

                override fun onFailure(call: Call<FetchAllShopsResponse>, t: Throwable) {

                    Toast.makeText(context, "Failed to load products",Toast.LENGTH_SHORT).show()
                }


            }
        )
    }
}

class RoundedTransformation(private val radius: Float) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val paint = Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }

        val canvas = Canvas(output)
        canvas.drawRoundRect(
            RectF(0F, 0F, width.toFloat(), height.toFloat()),
            radius,
            radius,
            paint
        )

        if (source != output) {
            source.recycle()
        }

        return output
    }

    override fun key(): String {
        return "rounded(radius=$radius)"
    }
}