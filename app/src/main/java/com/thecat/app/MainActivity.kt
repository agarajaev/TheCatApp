package com.thecat.app

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.thecat.app.base.BaseActivity
import com.thecat.app.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(HomeFragment())
        splash()
    }

    private fun splash() {
        Handler().postDelayed({
            splash.visibility = View.GONE
            container.visibility = View.VISIBLE
        }, 3000)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) supportFragmentManager.popBackStack()
        else finish()
    }
}