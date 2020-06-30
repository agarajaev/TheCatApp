package com.thecat.app.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.thecat.app.R
import com.thecat.app.utils.Constants.Companion.MAIN_BACK_STACK_TAG

/**
 * Base activity
 */
abstract class BaseActivity : AppCompatActivity() {

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.child_up,
                R.anim.exit_left,
                R.anim.parent_back,
                R.anim.child_back
            )
            .addToBackStack(MAIN_BACK_STACK_TAG)
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commitAllowingStateLoss()
    }

    fun setFragment(fragment: Fragment, args: Bundle) {
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.child_up,
                R.anim.exit_left,
                R.anim.parent_back,
                R.anim.child_back
            )
            .addToBackStack(MAIN_BACK_STACK_TAG)
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commitAllowingStateLoss()
    }

    fun setFragment(fragment: Fragment, args: Bundle, view: View) {
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.child_up,
                R.anim.exit_left,
                R.anim.parent_back,
                R.anim.child_back
            )
            .addSharedElement(
                view,
                ViewCompat.getTransitionName(view)!!
            )
            .addToBackStack(MAIN_BACK_STACK_TAG)
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commitAllowingStateLoss()
    }
}