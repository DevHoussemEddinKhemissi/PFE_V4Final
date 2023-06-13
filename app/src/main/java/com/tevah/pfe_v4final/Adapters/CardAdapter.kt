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
import com.tevah.pfe_v4final.Models.Card
import com.tevah.pfe_v4final.R


class CardAdapter(private val dataholder3: ArrayList<Card>) :
    RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.testrow2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cardModel = dataholder3[position]
        val myPath = PathImages.STATIC_PATH
        Log.d("testproduit", "onBindViewHolder: "+cardModel.image)
        Picasso
            .get()
            .load(myPath+cardModel.image)
            .transform( RoundedCornersTransformation(16, 0))
            .fit()
            .into(holder.img)
        holder.name.text = cardModel.name
        holder.speciality.text = cardModel.stock.toString()
        holder.price.text = cardModel.price.toString()
        holder.quantity.setText("1")
        holder.buttonminus.setOnClickListener {
            val text = holder.quantity.text.toString()
            var intValue = text.toInt()

            if (intValue > 1) {
                intValue -= 1
            }

            intValue = minOf(intValue, cardModel.stock)

            holder.quantity.text = intValue.toString()

            // Update the quantity value in the Card object
            cardModel.quantity = intValue
        }
        holder.buttonplus.setOnClickListener {
            val text = holder.quantity.text.toString()
            var intValue = text.toInt()

            if (intValue < cardModel.stock) {
                intValue += 1
            } else {
                intValue = cardModel.stock
            }

            intValue = maxOf(intValue, 1)

            holder.quantity.text = intValue.toString()

            // Update the quantity value in the Card object
            cardModel.quantity = intValue
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