package com.example.jetpack.data.datasource

import androidx.paging.DataSource
import com.example.jetpack.data.entity.Shoe
import com.example.jetpack.data.repository.ShoeRepository

/**
 * 构建CustomPageDataSource的工厂
 */
class CustomPageDataSourceFactory constructor(private val shoeRepository: ShoeRepository) : DataSource.Factory<Int, Shoe>() {
    override fun create(): DataSource<Int, Shoe> {
        return CustomPageDataSource(shoeRepository)
    }
}