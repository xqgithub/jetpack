package com.example.jetpack.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.jetpack.constants.ConfigConstants
import com.example.jetpack.constants.ConfigConstants.IMAGE_MANIPULATION_WORK_NAME
import com.example.jetpack.constants.ConfigConstants.KEY_IMAGE_URI
import com.example.jetpack.constants.ConfigConstants.TAG_OUTPUT
import com.example.jetpack.data.repository.UserRepostitory
import com.example.jetpack.work.BlurWorker
import com.example.jetpack.work.CleanUpWorker
import com.example.jetpack.work.SaveImageToFileWorker

class MeModel(val userRepository: UserRepostitory) : ViewModel() {

    private val workManager = WorkManager.getInstance()
    internal var imageUri: Uri? = null

    //观察任务状态需要使用到LiveData
    internal val outPutWorkInfos: LiveData<List<WorkInfo>>

    init {
        outPutWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }


    internal fun applyBlur(blurLevel: Int) {

        //1.执行一个任务:OneTimeWorkRequest只执行一次的任务请求，支持任务链
//        val request = OneTimeWorkRequest.from(CleanUpWorker::class.java)
//        workManager.enqueue(request)
        //2.执行多个任务
        //a.多任务按顺序执行
//        workManager.beginUniqueWork(
//            ConfigConstants.IMAGE_MANIPULATION_WORK_NAME, // 任务名称
//            ExistingWorkPolicy.REPLACE, // 任务相同的执行策略 分为REPLACE，KEEP，APPEND
//            mutableListOf(OneTimeWorkRequest.from(CleanUpWorker::class.java))
//        ).then(
//            OneTimeWorkRequestBuilder<BlurWorker>().setInputData(createInputDataForUri()).build()
//        ).then(
//            OneTimeWorkRequestBuilder<SaveImageToFileWorker>().build()
//        )
//            .enqueue()
        //b.添加约束条件
        var continuation = workManager.beginUniqueWork(
            ConfigConstants.IMAGE_MANIPULATION_WORK_NAME, // 任务名称
            ExistingWorkPolicy.REPLACE, // 任务相同的执行策略 分为REPLACE，KEEP，APPEND
            OneTimeWorkRequest.from(CleanUpWorker::class.java)
        )
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true) // 非电池低电量
            .setRequiredNetworkType(NetworkType.CONNECTED) // 网络连接的情况
            .setRequiresStorageNotLow(true) // 存储空间足
            .build()
        val save = OneTimeWorkRequestBuilder<BlurWorker>().setInputData(createInputDataForUri())
            .setConstraints(constraints)
            .addTag(TAG_OUTPUT)
            .build()

        continuation = continuation.then(save)
        continuation.enqueue()


    }

    fun cancelWork() {
        //取消某个任务，IMAGE_MANIPULATION_WORK_NAME 任务名称
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
        //取消所有的任务
//        workManager.cancelAllWork()
    }


    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }
}