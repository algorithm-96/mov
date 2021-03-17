package com.rinaldialdi.bwamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.rinaldialdi.bwamov.checkout.PilihBangkuActivity
import com.rinaldialdi.bwamov.home.dashboard.PlaysAdapter
import com.rinaldialdi.bwamov.model.Film
import com.rinaldialdi.bwamov.model.Plays
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Plays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)



        //Mengambil data yang sudah dikirimkan lewat dashboard fragment
        val data = intent.getParcelableExtra<Film>("data")


        //Menginisialisasi data dari database
            mDatabase = FirebaseDatabase.getInstance().getReference("Film")
                    .child(data!!.judul.toString())
                    .child("play")

        tv_judul.text = data.judul
        tv_genre.text = data.genre
        tv_desc.text = data.desc
        tv_rate.text = data.rating

        Glide.with(this)
                .load(data.poster)
                .into(iv_poster)

        rv_list_plays.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        btn_pilih_bangku.setOnClickListener {
            val intent = Intent(this@DetailActivity, PilihBangkuActivity::class.java).putExtra("film", data)
            startActivity(intent)
        }

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()

                for (getdataSnapshot in dataSnapshot.children){

                    val film = getdataSnapshot.getValue(Plays::class.java)
                    dataList.add(film!!)
                }
                rv_list_plays.adapter = PlaysAdapter(dataList){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }


        })
    }
}