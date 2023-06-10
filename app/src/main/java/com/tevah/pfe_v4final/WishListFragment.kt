package com.tevah.pfe_v4final

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tevah.pfe_v4final.Adapters.CardAdapter
import com.tevah.pfe_v4final.Models.Card

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

        recyclerViewCardList = view.findViewById(R.id.recyclerView3)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewCardList.layoutManager = layoutManager

        dataholder3 = ArrayList()
        val s1 = Card(R.drawable.testfood1, "Crabe frais spacieux", "Kita Waroenk", "€ 65")
        val s2 = Card(R.drawable.testfood2, "Les Penne Rigate", "Italiennes", "€ 35")
        val s3 = Card(R.drawable.testfood3, "Sandwich Libanais", "libanaise", "€ 20")

        dataholder3.add(s1)
        dataholder3.add(s2)
        dataholder3.add(s3)

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
}