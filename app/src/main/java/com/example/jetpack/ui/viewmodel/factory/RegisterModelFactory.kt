package com.example.jetpack.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.jetpack.data.repository.UserRepostitory
import com.example.jetpack.ui.viewmodel.RegisterModel

class RegisterModelFactory constructor(
    private val userRepostitory: UserRepostitory,
    private val navController: NavController
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterModel(userRepostitory, navController) as T
    }
}