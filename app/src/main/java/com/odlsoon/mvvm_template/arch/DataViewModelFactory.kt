package com.odlsoon.mvvm_template.arch

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class DataViewModelFactory(
    private val context: Context,
    private val repository: DataRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataViewModel(context, repository) as T
    }
}