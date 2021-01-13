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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jetpack.R
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.RepositoryProvider
import com.example.jetpack.data.entity.User
import com.example.jetpack.databinding.FragmentRegisterBinding
import com.example.jetpack.ui.viewmodel.CustomViewModelProvider
import com.example.jetpack.ui.viewmodel.RegisterModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 注册页面
 */
class RegisterFragment : Fragment() {

    private val registerModel: RegisterModel by viewModels {
        CustomViewModelProvider.providerRegisterModelFactory(MyApplication.myapplication.applicationContext, findNavController())
    }

    lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_register, container, false)

        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        binding.model = registerModel
        binding.activity = activity
        this.binding = binding
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //通过SafeArgs插件获取值
        val safeArgs: RegisterFragmentArgs by navArgs()
        //邮箱
        val email = safeArgs.email
        binding.model?.mail?.value = email
        //账户
        val account = safeArgs.account
        binding.model?.n?.value = account
        //密码
        val password = safeArgs.password
        binding.model?.p?.value = password

        btn_register.setOnClickListener {
            //注册用户并且保存到数据库中
            registerModel.register()
        }

        txt_cancel.setOnClickListener {
            activity?.onBackPressed()
        }

        registerModel.n.observe(viewLifecycleOwner, Observer {
            btn_register.isEnabled = it.isNotEmpty() && registerModel.p.value!!.isNotEmpty() && registerModel.mail.value!!.isNotEmpty()
        })
    }
}