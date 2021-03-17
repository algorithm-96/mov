package com.rinaldialdi.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.home.HomeActivity
import com.rinaldialdi.bwamov.model.Film
import com.rinaldialdi.bwamov.model.Plays
import com.rinaldialdi.bwamov.tiket.TiketActivity
import com.rinaldialdi.bwamov.tiket.TiketFragment
import com.rinaldialdi.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout_success.*
import kotlinx.android.synthetic.main.activity_tiket.*

class CheckoutSuccessActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        val data = intent.getParcelableExtra<Film>("data")

        btn_home.setOnClickListener {
            finishAffinity()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }



        btn_tiket.setOnClickListener {
            val goTiket = Intent(this, TiketActivity::class.java).putExtra("data", data)
            startActivity(goTiket)
        }

    }
}