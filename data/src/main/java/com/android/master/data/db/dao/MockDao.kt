package com.android.master.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.master.data.db.entity.MockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MockDao {

    @Query("SELECT * FROM `MockList`")
    fun getAll(): Flow<List<MockEntity>>

    @Query("SELECT * FROM `MockList` WHERE id=:id")
    suspend fun get(id: String): MockEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MockEntity)

    @Delete
    suspend fun delete(item: MockEntity)
}