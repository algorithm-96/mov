package com.rinaldialdi.bwamov.tiket

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.model.Checkout
import java.text.NumberFormat
import java.util.*

class TiketAdapter(private var data : List<Checkout>,
                   private var listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<TiketAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    class ViewHolder ( view: View): RecyclerView.ViewHolder(view) {

    private val tvTitle : TextView = view.findViewById(R.id.tv_kursi)


        @SuppressLint("SetTextI18n")
        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context: Context){

            tvTitle.setText("Seat No. "+data.kursi)


            itemView.setOnClickListener {
                listener(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout_tiket, parent, false)
        return ViewHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

}
