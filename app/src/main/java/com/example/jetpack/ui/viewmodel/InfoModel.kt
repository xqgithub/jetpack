package com.example.jetpack.ui.viewmodel

import androidx.lifecycle.*
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.RepositoryProvider
import com.example.jetpack.data.entity.Book
import com.example.jetpack.data.entity.User
import com.example.jetpack.data.repository.BookRepostitory
import com.example.jetpack.utils.SPreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoModel constructor(private val bookRepostitory: BookRepostitory) : ViewModel() {

    private val data = MutableLiveData<Int>().apply {
        value = 0
    }

    private val userrepostitory = RepositoryProvider.providerUserRepository(MyApplication.myapplication)

    var logined_username: String by SPreferenceUtils(
        MyApplication.myapplication.applicationContext,
        "user",
        "username",
        "我是已经登录的用户"
    )

    val books = data.switchMap {
        if (it == 0) {
            bookRepostitory.getAllBook()
        } else {
            bookRepostitory.getBookByUserID(it)
        }
    }

    fun setUserID() {
        viewModelScope.launch {
            //1.获取当前登录用户的信息
            var user = userrepostitory.getUserByUsername(logined_username)
            user?.let {
                data.value = user.id
            }
        }
    }

    /**
     * 依据用户，插入要读的书籍
     */
    fun insertBooks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                //1.获取当前登录用户的信息
                var user = userrepostitory.getUserByUsername(logined_username)
                user?.let {
                    //2.保存用户的读书信息
                    bookRepostitory.insertBook(Book("Android学习阶段一_${it.username}", it.id))
                    bookRepostitory.insertBook(Book("Android学习阶段二_${it.username}", it.id))
                }
            }
        }
    }
}