package com.example.fitlifein30days.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CommentViewModel(private val repository: CommentRepository) : ViewModel() {

    private val _comments = MutableLiveData<List<CommentModel>>()
    val comments: LiveData<List<CommentModel>> = _comments
    fun loadComments(dayIndex: Int) {
        viewModelScope.launch {
            _comments.value = repository.loadComments(dayIndex)
        }
    }
    fun addComment(dayIndex: Int, message: String) {
        viewModelScope.launch {
            repository.saveComment(CommentModel(dayIndex = dayIndex, message = message))
            loadComments(dayIndex)
        }
    }

    fun deleteComment(comment: CommentModel, dayIndex: Int) {
        viewModelScope.launch {
            repository.deleteComment(comment)
            loadComments(dayIndex)
        }
    }
    fun deleteAllComments() {
        viewModelScope.launch {
            repository.deleteAllComments()
            _comments.value = emptyList()
        }
    }
}
