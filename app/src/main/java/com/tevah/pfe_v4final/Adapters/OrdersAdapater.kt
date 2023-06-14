package com.tevah.pfe_v4final.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tevah.pfe_v4final.API.PathImages
import com.tevah.pfe_v4final.Models.OrderX
import com.tevah.pfe_v4final.Models.Produit
import com.tevah.pfe_v4final.R


class OrdersAdapater (private var orders: List<OrderX>) :
    RecyclerView.Adapter<OrdersAdapater.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.achatcontainer, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = orders[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateAchat: TextView = itemView.findViewById(R.id.textView10)
        private val prixtotal: TextView = itemView.findViewById(R.id.textView11)
        private val statu: TextView = itemView.findViewById(R.id.textView8)

        fun bind(model: OrderX) {
            val prix = model.product.prix.toDouble() * model.quantity
            prixtotal.text = "$prix €"
            dateAchat.text = model.createdAt
            if (model.payee){
                statu.text = "Payée"
            }else {
                statu.text = "Annulée"
            }
        }
    }
}