package com.example.jetpack.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.jetpack.MainActivity
import com.example.jetpack.R
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.RepositoryProvider
import com.example.jetpack.data.entity.User
import com.example.jetpack.utils.SPreferenceUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

/**
 * 主要负责登录逻辑的处理以及两个输入框内容改变的时候数据更新的处理
 */
class LoginModel constructor(name: String, pwd: String, context: Context, fragment: Fragment) {

    val myName = ObservableField<String>(name)
    val myPwd = ObservableField<String>(pwd)
    var context: Context = context
    var myfragment: Fragment = fragment
    private val userrepostitory = RepositoryProvider.providerUserRepository(MyApplication.myapplication)

    private val scope = CoroutineScope(Dispatchers.Main)

    //已经登录的用户名
    var logined_username: String by SPreferenceUtils(
        context,
        "user",
        "username",
        "我是已经登录的用户"
    )

    /**
     * 用户名改变回调的函数
     */
    fun onNameChanged(s: CharSequence) {
        myName.set(s.toString())
    }

    /**
     * 密码改变的回调函数
     */
    fun onPwdChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        myPwd.set(s.toString())
    }

    fun login() {
        //1.先从数据库中查询数据
        scope.launch {
            var user: User?
            withContext(Dispatchers.IO) {
                user = userrepostitory.getUserByUsername(myName.get()!!)
            }
            user?.let {
                if (myPwd.get().equals(it.password)
                ) {
                    Toast.makeText(context, "账号密码正确", Toast.LENGTH_SHORT).show()

                    logined_username = it.username

                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)

                } else {
                    Toast.makeText(context, "账号密码不正确，请重新输入！", Toast.LENGTH_SHORT).show()
                }
            } ?: let {
                Toast.makeText(context, "该用户没有注册，请注册后再登录！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun cancel() {
        myfragment.findNavController().popBackStack(R.id.welcome, false)
    }


    // TextWatcher
    val nameWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            myName.set(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    val pwdWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            myPwd.set(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

//    @BindingAdapter("addTextChangedListener")
//    fun addTextChangedListener(editText: EditText, textWatcher: TextWatcher) {
//        editText.addTextChangedListener(textWatcher)
//    }
}