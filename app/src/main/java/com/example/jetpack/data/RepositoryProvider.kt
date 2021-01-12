package com.example.jetpack.data

import android.content.Context
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.repository.BookRepostitory
import com.example.jetpack.data.repository.UserRepostitory

object RepositoryProvider {

    /**
     * 得到用户仓库
     */
    fun providerUserRepository(context: Context): UserRepostitory {
        return UserRepostitory.getInstance(MyApplication.appDatabase.userDao())
    }

    /**
     * 得到书籍仓库
     */
    fun providerBookRepository(context: Context): BookRepostitory {
        return BookRepostitory.getInstance(MyApplication.appDatabase.bookDao())
    }
}