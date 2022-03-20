package com.takehometest.acmerouter.ui.common

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.takehometest.acmerouter.R

fun Activity.openFragment(
    fragment: Fragment,
    addToBackStack: Boolean
) {
    this as AppCompatActivity
    supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragment_container, fragment)
        if (addToBackStack) {
            addToBackStack(fragment.toString())
        }
        commitAllowingStateLoss()
    }
}
