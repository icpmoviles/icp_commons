package es.icp.icp_commons.simplesearchview

import com.google.android.material.tabs.TabLayout

/**
 * @author Carlos del Campo Cebrian
 * @version 1.0
 *
 */
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