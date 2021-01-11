package com.example.jetpack.data.dao

import androidx.room.*
import com.example.jetpack.data.entity.User

@Dao
interface UserDao {

    @Query("select * from users where user_id = :user_id")
    fun getUserById(user_id: String): User?

    @Query("select * from users where user_name = :username")
    fun getUserByUsername(username: String): User?

    /*当数据库中已经有此用户的时候，直接替换*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Update
    fun upDateUser(user: User)

    @Query("delete from users")
    fun deleteAllUsers()
}