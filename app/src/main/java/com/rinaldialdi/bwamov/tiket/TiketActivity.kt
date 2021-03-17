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

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        val data = intent.getParcelableExtra<Film>("data")

        tv_title.text = data?.judul
        tv_genre.text = data?.genre
        tv_rate.text  = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(iv_poster)

        rv_checkout.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("C1",""))
        dataList.add(Checkout("C2",""))
        dataList.add(Checkout("C3",""))
        dataList.add(Checkout("C4",""))

        rv_checkout.adapter = TiketAdapter(dataList){

        }

        iv_scan.setOnClickListener {

            val dialog = CustomDialogFragment()

            dialog.show(supportFragmentManager, "customDialog")

        }


    }
}