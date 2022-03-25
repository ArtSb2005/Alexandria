package com.vvb.alexandria.fragments.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vvb.alexandria.R
import com.vvb.alexandria.fragments.BaseFragment
import com.vvb.alexandria.utilits.*
import kotlinx.android.synthetic.main.fragment_one_project.*

class OneProjectFragment(val mIntent: Intent) : BaseFragment(R.layout.fragment_one_project) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_project, container, false)
    }

    override fun onResume() {
        super.onResume()
        initFields()
        StartSettings()
    }

    fun initFields() {
        val projectIntent = mIntent
        val projectName = projectIntent.getStringExtra("name")
        val projectInfo = projectIntent.getStringExtra("info")
        val projectLink = projectIntent.getStringExtra("link")
        val projectAuthor = projectIntent.getStringExtra("author")
        val projectDate = projectIntent.getStringExtra("date")

        name.text = projectName
        info.text = projectInfo
        link.text = projectLink
        author.text = projectAuthor
        date.text = projectDate
    }

    override fun onStop() {
        super.onStop()
        ActionToolBar()
        StopSettings()
        SavedFragment()
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .remove(OneProjectFragment(mIntent))
    }
}