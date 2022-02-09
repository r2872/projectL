package com.autopromaker.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.autopromaker.fragment.InfoFragment
import com.autopromaker.fragment.MainFragment

class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> MainFragment()
            else -> InfoFragment()
        }
    }
}