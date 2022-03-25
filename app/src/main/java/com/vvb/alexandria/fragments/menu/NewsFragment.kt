package com.vvb.alexandria.fragments.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.vvb.alexandria.R
import com.vvb.alexandria.utilits.APP_ACTIVITY
import com.vvb.alexandria.utilits.EventsAdapter
import com.vvb.alexandria.utilits.NewsAdapter
import com.vvb.alexandria.utilits.ProjectData
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var NAdapter: NewsAdapter
    private lateinit var newsList: ArrayList<ProjectData>
    lateinit var mDataBase: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.isfragment = 0
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupRecyclerView() {
        recyclerNews.layoutManager = LinearLayoutManager(view!!.context)
        newsList = ArrayList()
        NAdapter = NewsAdapter(this, newsList)
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