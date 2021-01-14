package com.example.jetpack.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpack.data.repository.ShoeRepository
import com.example.jetpack.ui.viewmodel.ShoeModel

class ShoeModelFactory constructor(
    private val shoeRepository: ShoeRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShoeModel(shoeRepository) as T
    }
}
