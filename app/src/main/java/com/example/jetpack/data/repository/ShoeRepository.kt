package com.example.jetpack.data.repository

import com.example.jetpack.data.dao.ShoeDao
import com.example.jetpack.data.entity.Shoe

class ShoeRepository private constructor(private val shoeDao: ShoeDao) {

    fun getAllShoes() = shoeDao.getAllShoesLD()
    fun getAllShoes2() = shoeDao.getAllShoesLD2()

    /**
     * 通过品牌查询鞋子
     */
    fun getShoesByBrand(brand: String) = shoeDao.findShoesByBrandLD(brand)

    /**
     * 插入鞋子的集合
     */
    fun insertShoes(shoes: List<Shoe>) = shoeDao.insertShoes(shoes)

    /**
     * 清空表中的数据
     */
    fun deleteShoes(shoes: List<Shoe>) = shoeDao.deleteShoes(shoes)


    companion object {
        @Volatile
        private var instance: ShoeRepository? = null

        fun getInstance(shoeDao: ShoeDao): ShoeRepository =
            instance ?: synchronized(this) {
                instance
                    ?: ShoeRepository(shoeDao).also {
                        instance = it
                    }
            }
    }
}