package com.thecat.app.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thecat.app.base.BaseViewModel
import com.thecat.app.base.Resource
import com.thecat.app.data.model.Cat
import com.thecat.app.data.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavCatsViewModel(
    private val repository: Repository
) : BaseViewModel() {

    private val _favorites = MutableLiveData<Resource<List<Cat>>>()
    val favorites: LiveData<Resource<List<Cat>>>
        get() = _favorites

    fun getAll() {
        _favorites.postValue(Resource.loading())
        disposables.add(
            repository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _favorites.postValue(Resource.success(it)) },
                    { _favorites.postValue(Resource.error(it.message)) })
        )
    }
}