package com.vvb.alexandria.fragments.settings

import com.vvb.alexandria.R
import com.vvb.alexandria.utilits.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.username)
        StartSettings()
        register_btn_next.setOnClickListener {
            change()
        }
    }

    override fun change() {
        mNewUsername = settings_input_username.text.toString().toLowerCase(Locale.getDefault())
        if (mNewUsername.isEmpty()){
            showToast("Поле пустое")
        } else {
            REF_DATABASE_ROOT.child(
                NODE_USERNAMES
            ).addListenerForSingleValueEvent(AppValueEventListener{
                if (it.hasChild(mNewUsername)){
                    showToast("Такой пользователь уже существует")
                } else{
                    changeUsername()
                }
            })

        }
    }

    private fun changeUsername() {
        /* Изменение username в базе данных */
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(
            CURRENT_UID
        )
            .addOnCompleteListener {
                if (it.isSuccessful){
                    updateCurrentUsername(mNewUsername)
                }
            }
    }

    override fun onStop() {
        super.onStop()
        SavedFragment()
        StopSettings()
    }
}