package com.odlsoon.mvvm_template.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationDatabase {

    var migrate1_2 = object :  Migration(1,2){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE answer_table ADD user_type TEXT")
        }
    }

    var migrate2_3 = object : Migration(2,3){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS owner_table (`user_id` INTEGER NOT NULL, `reputation` INTEGER NOT NULL, `user_type` TEXT, `profile_image` TEXT, `display_name` TEXT, `link` TEXT, PRIMARY KEY(`user_id`))")
        }
    }

    var migrate3_4 = object : Migration(3,4){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS item_table (`accepted` INTEGER, `score` INTEGER, `last_activity_date` INTEGER, `creation_date` INTEGER, `answer_id` INTEGER, `question_id` INTEGER, `owner_user_id` INTEGER, PRIMARY KEY(`answer_id`), FOREIGN KEY(`owner_user_id`) REFERENCES `owner_table`(`user_id`) ON UPDATE NO ACTION ON DELETE NO ACTION )")
        }
    }

    companion object {
        val instance = MigrationDatabase()
    }

}