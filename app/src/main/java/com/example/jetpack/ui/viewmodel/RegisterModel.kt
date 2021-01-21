package com.example.jetpack.ui.viewmodel

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.jetpack.R
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.entity.User
import com.example.jetpack.data.repository.UserRepostitory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 注册的ViewModel
 */
class RegisterModel constructor(
    private val userRepostitory: UserRepostitory,
    private val navController: NavController
) : ViewModel() {

    //用户名
    val n = MutableLiveData<String>("")

    //密码
    val p = MutableLiveData<String>("")

    //邮箱
    val mail = MutableLiveData<String>("")

    /**
     * 用户名改变回调的函数
     */
    fun onNameChanged(s: CharSequence) {
        n.value = s.toString()
    }

    /**
     * 邮箱改变的时候
     */
    fun onEmailChanged(s: CharSequence) {
        mail.value = s.toString()
    }

    /**
     * 密码改变的回调函数
     */
    fun onPwdChanged(s: CharSequence) {
        p.value = s.toString()
    }

    /**
     * 用户注册
     */
    fun register() {
        if (TextUtils.isEmpty(n.value)
            || TextUtils.isEmpty(p.value)
            || TextUtils.isEmpty(mail.value)
        ) {
            Toast.makeText(MyApplication.myapplication.applicationContext, "用户名|密码|邮箱 不能为空", Toast.LENGTH_SHORT).show()
            return
        } else {
            viewModelScope.launch {
                val isExistUser = userRepostitory.getUserByUsername(n.value!!)
                isExistUser?.let {
                    Toast.makeText(MyApplication.myapplication.applicationContext, "用户已经存在，请重新注册", Toast.LENGTH_SHORT).show()
                } ?: let {
                    val tempuser = User(n.value!!, mail.value!!, p.value!!, 30, "新世界---人鱼岛", "")
                    val id = userRepostitory.register(tempuser)
                    val user = userRepostitory.findUserById(id)
                    user?.let {
                        val bundle = Bundle()
                        bundle.putString("name", it.username)
                        navController.navigate(R.id.login, bundle)
                        Toast.makeText(MyApplication.myapplication.applicationContext, "注册成功", Toast.LENGTH_SHORT).show()
                    } ?: let {
                        Toast.makeText(MyApplication.myapplication.applicationContext, "注册失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}