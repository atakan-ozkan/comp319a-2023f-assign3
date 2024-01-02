package com.example.fitlifein30days.appdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitlifein30days.comment.CommentDao
import com.example.fitlifein30days.comment.CommentModel

@Database(entities = [CommentModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDao
}