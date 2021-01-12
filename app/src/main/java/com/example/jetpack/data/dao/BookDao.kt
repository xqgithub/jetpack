package com.example.jetpack.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpack.data.entity.Book

@Dao
interface BookDao {

    @Query("select * from book where book_user_id = :book_user_id")
    fun getBookByUserID(book_user_id: Int): Book?


    @Query("select * from book ")
    fun getAllBook(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book): Long

}