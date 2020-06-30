package com.thecat.app.home

import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.thecat.app.R
import com.thecat.app.base.BaseFragment
import com.thecat.app.catDetail.CatDetailFragment
import com.thecat.app.data.model.Cat
import com.thecat.app.favorite.FavoritesFragment
import com.thecat.app.utils.Constants
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels { HomeViewModelFactory(requireContext()) }

    private lateinit var catsAdapter: CatsAdapter

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initUi() {
        initCatList()
        favorites.setOnClickListener { setFragment(FavoritesFragment()) }
    }

    private fun initCatList() {
        observe()
        catsAdapter = CatsAdapter(object : CatsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: Cat, image: ImageView) {
                val args = Bundle()
                args.putParcelable(Constants.CAT, item)
                setFragment(CatDetailFragment(), args)
            }
        })
        rvCats.adapter = catsAdapter
    }

    private fun observe() {
        viewModel.itemPagedList?.observe(this,
            Observer { items ->
                catsAdapter.submitList(items)
            })
    }
}