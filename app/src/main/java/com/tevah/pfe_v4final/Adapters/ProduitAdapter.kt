package com.tevah.pfe_v4final.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tevah.pfe_v4final.API.PathImages
import com.tevah.pfe_v4final.DetailProduit
import com.tevah.pfe_v4final.Models.Produit
import com.tevah.pfe_v4final.R

class ProduitAdapter (private val produit: List<Produit>) :
    RecyclerView.Adapter<ProduitAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.produit_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = produit[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return produit.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.categoryimage)
        private val distance: TextView = itemView.findViewById(R.id.distance)
        private val time: TextView = itemView.findViewById(R.id.time)
        private val vu: View = itemView.findViewById(R.id.simon)
        fun bind(model: Produit) {

            val myPath = PathImages.STATIC_PATH
            val imagePath = model.getProductImageUrl()
            Log.d("tata", "bind: "+imagePath)

            Picasso
                .get()
                .load(myPath+imagePath)
                .transform( RoundedCornersTransformation(16, 0))
                .fit()
                .into(img)


            distance.text = model.name
            time.text = model.shop.name
            vu.setOnClickListener{
                val intent = Intent(it.context, DetailProduit::class.java)
                Log.d("tg", "getView: "+model.name)
                intent.putExtra("keyss", model.name)

                it.context.startActivity(intent)
            }



        }
    }
}