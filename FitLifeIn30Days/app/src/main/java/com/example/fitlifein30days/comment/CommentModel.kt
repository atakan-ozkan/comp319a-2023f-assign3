package com.example.fitlifein30days.comment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayIndex: Int,
    val message: String
)
