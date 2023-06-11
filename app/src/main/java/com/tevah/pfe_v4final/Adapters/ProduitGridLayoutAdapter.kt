package com.tevah.pfe_v4final.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.tevah.pfe_v4final.DetailProduit
import com.tevah.pfe_v4final.Models.Produit
import com.tevah.pfe_v4final.R
import com.tevah.pfe_v4final.RestaurantDetailsActivity

class ProduitGridLayoutAdapter (private val context: Context, var productList: List<Produit>) : BaseAdapter() {

    override fun getCount(): Int {
        return productList.size
    }

    override fun getItem(position: Int): Any {
        return productList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
            holder = ViewHolder()
            holder.productImage = view.findViewById(R.id.productImage)
            holder.productTitle = view.findViewById(R.id.productTitle)
            holder.productPrice = view.findViewById(R.id.productPrice)
            holder.viewproduit = view.findViewById(R.id.produit_click_id)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val product = productList[position]

        // Set the product data to the views
        holder.productImage.setImageResource(R.drawable.exempleplat)
        holder.productTitle.text = product.name
        holder.productPrice.text = product.prix
        holder.viewproduit.setOnClickListener{
            val produitid = product.id
            val intent = Intent(it.context, DetailProduit::class.java)

            intent.putExtra("key", product.name)

            it.context.startActivity(intent)
        }

        return view
    }

    fun updateList(filteredProducts: List<Produit>) {
        productList = filteredProducts
        notifyDataSetChanged()
    }

    private class ViewHolder {
        lateinit var productImage: ImageView
        lateinit var productTitle: TextView
        lateinit var productPrice: TextView
        lateinit var viewproduit : View
       // val viewproduit:View = itemView.findViewById(R.id.produit_click_id)
    }
}