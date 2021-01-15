package com.example.jetpack.data.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.jetpack.constants.ConfigConstants
import com.example.jetpack.data.entity.Shoe
import com.example.jetpack.data.repository.ShoeRepository

/**
 * 自定义PageKeyedDataSource
 * 演示Page库的时候使用
 */
class CustomPageDataSource constructor(private val shoeRepository: ShoeRepository) : PageKeyedDataSource<Int, Shoe>() {

    // 第一次加载的时候调用
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Shoe>) {
        val startIndex = 0L
        val endIndex: Long = 0L + params.requestedLoadSize
        val shoes = shoeRepository.getPageShoes(startIndex, endIndex)
        callback.onResult(shoes, null, 2)
    }

    // 每次分页加载的时候调用
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Shoe>) {
        println("startPage:${params.key},size:${params.requestedLoadSize}")
        val startPage = params.key
        val startIndex = ((startPage - 1) * ConfigConstants.SINGLE_PAGE_SIZE).toLong() + 1
        val endIndex = startIndex + params.requestedLoadSize - 1
        val shoes = shoeRepository.getPageShoes(startIndex, endIndex)

        callback.onResult(shoes, params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Shoe>) {
        println("endPage:${params.key},size:${params.requestedLoadSize}")
        val endPage = params.key
        val endIndex = ((endPage - 1) * ConfigConstants.SINGLE_PAGE_SIZE).toLong() + 1
        var startIndex = endIndex - params.requestedLoadSize
        startIndex = if (startIndex < 0) 0L else startIndex
        val shoes = shoeRepository.getPageShoes(startIndex, endIndex)
        callback.onResult(shoes, params.key + 1)
    }
}