package com.example.jetpack.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jetpack.R
import kotlinx.android.synthetic.main.fragment_register.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCancel = view.findViewById(R.id.txt_cancel)
        mRegister = view.findViewById(R.id.btn_register)
        mEmailEt = view.findViewById(R.id.et_email)

        mRegister.setOnClickListener {
            Toast.makeText(context, "Register", Toast.LENGTH_SHORT).show()
            //跳转到登录页面
            // 参数设置
            val bundle = Bundle()
            bundle.putString("name", "路飞")
            findNavController().navigate(R.id.login, bundle)
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