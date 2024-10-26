package com.android.master.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.master.domain.model.MockItem

@Entity("MockList")
data class MockEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val data: String
)

fun MockEntity.toDomainModel(): MockItem {
    return MockItem(data = data)
}