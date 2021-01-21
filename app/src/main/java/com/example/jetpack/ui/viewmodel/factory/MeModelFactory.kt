package com.example.jetpack.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpack.data.repository.UserRepostitory
import com.example.jetpack.ui.viewmodel.MeModel

class MeModelFactory constructor(
    private val userRepostitory: UserRepostitory
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MeModel(userRepostitory) as T
    }
}