package com.odlsoon.mvvm_template.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.odlsoon.mvvm_template.arch.Data
import com.odlsoon.mvvm_template.arch.DataDao

@Database(
    entities = [Data::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dataDao: DataDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun database(context: Context): AppDatabase{
            var instanceTemp = instance
            if(instanceTemp != null) return instanceTemp

            synchronized(this){
                val newInstance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_db")
//                    .addMigrations(MigrationDatabase.instance.migrate3_4)
                    .build()

                instance = newInstance
                return newInstance
            }
        }
    }
}