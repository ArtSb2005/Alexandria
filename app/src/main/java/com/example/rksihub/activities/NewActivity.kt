package com.example.rksihub

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.rksihub.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_new.*
import kotlinx.android.synthetic.main.exit_menu.*

class NewActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        /**get Data*/
        val projectIntent = intent
        val projectName = projectIntent.getStringExtra("name")
        val projectInfo = projectIntent.getStringExtra("info")
        val projectLink = projectIntent.getStringExtra("link")
        val projectAuthor = projectIntent.getStringExtra("author")
        val projectDate = projectIntent.getStringExtra("date")

        /**call text*/
        name.text = projectName
        info.text = projectInfo
        link.text = projectLink
        author.text = projectAuthor
        date.text = projectDate


        val intent = Intent(this, MainActivity::class.java)
        exit_icon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                startActivity(intent)
            }
        })

    }

}