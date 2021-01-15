package com.example.jetpack.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jetpack.data.entity.Shoe

@Dao
interface ShoeDao {

    // 配合LiveData 返回所有的鞋子
    @Query("select * from shoe")
    fun getAllShoesLD(): LiveData<List<Shoe>>

    @Query("select * from shoe")
    fun getAllShoesLD2(): List<Shoe>


    /**
     * 通过品牌查询鞋子
     */
    @Query("select * from shoe where shoe_brand=:brand")
    fun findShoesByBrandLD(brand: String): LiveData<List<Shoe>>

    // 增加多双鞋子
    // 除了List之外，也可以使用数组
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoes(shoes: List<Shoe>)

    /**
     * 清空表中的数据
     */
    @Delete
    fun deleteShoes(shoes: List<Shoe>)


}