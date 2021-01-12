package com.example.jetpack.ui.viewmodel

import android.content.Context
import com.example.jetpack.data.RepositoryProvider
import com.example.jetpack.data.repository.BookRepostitory
import com.example.jetpack.ui.viewmodel.factory.InfoModelFactory

object CustomViewModelProvider {

    fun providerInfoModelFactory(context: Context): InfoModelFactory {
        val repostitory: BookRepostitory = RepositoryProvider.providerBookRepository(context)
        return InfoModelFactory(repostitory)
    }
}