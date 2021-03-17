package com.rinaldialdi.bwamov.tiket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.model.Checkout
import com.rinaldialdi.bwamov.model.Film
import kotlinx.android.synthetic.main.activity_tiket.*

class TiketActivity : AppCompatActivity() {

    private var dataFilm : Film? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        dataFilm    = intent.extras?.getParcelable("film")

        tv_title.text = dataFilm?.judul
        tv_genre.text = dataFilm?.genre
        tv_rate.text  = dataFilm?.rating

        Glide.with(this)
            .load(dataFilm?.poster)
            .into(iv_poster)
        val dataCheckout          = ArrayList<Checkout>()
        rv_checkout.layoutManager = LinearLayoutManager(this)
        dataCheckout.add(Checkout("C1",""))
        dataCheckout.add(Checkout("C2",""))
        dataCheckout.add(Checkout("C3",""))
        dataCheckout.add(Checkout("C4",""))

        rv_checkout.adapter = TiketAdapter(dataCheckout){

        }

        iv_scan.setOnClickListener {

            val dialog = CustomDialogFragment()

            dialog.show(supportFragmentManager, "customDialog")

        }


    }
}