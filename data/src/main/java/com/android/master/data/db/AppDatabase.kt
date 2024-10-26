package com.android.master.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.master.data.db.dao.MockDao
import com.android.master.data.db.entity.MockEntity

@Database(entities = [MockEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mockDao(): MockDao

    companion object {
        const val DB_NAME = "MockDatabase.db"
    }
}