package com.example.rksihub

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rksihub.activities.RegisterActivity
import com.example.rksihub.databinding.ActivityMainBinding
import com.example.rksihub.fragments.*
import com.example.rksihub.models.User
import com.example.rksihub.utilits.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mDataBase: DatabaseReference
    private lateinit var projectList: ArrayList<ProjectData>
    private lateinit var newsList: ArrayList<ProjectData>
    private lateinit var mAdapter: ProjectsAdapter
    private lateinit var NAdapter: NewsAdapter
    lateinit var toggle: ActionBarDrawerToggle

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initFirebase()
        initUser()
        setUpTabBar()
        ActionToolBar()

        newsList = ArrayList()
        NAdapter = NewsAdapter(this, newsList)
        recyclerNews.layoutManager = LinearLayoutManager(this)
        recyclerNews.setHasFixedSize(true)
        // recyclerAnimals.adapter = mAdapter
        // recyclerAnimals.adapter = mAdapter
        /**getData firebase*/
        getNewsData()

        projectList = ArrayList()
        mAdapter = ProjectsAdapter(this, projectList)
        recyclerProjects.layoutManager = LinearLayoutManager(this)
        recyclerProjects.setHasFixedSize(true)
        // recyclerAnimals.adapter = mAdapter
        // recyclerAnimals.adapter = mAdapter
        /**getData firebase*/
        getProjectsData()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
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
        APP_ACTIVITY = this
        linearLayout.setVisibility(View.INVISIBLE)
        linearLayout1.setVisibility(View.INVISIBLE)
    }


    private fun initFunc() {
        if (AUTH.currentUser!=null){
            replaceFragment(NewsFragment())
        } else {
            replaceActivity(RegisterActivity())
        }
    }

    private fun ActionToolBar() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_settings -> drawerLayout.closeDrawer(GravityCompat.START) to linearLayout1.setVisibility(View.INVISIBLE) to linearLayout.setVisibility(View.INVISIBLE) to supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.dataContainer, SettingsFragment()).commit()
                R.id.settings_menu_exit -> {
                    AUTH.signOut()
                    replaceActivity(RegisterActivity())
                }
                R.id.settings_menu_change_name -> drawerLayout.closeDrawer(GravityCompat.START) to replaceFragment(ChangeNameFragment()) to linearLayout1.setVisibility(View.INVISIBLE) to linearLayout.setVisibility(View.INVISIBLE)
            }
            true
        }
        ToolImg.setOnClickListener { view ->
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setUpTabBar() {
        mBinding.menu.setOnItemSelectedListener {
            when (it) {
                R.id.home -> linearLayout1.setVisibility(View.VISIBLE) to linearLayout.setVisibility(View.INVISIBLE) to supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.dataContainer, NewsFragment()).commit()
                R.id.chat -> linearLayout.setVisibility(View.INVISIBLE) to linearLayout1.setVisibility(View.INVISIBLE) to supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.dataContainer, ChatsFragment()).commit()
                R.id.project -> linearLayout.setVisibility(View.VISIBLE) to linearLayout1.setVisibility(View.INVISIBLE) to supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.dataContainer, ProjectFragment()).commit()
                R.id.calendar -> linearLayout.setVisibility(View.INVISIBLE) to linearLayout1.setVisibility(View.INVISIBLE) to supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.dataContainer, CalendarFragment()).commit()
            }
        }
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
                Toast.makeText(this@MainActivity,
                    error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getNewsData() {

        mDataBase = FirebaseDatabase.getInstance().getReference("Animals")
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (projectSnapshot in snapshot.children){
                        val project = projectSnapshot.getValue(ProjectData::class.java)
                        newsList.add(project!!)
                    }
                    recyclerNews.adapter = NAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity,
                    error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initFields() {
        if (AUTH.currentUser!=null){
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)
            path.downloadUrl.addOnCompleteListener { task2 ->
                if (task2.isSuccessful) {
                    val photoUrl = task2.result.toString()
                    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
                        .child(CHILD_PHOTO_URL).setValue(photoUrl)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                ToolImg.downloadAndSetImage(photoUrl)
                                nav_photo.downloadAndSetImage(photoUrl)
                                USER.photoUrl = photoUrl
                            }
                        }
                }
            }
        } else {
            println("Авторизация")
        }

    }


    private fun initUser() {
        if (AUTH.currentUser!=null){
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
                .addListenerForSingleValueEvent(AppValueEventListener{
                    USER = it.getValue(User::class.java) ?: User()
                    user_name.text = USER.fullname
                    user_number_phone.text = USER.phone
                })
        }else {
            println("Авторизация")
        }
    }
}

