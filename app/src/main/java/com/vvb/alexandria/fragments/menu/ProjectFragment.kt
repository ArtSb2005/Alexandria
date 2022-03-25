package com.vvb.alexandria.fragments.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vvb.alexandria.R
import com.google.firebase.database.*
import com.vvb.alexandria.utilits.APP_ACTIVITY
import com.vvb.alexandria.utilits.ProjectData
import com.vvb.alexandria.utilits.ProjectsAdapter
import kotlinx.android.synthetic.main.fragment_project.*


class ProjectFragment : Fragment(R.layout.fragment_project) {

    private lateinit var mAdapter: ProjectsAdapter
    private lateinit var projectList: ArrayList<ProjectData>
    lateinit var mDataBase: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.isfragment = 2
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupRecyclerView() {
        recyclerProjects.layoutManager = LinearLayoutManager(view!!.context)
        projectList = ArrayList()
        mAdapter = ProjectsAdapter(view!!.context, projectList)
        recyclerProjects.setHasFixedSize(true)
        // recyclerAnimals.adapter = mAdapter
        // recyclerAnimals.adapter = mAdapter
        /**getData firebase*/
        getProjectsData()
    }

    private fun getProjectsData() {

        mDataBase = FirebaseDatabase.getInstance().getReference("Projects")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (projectSnapshot in snapshot.children){
                        val project = projectSnapshot.getValue(ProjectData::class.java)
                        projectList.add(project!!)
                    }
                    recyclerProjects.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,
                    error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .remove(ProjectFragment())
    }
}
