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
import com.tevah.pfe_v4final.Models.Card
import com.tevah.pfe_v4final.R
import com.tevah.pfe_v4final.RestaurantDetailsActivity

class CardAdapter (private val dataholder3: ArrayList<Card>) : RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_row_disgn, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cardModel = dataholder3[position]
        holder.img.setImageResource(cardModel.image)
        holder.name.text = cardModel.name
        holder.speciality.text = cardModel.speciality
        holder.price.text = cardModel.price
        holder.buttonminus.setOnClickListener{

            holder.quantity.setText("2")
        }
        holder.buttonplus.setOnClickListener{

            val intent = Intent(it.context, RestaurantDetailsActivity::class.java)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataholder3.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imagecar1)
        val name: TextView = itemView.findViewById(R.id.name1)
        val speciality: TextView = itemView.findViewById(R.id.sepeciality1)
        val price: TextView = itemView.findViewById(R.id.price1)
        val buttonplus: TextView = itemView.findViewById(R.id.button4)
        val buttonminus: TextView = itemView.findViewById(R.id.button2)
        val quantity:TextView = itemView.findViewById(R.id.textView9)

    }
}