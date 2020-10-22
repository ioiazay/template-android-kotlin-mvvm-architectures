package com.odlsoon.mvvm_template.utils

import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object UIHelper {

    fun loadFragment(fm: FragmentManager, fragment: Fragment, parentView: FrameLayout){
        parentView.removeAllViews()
        fm.beginTransaction()
            .replace(parentView.id, fragment)
            .commit()
    }

    fun refreshFragment(fm: FragmentManager, fragment: Fragment){
        fm.beginTransaction()
            .detach(fragment)
            .attach(fragment)
            .commit()
    }

}