package com.odlsoon.mvvm_template.arch

import androidx.room.Dao
import androidx.room.Query
import com.odlsoon.mvvm_template.BaseDao

@Dao
interface DataDao: BaseDao<Data> {

    @Query("SELECT * FROM data_table")
    fun getAllData() : List<Data>

}