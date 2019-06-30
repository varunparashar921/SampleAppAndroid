package com.sample.appointment.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.sample.appointment.views.users_list.UsersFragment
import com.sample.appointment.R
import com.sample.appointment.utils.CommonUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        push(UsersFragment())
    }

    fun push(fragment: Fragment, needToAddBackStack: Boolean = false) {
        hideProgressAndKeyboard()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.mainFrameLayout, fragment)
        if (needToAddBackStack) ft.addToBackStack(fragment.javaClass.simpleName)
        ft.replace(R.id.mainFrameLayout, fragment).commitAllowingStateLoss()
    }

    fun pop() {
        hideProgressAndKeyboard()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else finish()
    }

    fun popUpTo(upToName: String) {
        hideProgressAndKeyboard()
        supportFragmentManager.popBackStackImmediate(upToName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun popAll() {
        hideProgressAndKeyboard()
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun hideProgressAndKeyboard() {
        CommonUtils.hideProgress()
        CommonUtils.hideSoftKeyboard(this)
    }
}
