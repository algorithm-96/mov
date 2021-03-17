package com.rinaldialdi.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.model.Checkout
import com.rinaldialdi.bwamov.model.Film
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        var statusA1: Boolean = false
        var statusA2: Boolean = false
        var statusA3: Boolean = false
        var statusA4: Boolean = false
        var total: Int = 0

        val dataList = ArrayList<Checkout>()

        val data = intent.extras?.getParcelable<Film>("film")
        tv_judul.text = data!!.judul

        a1.setOnClickListener {
            if (statusA1){
                a1.setImageResource(R.drawable.ic_rectangle_empty)
                statusA1 = false
                total -= 1
                beliTiket(total)
            } else{
                a1.setImageResource(R.drawable.ic_rectangle_selected)
                statusA1 = true
                total += 1
                beliTiket(total)

                val dataCheckout = Checkout ("A1", "70000")
                dataList.add(dataCheckout)
            }
        }

        a2.setOnClickListener {
            if (statusA2){
                a2.setImageResource(R.drawable.ic_rectangle_empty)
                statusA2 = false
                total -= 1
                beliTiket(total)
            } else{
                a2.setImageResource(R.drawable.ic_rectangle_selected)
                statusA2 = true
                total += 1
                beliTiket(total)

                val dataCheckout = Checkout ("A2", "70000")
                dataList.add(dataCheckout)
            }
        }

        btn_lanjut.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("data", dataList)
            intent.putExtra("film", data)
            startActivity(intent)
        }

    }

    private fun beliTiket(total: Int) {
        if (total == 0){
            btn_lanjut.setText("Beli Tiket")
            btn_lanjut.visibility = View.INVISIBLE
        } else {

            btn_lanjut.setText("Beli Tiket($total)")
            btn_lanjut.visibility =View.VISIBLE
        }

    }
}