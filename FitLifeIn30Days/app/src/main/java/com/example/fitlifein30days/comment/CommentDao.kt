package com.example.fitlifein30days.comment

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CommentDao {
    @Insert
    fun insertComment(comment: CommentModel)

    @Query("SELECT * FROM comments WHERE dayIndex = :dayIndex")
    fun getCommentsForDay(dayIndex: Int): List<CommentModel>
    @Delete
    fun deleteComment(comment: CommentModel)
    @Query("DELETE FROM comments")
    fun deleteAllComments()
}