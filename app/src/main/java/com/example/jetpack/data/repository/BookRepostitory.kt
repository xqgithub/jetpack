package com.example.jetpack.data.repository

import com.example.jetpack.data.dao.BookDao
import com.example.jetpack.data.dao.UserDao
import com.example.jetpack.data.entity.Book
import com.example.jetpack.data.entity.User

/**
 * 数据处理仓库---书籍库
 */
class BookRepostitory private constructor(private val bookDao: BookDao) {

    /**
     * 获得所有的书籍
     */
    fun getAllBook() = bookDao.getAllBook()

    /**
     * 获得书籍根据user_id
     */
    fun getBookByUserID(book_user_id: Int) = bookDao.getBookByUserID(book_user_id)

    /**
     * 插入书籍---单
     */
    fun insertBook(book: Book) = bookDao.insertBook(book)


    companion object {
        @Volatile
        private var instance: BookRepostitory? = null

        fun getInstance(bookDao: BookDao): BookRepostitory =
            instance ?: synchronized(this) {
                instance
                    ?: BookRepostitory(bookDao).also {
                        instance = it
                    }
            }
    }
}