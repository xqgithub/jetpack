package com.example.jetpack.ui.fragment.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jetpack.R
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.RepositoryProvider
import com.example.jetpack.data.entity.User
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 注册页面
 */
class RegisterFragment : Fragment() {

    lateinit var mCancel: TextView
    lateinit var mRegister: Button
    lateinit var mEmailEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }


    private val scope = CoroutineScope(Dispatchers.Main)
    private val userrepostitory = RepositoryProvider.providerUserRepository(MyApplication.myapplication)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCancel = view.findViewById(R.id.txt_cancel)
        mRegister = view.findViewById(R.id.btn_register)
        mEmailEt = view.findViewById(R.id.et_email)

        mRegister.setOnClickListener {
            //注册用户并且保存到数据库中
            val email = et_email.text.toString().trim()
            val account = et_account.text.toString().trim()
            val pwd = et_pwd.text.toString().trim()
//            val user = User(account, email, pwd, 30)
            val user = User(account, email, pwd, 30, "新世界---人鱼岛")
            scope.launch {
                withContext(Dispatchers.IO) {
                    val num = userrepostitory.insertUser(user)
                    println("插入数据：${num}")
                }
                Toast.makeText(context, "Register", Toast.LENGTH_SHORT).show()
                //跳转到登录页面
                // 参数设置
                val bundle = Bundle()
                bundle.putString("name", account)
                findNavController().navigate(R.id.login, bundle)
            }
        }

        mCancel.setOnClickListener {
            activity?.onBackPressed()
        }


        //通过SafeArgs插件获取值
        val safeArgs: RegisterFragmentArgs by navArgs()
        //邮箱
        val email = safeArgs.email
        mEmailEt.setText(email)
        //账户
        val account = safeArgs.account
        et_account.setText(account)
        //密码
        val password = safeArgs.password
        et_pwd.setText(password)
    }


}