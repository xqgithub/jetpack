package com.example.jetpack.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpack.data.repository.BookRepostitory
import com.example.jetpack.ui.viewmodel.InfoModel

class InfoModelFactory(
    private val bookRepostitory: BookRepostitory
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InfoModel(bookRepostitory) as T
    }
}