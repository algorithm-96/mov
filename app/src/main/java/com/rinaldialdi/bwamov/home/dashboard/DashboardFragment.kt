package com.rinaldialdi.bwamov.home.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.rinaldialdi.bwamov.DetailActivity
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.model.Film
import com.rinaldialdi.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase   = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.text = preferences.getValues("nama")
        if (preferences.getValues("saldo").equals("")){

            preferences.getValues("saldo")?.let { currency(it.toDouble(),tv_saldo) }
        }

        Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

        rv_nowplaying.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        rv_comingsoon.layoutManager = LinearLayoutManager(context)


        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()

                for (getdataSnapshot in dataSnapshot.children){

                    val film = getdataSnapshot.getValue(Film::class.java)
                    if (film != null) {
                        dataList.add(film)
                    }
                }

                rv_nowplaying.adapter = NowPlayingAdapter(dataList){
                    val intent = Intent(context, DetailActivity::class.java). putExtra("data", it)
                    startActivity(intent)
                }
                rv_comingsoon.adapter = ComingSoonAdapter(dataList){
                    val intent = Intent(context, DetailActivity::class.java). putExtra("data", it)
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }


        })
    }

    private fun currency(harga : Double, textview : TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textview.text = format.format(harga)

    }
}