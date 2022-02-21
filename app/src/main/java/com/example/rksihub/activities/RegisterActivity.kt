package com.example.rksihub.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.rksihub.R
import com.example.rksihub.databinding.ActivityRegisterBinding
import com.example.rksihub.fragments.EnterPhoneNumberFragment
import com.example.rksihub.utilits.initFirebase
import com.example.rksihub.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()

    }

    override fun onStart() {
        super.onStart()
        title = getString(R.string.register_title_your_phone)
        replaceFragment(EnterPhoneNumberFragment())
    }
}