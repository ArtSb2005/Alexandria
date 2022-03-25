package com.vvb.alexandria.fragments.menu

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.vvb.alexandria.R
import com.vvb.alexandria.utilits.EventsAdapter
import com.vvb.alexandria.utilits.ProjectData
import com.vvb.alexandria.utilits.StartSettings
import com.vvb.alexandria.utilits.StopSettings
import kotlinx.android.synthetic.main.fragment_news.*


class EventsFragment : Fragment() {

    private lateinit var NAdapter: EventsAdapter
    private lateinit var newsList: ArrayList<ProjectData>
    lateinit var mDataBase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StartSettings()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onStop() {
        super.onStop()
        StopSettings()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupRecyclerView() {
        recyclerNews.layoutManager = LinearLayoutManager(view!!.context)
        newsList = ArrayList()
        NAdapter = EventsAdapter(this, newsList)
        recyclerNews.setHasFixedSize(true)
        // recyclerAnimals.adapter = mAdapter
        // recyclerAnimals.adapter = mAdapter
        /**getData firebase*/
        getNewsData()
    }

    private fun getNewsData() {

        mDataBase = FirebaseDatabase.getInstance().getReference("Animals")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (projectSnapshot in snapshot.children) {
                        val project = projectSnapshot.getValue(ProjectData::class.java)
                        newsList.add(project!!)
                    }
                    try{
                        recyclerNews.adapter = NAdapter
                    }
                    catch(e: NullPointerException){
                        println("Ой")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,
                    error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}