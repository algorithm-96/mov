package com.rinaldialdi.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.model.Checkout
import com.rinaldialdi.bwamov.model.Film
import com.rinaldialdi.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    private var total : Int = 0
    private var dataList : ArrayList<Checkout>? = null
    private var dataFilm : Film? = null
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)

        dataList    = intent.extras?.getParcelableArrayList("data")
        dataFilm    = intent.extras?.getParcelable("film")


        for (a in dataList!!.indices){
            total += dataList!![a].harga!!.toInt()
        }

        dataList!!.add(Checkout("Total yang harus dibayar", total.toString()))

        rv_checkout.layoutManager = LinearLayoutManager(this)
        rv_checkout.adapter = CheckoutAdapter(dataList!!){

        }

        btn_bayar.setOnClickListener {
            val intent = Intent(this, CheckoutSuccessActivity::class.java)
            intent.putExtra("data", dataList)
            intent.putExtra("data", dataFilm)
            startActivity(intent)
        }

    }
}