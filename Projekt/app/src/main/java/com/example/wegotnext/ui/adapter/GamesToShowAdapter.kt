package com.example.wegotnext.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.wegotnext.ui.fragments.AllGamesFragment
import com.example.wegotnext.ui.fragments.UserGamesFragment

class GamesToShowAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    val fragments = arrayOf(
        AllGamesFragment.newInstance(),
        UserGamesFragment.newInstance()
    )
    val titles = arrayOf("All Games", "User Games")
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
    override fun getCount(): Int {
        return fragments.size;
    }
}