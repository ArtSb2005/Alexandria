package com.vvb.alexandria.fragments.menu

import androidx.fragment.app.Fragment
import com.vvb.alexandria.R
import com.vvb.alexandria.utilits.APP_ACTIVITY


class ChatsFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
        APP_ACTIVITY.isfragment = 1
    }
}