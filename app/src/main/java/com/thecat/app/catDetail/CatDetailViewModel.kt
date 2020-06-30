package com.thecat.app.catDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thecat.app.base.BaseViewModel
import com.thecat.app.base.Resource
import com.thecat.app.data.model.Cat
import com.thecat.app.data.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CatDetailViewModel(
    private val repository: Repository
) : BaseViewModel() {

    private val _favorites = MutableLiveData<Resource<List<Cat>>>()
    private val _favCat = MutableLiveData<Resource<Boolean>>()

    val favorites: LiveData<Resource<List<Cat>>>
        get() = _favorites

    val favCat: LiveData<Resource<Boolean>>
        get() = _favCat

    fun insert(cat: Cat) {
        _favCat.postValue(Resource.loading())
        disposables.add(
            repository.insert(cat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _favCat.postValue(Resource.success(true)) },
                    { _favCat.postValue(Resource.error(it.message)) })
        )
    }

    fun delete(cat: Cat) {
        _favCat.postValue(Resource.loading())
        disposables.add(
            repository.delete(cat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _favCat.postValue(Resource.success(false)) },
                    { _favCat.postValue(Resource.error(it.message)) })
        )
    }

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