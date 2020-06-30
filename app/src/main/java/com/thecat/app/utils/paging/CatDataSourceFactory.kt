package com.thecat.app.utils.paging

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.thecat.app.data.model.Cat
import io.reactivex.disposables.CompositeDisposable

class CatDataSourceFactory(
    private val context: Context,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Cat>() {
    private val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Cat>>()

    override fun create(): DataSource<Int, Cat> {
        val itemDataSource = CatDataSource(context, compositeDisposable)
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }

    fun getItemLiveDataSource(): MutableLiveData<PageKeyedDataSource<Int, Cat>>? {
        return itemLiveDataSource
    }
}