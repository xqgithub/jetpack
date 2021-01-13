package com.example.jetpack.data.repository

import android.accounts.Account
import com.example.jetpack.data.dao.UserDao
import com.example.jetpack.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 数据处理仓库---用户
 */
class UserRepostitory private constructor(private val userDao: UserDao) {


    /**
     * 根据id选择用户
     */
    fun findUserById(id: Long) = userDao.getUserById(id)

    /**
     * 获得所有的用户
     */
    fun getAllUsers() = userDao.getAllUsers()

    /**
     * 根据用户名字获得用户---单
     */
    fun getUserByUsername(username: String) = userDao.getUserByUsername(username)

    /**
     * 插入用户---单
     */
    fun register(user: User): Long {
        return userDao.insertUser(user)
    }

    companion object {
        @Volatile
        private var instance: UserRepostitory? = null

        fun getInstance(userDao: UserDao): UserRepostitory =
            instance ?: synchronized(this) {
                instance
                    ?: UserRepostitory(userDao).also {
                        instance = it
                    }
            }
    }
}