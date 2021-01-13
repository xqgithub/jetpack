package com.example.jetpack.ui.viewmodel

import android.content.Context
import androidx.navigation.NavController
import com.example.jetpack.data.RepositoryProvider
import com.example.jetpack.data.repository.BookRepostitory
import com.example.jetpack.data.repository.UserRepostitory
import com.example.jetpack.ui.viewmodel.factory.InfoModelFactory
import com.example.jetpack.ui.viewmodel.factory.RegisterModelFactory

object CustomViewModelProvider {

    fun providerInfoModelFactory(context: Context): InfoModelFactory {
        val repostitory: BookRepostitory = RepositoryProvider.providerBookRepository(context)
        return InfoModelFactory(repostitory)
    }

    fun providerRegisterModelFactory(context: Context, navController: NavController): RegisterModelFactory {
        val repostitory: UserRepostitory = RepositoryProvider.providerUserRepository(context)
        return RegisterModelFactory(repostitory, navController)
    }
}