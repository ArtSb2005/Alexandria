package com.vvb.alexandria

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.vvb.alexandria.databinding.ActivityMainBinding
import com.vvb.alexandria.fragments.SplashFragment
import com.vvb.alexandria.fragments.menu.NewsFragment
import com.vvb.alexandria.fragments.register.EnterPhoneNumberFragment
import com.vvb.alexandria.models.UserModel
import com.vvb.alexandria.utilits.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    var isfragment = 0
    var isValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        bottomNavigation.setVisibility(View.INVISIBLE)
        ToolBar.setVisibility(View.INVISIBLE)
        DrawerLoad()
        toggle.syncState()
        initFirebase()
        initUser()
        setUpTabBar()
        ActionToolBar()
        TabBarIcon()
        CoroutineScope(Dispatchers.IO).launch {
            initContacts()
        }
        FirebaseApp.initializeApp(/*context=*/this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
        AppStates.updateState(AppStates.ONLINE)
        SavedFragment()
    }

    override fun onStop() {
        super.onStop()
        AppStates.updateState(AppStates.OFFLINE)

    }

    private fun initFunc() {
        if (AUTH.currentUser!=null){
            if (isValid == true){
                replaceFragment(NewsFragment(), false)
            } else {replaceFragment(SplashFragment(), false)}
            isValid = true

        } else {
            replaceFragment(EnterPhoneNumberFragment(), false)
        }
    }

    private fun initUser() {
        if (AUTH.currentUser!=null){
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
                .addListenerForSingleValueEvent(AppValueEventListener{
                    USER = it.getValue(UserModel::class.java) ?: UserModel()
                    user_name.text = USER.fullname
                    user_number_phone.text = USER.phone
                })
        }else {
            println()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            initContacts()
        }
    }

}

