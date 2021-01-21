package com.example.jetpack.work

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.jetpack.constants.ConfigConstants.KEY_IMAGE_URI
import com.example.jetpack.utils.makeStatusNotification
import java.text.SimpleDateFormat
import java.util.*

/**
 * 存储照片的Worker
 */
class SaveImageToFileWorker(ctx: Context, parameters: WorkerParameters) : Worker(ctx, parameters) {


    private val Title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z",
        Locale.getDefault()
    )

    override fun doWork(): Result {
        makeStatusNotification("Saving image", applicationContext)

        val resolver = applicationContext.contentResolver
        return try {
            // 获取从外部传入的参数
            val resourceUri = inputData.getString(KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )
            val imageUrl = MediaStore.Images.Media.insertImage(
                resolver, bitmap, Title, dateFormatter.format(Date())
            )
            if (!imageUrl.isNullOrEmpty()) {
                val output = workDataOf(KEY_IMAGE_URI to imageUrl)

                Result.success(output)
            } else {
                println("Writing to MediaStore failed")
                Result.failure()
            }
        } catch (exception: Exception) {
            println("Unable to save image to Gallery $exception")
            Result.failure()
        }
    }

}