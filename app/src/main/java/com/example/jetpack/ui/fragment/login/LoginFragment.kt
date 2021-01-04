package com.example.jetpack.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.jetpack.MainActivity
import com.example.jetpack.R

/**
 * 登录页面
 */
class LoginFragment : androidx.fragment.app.Fragment() {

    lateinit var mCancel: TextView
    lateinit var mLogin: Button
    lateinit var mAccount: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCancel = view.findViewById(R.id.txt_cancel)
        mLogin = view.findViewById(R.id.btn_login)
        mAccount = view.findViewById(R.id.et_account)

        mLogin.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            context!!.startActivity(intent)
        }

        mCancel.setOnClickListener {
            activity?.onBackPressed()
        }

        val name = arguments?.getString("name")
        mAccount.setText(name)
    }
}