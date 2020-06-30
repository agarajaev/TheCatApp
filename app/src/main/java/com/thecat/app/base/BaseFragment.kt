package com.thecat.app.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.thecat.app.MainActivity
import com.thecat.app.R

/**
 * Base fragment
 */
abstract class BaseFragment : Fragment() {

    lateinit var progressBar: AlertDialog

    /**
     * Returns layout id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Init view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createProgressBar()
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    /**
     * Init user cases view
     */
    abstract fun initUi()

    protected fun displayProgressbar(loading: Boolean) {
        if (loading) {
            progressBar.show()
        } else {
            progressBar.dismiss()
        }
    }

    private fun createProgressBar() {
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_progressbar, null)
        progressBar = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setCancelable(false)
            .create()
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    protected fun displayError(message: String?) {
        val errorDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.text_error))
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(android.R.string.yes) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        errorDialog.show()
    }

    public fun setFragment(fragment: Fragment) {
        (context as MainActivity).setFragment(fragment)
    }

    public fun setFragment(fragment: Fragment, args: Bundle?) {
        if (args != null) fragment.arguments = args
        setFragment(fragment)
    }

    public fun setFragment(fragment: Fragment, args: Bundle, view: View) {
        (context as MainActivity).setFragment(fragment, args, view)
    }

    protected open fun hideKeyboard() {
        val view: View? = (context as Activity).currentFocus
        if (view != null) {
            (context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected open fun showKeyboard() {
        val imm: InputMethodManager =
            context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    public fun onBackPressed() {
        (context as MainActivity).onBackPressed()
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        displayProgressbar(false)
    }
}