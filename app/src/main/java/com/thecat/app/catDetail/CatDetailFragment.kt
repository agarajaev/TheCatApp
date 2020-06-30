package com.thecat.app.catDetail

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.thecat.app.R
import com.thecat.app.base.BaseFragment
import com.thecat.app.base.Status.*
import com.thecat.app.data.model.Cat
import com.thecat.app.utils.Constants
import kotlinx.android.synthetic.main.fragment_cat_detail.*

class CatDetailFragment : BaseFragment() {

    private val viewModel: CatDetailViewModel by viewModels { CatDetailViewModelFactory(requireContext()) }

    private lateinit var cat: Cat

    override fun getLayoutId(): Int = R.layout.fragment_cat_detail

    override fun initUi() {
        cat = arguments?.get(Constants.CAT) as Cat

        viewModel.getAll()
        observe()

        Glide.with(requireContext())
            .load(cat.imageUrl)
            .centerCrop()
            .into(image)


        icFav.setOnClickListener {
            if (cat.isFav!!) {
                viewModel.delete(cat)
                cat.isFav = false
            } else {
                viewModel.insert(cat)
                cat.isFav = true
            }
        }

        name.text = cat.name
        if (cat.desc != null) tvDesc.text = cat.desc
        tvAge.text = cat.age

        btnDownload.setOnClickListener {
            Permissions.check(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                null,
                object : PermissionHandler() {
                    override fun onGranted() {
                        downloadImage()
                    }
                })
        }

        back.setOnClickListener { onBackPressed() }
    }

    private fun observe() {
        viewModel.favCat.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                LOADING -> displayProgressbar(true)
                ERROR -> displayError(it.message)
                SUCCESS -> {
                    displayProgressbar(false)
                    if (it.data == true) icFav.setImageResource(R.drawable.ic_fav_filled)
                    else icFav.setImageResource(R.drawable.ic_fav)
                }
            }
        })

        viewModel.favorites.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                LOADING -> displayProgressbar(true)
                ERROR -> displayError(it.message)
                SUCCESS -> {
                    displayProgressbar(false)
                    for (favCat in it.data!!) {
                        if (favCat.id == cat.id) {
                            cat.isFav = true
                            icFav.setImageResource(R.drawable.ic_fav_filled)
                        }
                    }
                }
            }
        })
    }

    private fun downloadImage() {
        val request = DownloadManager.Request(Uri.parse(cat.imageUrl))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(getString(R.string.download_image))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "TheCatApp${System.currentTimeMillis()}"
        )

        val manager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }
}