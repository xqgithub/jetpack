package com.example.jetpack.ui.viewmodel

import android.content.Context
import androidx.navigation.NavController
import com.example.jetpack.data.RepositoryProvider
import com.example.jetpack.data.repository.BookRepostitory
import com.example.jetpack.data.repository.ShoeRepository
import com.example.jetpack.data.repository.UserRepostitory
import com.example.jetpack.ui.viewmodel.factory.InfoModelFactory
import com.example.jetpack.ui.viewmodel.factory.MeModelFactory
import com.example.jetpack.ui.viewmodel.factory.RegisterModelFactory
import com.example.jetpack.ui.viewmodel.factory.ShoeModelFactory

object CustomViewModelProvider {

    fun providerInfoModelFactory(context: Context): InfoModelFactory {
        val repostitory: BookRepostitory = RepositoryProvider.providerBookRepository(context)
        return InfoModelFactory(repostitory)
    }

    fun providerRegisterModelFactory(context: Context, navController: NavController): RegisterModelFactory {
        val repostitory: UserRepostitory = RepositoryProvider.providerUserRepository(context)
        return RegisterModelFactory(repostitory, navController)
    }

    fun providerShoeModelFactory(context: Context): ShoeModelFactory {
        val repostitory: ShoeRepository = RepositoryProvider.providerShoeRepository(context)
        return ShoeModelFactory(repostitory)
    }


    fun providerMeModel(context: Context): MeModelFactory {
        val userRepostitory: UserRepostitory = RepositoryProvider.providerUserRepository(context)
        return MeModelFactory(userRepostitory)
    }
}