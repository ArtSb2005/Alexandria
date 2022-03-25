package com.vvb.alexandria

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.vvb.alexandria.utilits.APP_ACTIVITY
import com.vvb.alexandria.utilits.StartSettings
import com.vvb.alexandria.utilits.StopSettings
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Handler as Handler1

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_ACTIVITY.drawerLayout.close()
        APP_ACTIVITY.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        //4second splash time
        Handler1().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }



    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.drawerLayout.open()
        APP_ACTIVITY.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
    }
}