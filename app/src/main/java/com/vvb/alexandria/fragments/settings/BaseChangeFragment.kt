package com.vvb.alexandria.fragments.settings

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.vvb.alexandria.MainActivity
import com.vvb.alexandria.R

open class BaseChangeFragment (layout:Int): Fragment(layout) {


    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        /* Создание выпадающего меню*/
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* Слушатель выбора пункта выпадающего меню */
        when (item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    open fun change() {

    }
}