package com.example.jetpack.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.datasource.CustomPageDataSourceFactory
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

    //1. 鞋子集合的观察类,不带分页的
    val shoes = brand.switchMap {
        if (it == "All") {
            shoeRepository.getAllShoes()
        } else {
            shoeRepository.getShoesByBrand(it)
        }
    }

    //2. 鞋子集合的观察类，带分页的
//    val shoes = LivePagedListBuilder<Int, Shoe>(
//        CustomPageDataSourceFactory(shoeRepository) // DataSourceFactory
//        , PagedList.Config.Builder()
//            .setPageSize(5) // 分页加载的数量
//            .setEnablePlaceholders(false) // 当item为null是否使用PlaceHolder展示
//            .setInitialLoadSizeHint(5) // 预加载的数量
//            .build()
//    ).build()


    fun setBrand(brand: String) {
        this.brand.value = brand
    }


    /**
     * 获取assets中shoes.json的数据
     */
    suspend fun getShoesFromAssets() = viewModelScope.launch {
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
