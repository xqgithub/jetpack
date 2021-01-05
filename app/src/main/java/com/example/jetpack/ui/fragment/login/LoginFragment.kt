package com.example.jetpack.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.jetpack.MainActivity
import com.example.jetpack.R
import com.example.jetpack.ui.viewmodel.LoginModel
import com.example.jetpack.databinding.FragmentLogin2Binding
import kotlinx.android.synthetic.main.fragment_login2.*

/**
 * 登录页面
 */
class LoginFragment : Fragment() {

    lateinit var loginModel: LoginModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login2, container, false)

        val binding: FragmentLogin2Binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login2, container, false)
        loginModel = LoginModel("", "", context!!, this@LoginFragment)
        binding.loginmodel = loginModel
        binding.fragment = this@LoginFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mCancel = view.findViewById(R.id.txt_cancel)
//        mLogin = view.findViewById(R.id.btn_login)
//        mAccount = view.findViewById(R.id.et_account)
//
//        mLogin.setOnClickListener {
//            val intent = Intent(context, MainActivity::class.java)
//            context!!.startActivity(intent)
//        }
//
//        txt_cancel.setOnClickListener {
////            activity?.onBackPressed()
//            findNavController().popBackStack(R.id.welcome, false)
//        }

//        val name = arguments?.getString("name")
//        mAccount.setText(name)


        val name = arguments?.getString("name")
        if (!TextUtils.isEmpty(name)) {
            loginModel.myName.set(name)
        }
    }
}