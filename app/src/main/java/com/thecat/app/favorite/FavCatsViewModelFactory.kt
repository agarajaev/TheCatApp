package com.thecat.app.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thecat.app.data.Repository

@Suppress("UNCHECKED_CAST")
class FavCatsViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavCatsViewModel(repository) as T
    }
}