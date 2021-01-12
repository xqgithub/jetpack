package com.example.jetpack.data.entity

import android.location.Address
import androidx.room.*

@Entity(
    tableName = "users"
)
data class User(
    @ColumnInfo(name = "user_name")
    val username: String,
    @ColumnInfo(name = "mail_box")
    val mailbox: String,
    @ColumnInfo(name = "pass_word")
    val password: String,
    @ColumnInfo(name = "user_age")
    val userage: Int,
    @ColumnInfo(name = "user_address")
    val address: String? = ""
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var id: Int = 0
}