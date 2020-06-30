package com.thecat.app.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.thecat.app.R
import com.thecat.app.base.BaseFragment
import com.thecat.app.base.Status
import com.thecat.app.catDetail.CatDetailFragment
import com.thecat.app.data.model.Cat
import com.thecat.app.data.Repository
import com.thecat.app.utils.Constants
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_favorites.back

class FavoritesFragment : BaseFragment() {

    private val viewModel: FavCatsViewModel by viewModels {
        FavCatsViewModelFactory(
            Repository(
                requireContext()
            )
        )
    }

    private lateinit var favCatsAdapter: FavCatsAdapter

    override fun getLayoutId(): Int = R.layout.fragment_favorites

    override fun initUi() {
        viewModel.getAll()
        observe()

        rvFavorites.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        favCatsAdapter = FavCatsAdapter(object :
            FavCatsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, cat: Cat) {
                val args = Bundle()
                args.putParcelable(Constants.CAT, cat)
                setFragment(CatDetailFragment(), args)
            }
        })
        rvFavorites.adapter = favCatsAdapter
        back.setOnClickListener { onBackPressed() }
    }

    private fun observe() {
        viewModel.favorites.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> displayProgressbar(true)
                Status.ERROR -> displayError(it.message)
                Status.SUCCESS -> {
                    displayProgressbar(false)
                    if (it.data == null || it.data.isEmpty()) {
                        tvEmptyList.visibility = View.VISIBLE
                    } else {
                        tvEmptyList.visibility = View.GONE
                        favCatsAdapter.setItems(it.data)
                    }
                }
            }
        })
    }
}