package com.dwicandra.githubusers.ui.detail.adapter.sectionpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dwicandra.githubusers.ui.detail.fragment.FollowersFragment
import com.dwicandra.githubusers.ui.detail.fragment.FollowingFragment

class SectionPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, data: Bundle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val fragmentBundle: Bundle = data

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}