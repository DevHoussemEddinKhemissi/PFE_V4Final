package com.tevah.pfe_v4final.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tevah.pfe_v4final.API.PathImages
import com.tevah.pfe_v4final.Models.Shop
import com.tevah.pfe_v4final.Models.ShopWithDistance
import com.tevah.pfe_v4final.R
import com.tevah.pfe_v4final.RestaurantDetailsActivity

class ShopAdapter(private val shopModels: List<ShopWithDistance>) : RecyclerView.Adapter<ShopAdapter.MyViewHolder>() {
    private val TAG = "TasksSample"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val shopModel = shopModels[position]

        //holder.img.setImageResource(shopModel.image)
        // Set the product data to the views

        Picasso
            .get()
            .load(shopModel.shop.getImagePath())
            .transform( RoundedCornersTransformation(16, 0))
            .fit()
            .into(holder.img)
        holder.name.text = shopModel.shop.name
        holder.adresse.text = ""+(shopModel.distance/1000).toString()+" km"
        holder.button.setOnClickListener {
            Log.i(TAG, "onClick: " + shopModel.shop.name)
            val intent = Intent(it.context, RestaurantDetailsActivity::class.java)
            intent.putExtra("shop",shopModel)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return shopModels.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imageView4)
        val name: TextView = itemView.findViewById(R.id.textView2)
        val adresse: TextView = itemView.findViewById(R.id.textView5)
        val button: Button = itemView.findViewById(R.id.button3)
    }
}