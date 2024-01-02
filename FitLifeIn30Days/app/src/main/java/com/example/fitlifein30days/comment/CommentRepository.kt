package com.example.fitlifein30days.comment

class CommentRepository(private val commentDao: CommentDao) {

    fun saveComment(comment: CommentModel) {
        commentDao.insertComment(comment)
    }

    fun loadComments(dayIndex: Int): List<CommentModel> {
        return commentDao.getCommentsForDay(dayIndex)
    }

    fun deleteComment(comment: CommentModel) {
        commentDao.deleteComment(comment)
    }

    fun deleteAllComments() {
        commentDao.deleteAllComments()
    }
}