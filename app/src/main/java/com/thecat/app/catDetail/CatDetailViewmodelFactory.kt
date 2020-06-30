package com.thecat.app.catDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thecat.app.data.Repository

@Suppress("UNCHECKED_CAST")
class CatDetailViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CatDetailViewModel(Repository(context)) as T
    }

}