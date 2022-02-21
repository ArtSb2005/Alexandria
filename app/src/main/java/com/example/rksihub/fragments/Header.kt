package com.example.rksihub.fragments

import android.view.Menu
import android.view.MenuInflater
import com.example.rksihub.R
import com.example.rksihub.utilits.USER
import kotlinx.android.synthetic.main.fragment_settings.*

class Header : BaseFragment(R.layout.nav_header) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()

    }

    private fun initFields() {
        settings_bio.text = USER.bio
        settings_username.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.status
        settings_login.text = USER.username
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

}