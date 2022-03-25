package com.vvb.alexandria.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.vvb.alexandria.R
import com.vvb.alexandria.fragments.menu.NewsFragment
import com.vvb.alexandria.utilits.APP_ACTIVITY
import com.vvb.alexandria.utilits.AUTH
import com.vvb.alexandria.utilits.StopSettings
import com.vvb.alexandria.utilits.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

class SplashFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        APP_ACTIVITY.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        Handler().postDelayed({
            initFunc()
        }, 3000)
    }

    private fun initFunc() {
        if (AUTH.currentUser!=null){
            replaceFragment(NewsFragment())
        } else { }
    }

    override fun onStop() {
        super.onStop()
        StopSettings()
        APP_ACTIVITY.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        APP_ACTIVITY.ToolBar.visibility = View.VISIBLE
        APP_ACTIVITY.nav_view.visibility = View.VISIBLE
        APP_ACTIVITY.bottomNavigation.visibility = View.VISIBLE
    }
}