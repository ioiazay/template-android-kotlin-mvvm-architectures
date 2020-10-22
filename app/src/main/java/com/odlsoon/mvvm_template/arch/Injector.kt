package com.odlsoon.mvvm_template.arch

import android.content.Context
import com.odlsoon.mvvm_template.db.AppDatabase

object Injector {

    private fun getDataRepository(context: Context): DataRepository{
        return DataRepository(AppDatabase.database(context).dataDao)
    }

    fun provideDataViewModelFactory(context: Context): DataViewModelFactory{
        return DataViewModelFactory(context, getDataRepository(context))
    }
}