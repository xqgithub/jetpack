package com.example.jetpack.data.entity

import androidx.room.*

@Entity(
    tableName = "book",
    foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["book_user_id"])],
    indices = [Index("book_title", unique = true)]
)
data class Book(
    @ColumnInfo(name = "book_title")
    val title: String?,
    @ColumnInfo(name = "book_user_id")
    val book_user_id: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    var bookid: Int = 0
}