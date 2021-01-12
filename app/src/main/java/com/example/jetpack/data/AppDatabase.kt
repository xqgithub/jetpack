package com.example.jetpack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jetpack.data.dao.BookDao
import com.example.jetpack.data.dao.UserDao
import com.example.jetpack.data.entity.Book
import com.example.jetpack.data.entity.User

@Database(entities = [User::class, Book::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "Sample.db"
            )
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build()
        }

        /**
         * 数据库版本从1到2的时候，添加字段user_address
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE users ADD COLUMN user_address Text")
            }
        }
    }
}