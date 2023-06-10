package com.tevah.pfe_v4final

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import com.tevah.pfe_v4final.Adapters.ProduitAdapter
import com.tevah.pfe_v4final.Adapters.ProduitGridLayoutAdapter
import com.tevah.pfe_v4final.Models.Produit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductsFragment : Fragment() {
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var productGridView: GridView
    private lateinit var productAdapter: ProduitGridLayoutAdapter
    private  var productList: List<Produit> = ArrayList()

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
        val view = inflater.inflate(R.layout.fragment_products, container, false)

        searchEditText = view.findViewById(R.id.searchEditText)
        searchButton = view.findViewById(R.id.searchButton)
        productGridView = view.findViewById(R.id.productGridView)

        productList = listOf(
            Produit(R.drawable.exempleplat, "Image 1 URL", "$9.99"),
            Produit(R.drawable.exempleplat1, "pizza", "$14.99"),
            Produit(R.drawable.exempleplat2, "sushi", "$12.99"),
            Produit(R.drawable.exempleplat, "Image 2 URL", "$10.00"),
            Produit(R.drawable.exempleplat, "Image 2 URL", "$17.50")

        )

        productAdapter = ProduitGridLayoutAdapter(requireContext(), productList)
        productGridView.adapter = productAdapter

        searchEditText.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                val filteredList = filterProducts(searchText)
                 productAdapter.productList = filteredList
                productAdapter.notifyDataSetChanged()
            }

             override fun afterTextChanged(s: Editable?) {}
        })

        searchButton.setOnClickListener {
            showDisclaimerPopup()
        }

        return view
    }
    private fun filterProducts(searchText: String): List<Produit> {
        val filteredList: MutableList<Produit> = ArrayList()

        for (product in productList) {
            if (product.distance.contains(searchText, true)) {
                filteredList.add(product)
            }
        }

        return filteredList
    }
    private fun showDisclaimerPopup() {
        val popup = PopupDisclaimer(requireContext())
        popup.setup("Halo",
            "This is a test text multiline This is a test text multiline This is a test text multiline This is a test text multiline This is a test text multiline This is a test text multiline This is a test text multiline ",
            "Done") {
            //...Example
            Toast.makeText(this.context, "clicked on accept", Toast.LENGTH_SHORT).show()

            popup.dismiss()
        }
        popup.show()
    }

    companion object {


        @JvmStatic
        fun newInstance(param1: String, param2: String): ProductsFragment {
            val fragment = ProductsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


}
