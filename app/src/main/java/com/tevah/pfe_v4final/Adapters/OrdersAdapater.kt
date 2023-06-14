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
import java.text.SimpleDateFormat
import java.util.*


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

    fun formatDateTime(dateString: String): String {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(dateString)
        return format.format(date)
    }
    override fun getItemCount(): Int {
        return orders.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateAchat: TextView = itemView.findViewById(R.id.textView10)
        private val prixtotal: TextView = itemView.findViewById(R.id.textView11)
        private val statu: TextView = itemView.findViewById(R.id.textView8)

        private val nomproduit: TextView = itemView.findViewById(R.id.textView12)
        fun bind(model: OrderX) {
            val prix = model.product.prix.toDouble() * model.quantity
            prixtotal.text = "$prix €"
            val date = formatDateTime(model.createdAt)
            dateAchat.text = date.toString()
            nomproduit.text=model.product.name


            println(date)
            Log.d("TAGiii", "bind: "+model.createdAt)
            if (model.payee){
                statu.text = "Payée"
            }else {
                statu.text = "Annulée"
            }
        }
    }
}