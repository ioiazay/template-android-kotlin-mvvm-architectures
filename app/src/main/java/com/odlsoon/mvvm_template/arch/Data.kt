package com.odlsoon.mvvm_template.arch

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
class Data (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0,
    var name : String
){
    constructor(): this(0, "")
}