package com.example.jetpack.application

import androidx.multidex.MultiDexApplication
import com.example.jetpack.data.AppDatabase
import com.facebook.stetho.Stetho

class MyApplication : MultiDexApplication() {

    lateinit var myapplication: MyApplication

    companion object {
        lateinit var appDatabase: AppDatabase
    }


    override fun onCreate() {
        super.onCreate()
        myapplication = this
        initConfig()
    }

    /**
     * 初始化配置
     */
    private fun initConfig() {
        Stetho.initializeWithDefaults(this)
        appDatabase = AppDatabase.getInstance(this)
    }
}