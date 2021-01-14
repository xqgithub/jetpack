package com.example.jetpack.constants

import com.example.jetpack.application.MyApplication
import com.example.jetpack.utils.SDCardUtils
import java.io.File

object ConfigConstants {


    /** gilde配置数据 **/
    //gild缓存大小
    const val GILDE_DISKCACHESIZEBYTES = 1024 * 1024 * 100 // 100 MB

    //gilde缓存路径
    val GILDE_DISKCACHEDIR =
        SDCardUtils.getExternalFilesDir(
            MyApplication.myapplication.applicationContext,
            null
        ).absolutePath + File.separator + "AGlideImage"
}