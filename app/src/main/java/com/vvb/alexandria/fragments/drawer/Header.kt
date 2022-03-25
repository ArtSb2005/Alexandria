package com.vvb.alexandria.fragments.drawer

import android.view.Menu
import android.view.MenuInflater
import com.vvb.alexandria.R
import com.vvb.alexandria.fragments.BaseFragment
import com.vvb.alexandria.utilits.USER
import kotlinx.android.synthetic.main.fragment_settings.*

class Header : BaseFragment(R.layout.nav_header) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()

    }

    private fun initFields() {
        settings_bio.text = USER.bio
        settings_full_name.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.state
        settings_login.text = USER.username
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

}