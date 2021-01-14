package com.example.jetpack.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.jetpack.data.repository.ShoeRepository

class ShoeModel constructor(private val shoeRepository: ShoeRepository) : ViewModel() {

    // 品牌的观察对象 默认观察所有的品牌
    private val brand = MutableLiveData<String>().apply {
        value = "All"
    }

    // 鞋子集合的观察类
    val shoes = brand.switchMap {
        if (it == "All") {
            shoeRepository.getAllShoes()
        } else {
            shoeRepository.getShoesByBrand(it)
        }
    }

    fun setBrand(brand: String) {
        this.brand.value = brand
    }
}