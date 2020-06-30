package com.thecat.app.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Base viewModel with support
 * [CompositeDisposable]
 */
abstract class BaseViewModel : ViewModel() {

    protected var disposables: CompositeDisposable = CompositeDisposable()

    /**
     * Sets view listeners
     */
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}