package com.odlsoon.mvvm_template.utils.support

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()
    private val fragmentTitle = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String){
        fragments.add(fragment)
        fragmentTitle.add(title)
        notifyDataSetChanged()
    }

    fun removeFragment(position: Int){
        fragments.removeAt(position)
        notifyDataSetChanged()
    }

    fun getFragments() = fragments

    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount(): Int = fragments.size
    override fun getPageTitle(position: Int): CharSequence? = fragmentTitle[position]
}