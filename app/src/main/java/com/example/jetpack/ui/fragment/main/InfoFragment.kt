package com.example.jetpack.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.jetpack.R
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.RepositoryProvider
import com.example.jetpack.data.entity.Book
import com.example.jetpack.ui.viewmodel.CustomViewModelProvider
import com.example.jetpack.ui.viewmodel.InfoModel
import com.example.jetpack.utils.SPreferenceUtils
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 首页---info页面
 */
class InfoFragment : Fragment() {

    // by viewModels 需要依赖 "androidx.navigation:navigation-ui-ktx:$rootProject.navigationVersion"
    private val viewModel: InfoModel by viewModels {
        CustomViewModelProvider.providerInfoModelFactory(activity!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        tv_readbook.setOnClickListener {
            viewModel.insertBooks()
        }


        //观察者模式，数据变化就会调用
        viewModel.books.observe(viewLifecycleOwner, Observer {
            it?.let { listbook ->
                listbook.forEach { book ->
                    println("书籍的名称：${book.title}")
                }
            }
        })


        tv_viewbook.setOnClickListener {
            viewModel.setUserID()
        }
    }
}