package com.rinaldialdi.bwamov.tiket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.home.dashboard.ComingSoonAdapter
import com.rinaldialdi.bwamov.model.Film
import com.rinaldialdi.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.fragment_tiket.*


class TiketFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tiket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!)
        mDatabase   = FirebaseDatabase.getInstance().getReference("Film")

        rv_tiket.layoutManager = LinearLayoutManager(context)
        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getDatasnapshot in dataSnapshot.children){
                    val film = getDatasnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_tiket.adapter = ComingSoonAdapter(dataList){

                    val intent = Intent(context, TiketActivity::class.java).putExtra("film", it)
                    startActivity(intent)

                }
                tv_total.setText("${dataList.size} Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}