package com.thecat.app.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.thecat.app.base.BaseViewModel
import com.thecat.app.data.model.Cat
import com.thecat.app.utils.paging.CatDataSource.Companion.LIMIT
import com.thecat.app.utils.paging.CatDataSourceFactory

class HomeViewModel(context: Context) : BaseViewModel() {
    var itemPagedList: LiveData<PagedList<Cat?>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Int, Cat>>? = null

    init {
        val itemDataSourceFactory =
            CatDataSourceFactory(context = context, compositeDisposable = disposables)
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource()
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(LIMIT).build()
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)
            .build()
    }
}