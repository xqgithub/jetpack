package com.example.jetpack.work

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.jetpack.constants.ConfigConstants
import com.example.jetpack.constants.ConfigConstants.KEY_IMAGE_URI
import com.example.jetpack.utils.blurBitmap
import com.example.jetpack.utils.makeStatusNotification
import com.example.jetpack.utils.writeBitmapToFile

/**
 * 模糊处理的Worker
 */
class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val context = applicationContext
        val resourceUri = inputData.getString(ConfigConstants.KEY_IMAGE_URI)

        // 通知开始处理图片
        makeStatusNotification("Blurring image", context)

        return try {
            // 图片处理逻辑
            if (TextUtils.isEmpty(resourceUri)) {
                println("Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }
            val resolver = context.contentResolver
            val picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
            // 创建Bitmap文件
            val output = blurBitmap(picture, context)
            // 存入路径
            val outputUri = writeBitmapToFile(context, output)

            // 输出路径
            val outPutData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            makeStatusNotification("Output is $outputUri", context)
            Result.success(outPutData)
        } catch (exception: Exception) {
            println("Error cleaning up $exception")
            // 失败时返回
            Result.failure()
        }
    }
}