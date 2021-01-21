package com.example.jetpack.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.jetpack.constants.ConfigConstants.OUTPUT_PATH
import com.example.jetpack.utils.makeStatusNotification
import java.io.File

/**
 * 清理临时文件的Worker
 */
class CleanUpWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        // Makes a notification when the work starts and slows down the work so that
        // it's easier to see each WorkRequest start, even on emulated devices
        makeStatusNotification("Cleaning up old temporary files", applicationContext)

        return try {
            // 删除逻辑
            val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
            if (outputDirectory.exists()) {
                val entries = outputDirectory.listFiles()
                if (entries != null) {
                    for (entry in entries) {
                        val name = entry.name
                        if (name.isNotEmpty() && name.endsWith(".png")) {
                            val deleted = entry.delete()
                            println(String.format("Deleted %s - %s", name, deleted))

                        }
                    }
                }
            }
            // 成功时返回
            Result.success()
        } catch (exception: Exception) {
            println("Error cleaning up $exception")
            // 失败时返回
            Result.failure()
        }
    }
}