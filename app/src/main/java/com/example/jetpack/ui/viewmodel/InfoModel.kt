package com.example.jetpack.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.jetpack.data.entity.Book
import com.example.jetpack.data.repository.BookRepostitory

class InfoModel constructor(private val bookRepostitory: BookRepostitory) : ViewModel() {

    private val data = MutableLiveData<Int>().apply {
        value = 0
    }


    val books = data.switchMap {
        if (it == 0) {
            bookRepostitory.getAllBook()
        } else {
            bookRepostitory.getBookByUserID(it)
        }
    }

    fun setUserID(book_user_id: Int) {
        data.value = book_user_id
//        this.book_user_id = book_user_id
    }
}