package com.example.jetpack.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.entity.Shoe
import com.example.jetpack.data.repository.ShoeRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

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


    /**
     * 获取assets中shoes.json的数据
     */
    fun getShoesFromAssets() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                //1.先查看表中是否有数据，如果有数据先清空数据
                val listshoes = shoeRepository.getAllShoes2()
                listshoes?.let {
                    println("shoe表中的数量为：${it.size}")
                    if (it.isNotEmpty()) {
                        shoeRepository.deleteShoes(it)
                    }
                }
                //2.获取assets中shoes.json的数据
                var resultjson: String?
                MyApplication.myapplication.applicationContext.assets.open("shoes.json").use {
                    BufferedReader(InputStreamReader(it, "utf-8")).use { bf ->
                        var line = ""
                        var stringBuilder = StringBuilder()
                        while (bf.readLine()?.let {
                                line = it
                            } != null) {
                            stringBuilder.append(line)
                        }
                        resultjson = stringBuilder.toString()
                    }
                }

                resultjson?.let {
                    val shoeType = object : TypeToken<List<Shoe>>() {}.type
                    val shoeList = Gson().fromJson(it, shoeType) as List<Shoe>
                    //3.插入数据到shoe表中
                    shoeRepository.insertShoes(shoeList)
                }
            }
        }
    }
}
