package com.vvb.alexandria.utilits

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import com.vvb.alexandria.MainActivity
import com.vvb.alexandria.R
import com.vvb.alexandria.fragments.drawer.ContactsFragment
import com.vvb.alexandria.fragments.menu.*
import com.vvb.alexandria.fragments.register.EnterPhoneNumberFragment
import com.vvb.alexandria.fragments.settings.ChangeNameFragment
import com.vvb.alexandria.fragments.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import java.text.SimpleDateFormat
import java.util.*


fun showToast(message:String){
    Toast.makeText(APP_ACTIVITY,message,Toast.LENGTH_SHORT).show()
}

fun restartActivity(){
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    APP_ACTIVITY.finish()
}

fun replaceFragment(fragment: Fragment, addStack:Boolean = true){
    /* Функция расширения для AppCompatActivity, позволяет устанавливать фрагменты */
    if (addStack){
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.dataContainer,
                fragment
            ).commit()
    } else {
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer,
                fragment
            ).commit()
    }

}


fun ImageView.downloadAndSetImage(url:String){
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_default_photo_bar)
        .into(this)
}

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun setUpTabBar() {
    APP_ACTIVITY.bottomNavigation.setOnClickMenuListener {
        when (it.id){
            0 -> APP_ACTIVITY.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.dataContainer, NewsFragment()).commit()
            1 -> APP_ACTIVITY.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.dataContainer, ChatsFragment()).commit()
            2 -> APP_ACTIVITY.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.dataContainer, ProjectFragment()).commit()
            3 -> APP_ACTIVITY.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.dataContainer, CalendarFragment()).commit()
            else -> {}
        }
    }
}

fun SavedFragment() {
    if (APP_ACTIVITY.isfragment == 1){ replaceFragment(ChatsFragment()) }
    if (APP_ACTIVITY.isfragment == 2){ replaceFragment(ProjectFragment()) }
    if (APP_ACTIVITY.isfragment == 3){ replaceFragment(CalendarFragment()) }
    if (APP_ACTIVITY.isfragment == 4){ replaceFragment(SettingsFragment()) }
    if (APP_ACTIVITY.isfragment == 5){ replaceFragment(ChangeNameFragment()) }
    if (APP_ACTIVITY.isfragment == 6){ replaceFragment(ContactsFragment()) }
}

fun StopSettings(){
    APP_ACTIVITY.ToolImg.visibility = View.VISIBLE
    APP_ACTIVITY.arrowback.visibility = View.GONE
    APP_ACTIVITY.bottomNavigation.visibility = View.VISIBLE
    APP_ACTIVITY.ToolImg.downloadAndSetImage(USER.photoUrl)
}

fun StartSettings(){
    APP_ACTIVITY.bottomNavigation.visibility = View.GONE
    APP_ACTIVITY.ToolImg.visibility = View.GONE
    APP_ACTIVITY.arrowback.visibility = View.VISIBLE
    APP_ACTIVITY.arrowback.setOnClickListener(object : View.OnClickListener {
        override fun onClick(view: View?) {
            replaceFragment(NewsFragment())
        }
    })
}

fun TabBarIcon() {
    val bottomNavigation = APP_ACTIVITY.bottomNavigation
    bottomNavigation.show(0)
    bottomNavigation.add(MeowBottomNavigation.Model(0,R.drawable.ic_home))
    bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_chat))
    bottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.ic_project))
    bottomNavigation.add(MeowBottomNavigation.Model(3,R.drawable.ic_calendar))
}

fun initFields() {
    if (AUTH.currentUser!=null){
        val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
            .child(CURRENT_UID)
        getUrlFromStorage(path){
            putUrlToDatabase(it){
                APP_ACTIVITY.ToolImg.downloadAndSetImage(USER.photoUrl)
                APP_ACTIVITY.nav_photo.downloadAndSetImage(USER.photoUrl)
            }
        }
    }else {
        println("Авторизация")
    }
}

fun DrawerLoad(){
    val drawerLayout: DrawerLayout = APP_ACTIVITY.findViewById(R.id.drawerLayout)
    APP_ACTIVITY.toggle = ActionBarDrawerToggle(APP_ACTIVITY, drawerLayout, R.string.open, R.string.close)
    drawerLayout.addDrawerListener(APP_ACTIVITY.toggle)
}

fun ActionToolBar() {
    val navView: NavigationView = APP_ACTIVITY.findViewById(R.id.nav_view)
    navView.setNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.nav_contacts -> APP_ACTIVITY.drawerLayout.closeDrawer(GravityCompat.START) to APP_ACTIVITY.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.dataContainer, ContactsFragment()).commit()
            R.id.nav_events -> APP_ACTIVITY.drawerLayout.closeDrawer(GravityCompat.START) to APP_ACTIVITY.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.dataContainer, EventsFragment()).commit()
            R.id.nav_settings -> APP_ACTIVITY.drawerLayout.closeDrawer(GravityCompat.START) to APP_ACTIVITY.supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.dataContainer, SettingsFragment()).commit()
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                replaceFragment(EnterPhoneNumberFragment())
            }
            R.id.settings_menu_change_name -> APP_ACTIVITY.drawerLayout.closeDrawer(GravityCompat.START) to replaceFragment(
                ChangeNameFragment()
            )
        }
        true
    }
    APP_ACTIVITY.ToolImg.setOnClickListener { view ->
        APP_ACTIVITY.drawerLayout.openDrawer(GravityCompat.START)
    }
}