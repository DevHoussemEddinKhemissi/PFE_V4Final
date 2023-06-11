package com.tevah.pfe_v4final

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tevah.pfe_v4final.Adapters.CardAdapter
import com.tevah.pfe_v4final.Models.Card
import com.tevah.pfe_v4final.Models.WishlistItem
import com.tevah.pfe_v4final.SQLDB.Database
import com.tevah.pfe_v4final.SQLDB.DatabaseContract


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
    private lateinit var database: Database
    private lateinit var recyclerViewCardList: RecyclerView
    private lateinit var dataholder3: ArrayList<Card>
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

        database = Database(requireContext())

        database = Database(requireContext())

        val wishlistDataList: ArrayList<WishlistItem> = ArrayList()

        val wishlistData = getAllWishlistData()
        if (wishlistData.isNotEmpty()) {
            // Wishlist table is not empty, populate the ArrayList
            wishlistDataList.addAll(wishlistData)

            // Access the wishlistDataList to retrieve the retrieved data
            for (item in wishlistDataList) {
                // Do something with each WishlistItem in the list
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

        for (wishlistItem in wishlistDataList) {
           val card = Card(wishlistItem.image, wishlistItem.name, wishlistItem.stock, wishlistItem.price)
            dataholder3.add(card)
        }

        recyclerViewCardList.adapter = CardAdapter(dataholder3)

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

        while (cursor.moveToNext()) {
            val itemId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_ID))
            val itemName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_NAME))
            val itemImage = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_IMAGE))
            val itemStock = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_STOCK))
            val itemValid = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_VALID))
            val itemPrice = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.WishlistEntry.COLUMN_PRIX))

            val wishlistItem = WishlistItem(itemId, itemName, itemImage, itemStock, itemValid, itemPrice)
            wishlistData.add(wishlistItem)
        }

        cursor.close()

        return wishlistData
    }
}