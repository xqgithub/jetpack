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


    // 单个页面大小
    const val SINGLE_PAGE_SIZE = 5

    // Name of Notification Channel for verbose notifications of background work
    @JvmField
    val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1

    @JvmField
    val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"

    // Other keys
    const val OUTPUT_PATH = "blur_filter_outputs"

    // MeModel
    const val KEY_IMAGE_URI = "KEY_IMAGE_URI"

    // The name of the image manipulation work
    const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

    const val TAG_OUTPUT = "OUTPUT"

}