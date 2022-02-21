package com.example.rksihub.fragments

import androidx.fragment.app.Fragment
import com.example.rksihub.MainActivity


open class BaseFragment( layout:Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        (activity as MainActivity)
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity)
    }
}