package es.icp.icp_commons.simplesearchview

import com.google.android.material.tabs.TabLayout

abstract class SimpleOnTabSelectedListener : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
        // No action
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        // No action
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        // No action
    }
}