package com.thecat.app.utils.paging

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.thecat.app.data.Repository
import com.thecat.app.data.network.catResponseConverter
import com.thecat.app.data.model.Cat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CatDataSource(
    private val context: Context,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Cat>() {

    companion object {
        val FIRST_PAGE = 1
        val LIMIT = 20
    }

    private val repository = Repository.getInstance(context)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Cat>
    ) {
        compositeDisposable.add(
            repository.getAllCats(limit = LIMIT, page = FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it != null) {
                        callback.onResult(catResponseConverter(it), null, FIRST_PAGE + 1);
                    }
                }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Cat>) {
        compositeDisposable.add(
            repository.getAllCats(limit = LIMIT, page = params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it != null) {
                        var key: Int? = params.key + 1
                        if (it.size < LIMIT) key = null
                        callback.onResult(catResponseConverter(it), key);
                    }
                }
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Cat>) {
        compositeDisposable.add(
            repository.getAllCats(limit = LIMIT, page = params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it != null) {
                        var key: Int? = params.key - 1
                        if (params.key <= 1) key = null
                        callback.onResult(catResponseConverter(it), key);
                    }
                }
        )
    }
}