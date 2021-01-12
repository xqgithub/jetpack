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

    private val scope = CoroutineScope(Dispatchers.Main)
    private val userrepostitory = RepositoryProvider.providerUserRepository(MyApplication.myapplication)
    private val bookrepostitory = RepositoryProvider.providerBookRepository(MyApplication.myapplication)

    // by viewModels 需要依赖 "androidx.navigation:navigation-ui-ktx:$rootProject.navigationVersion"
    private val viewModel: InfoModel by viewModels {
        CustomViewModelProvider.providerInfoModelFactory(MyApplication.myapplication)
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

        var logined_username: String by SPreferenceUtils(
            context!!,
            "user",
            "username",
            "我是已经登录的用户"
        )

        tv_readbook.setOnClickListener {
            scope.launch {
                withContext(Dispatchers.IO) {
                    //1.获取当前登录用户的信息
                    var user = userrepostitory.getUserByUsername(logined_username)
                    user?.let {
                        //2.保存用户的读书信息
                        bookrepostitory.insertBook(Book("Android学习阶段一_${it.username}", it.id))
                        bookrepostitory.insertBook(Book("Android学习阶段二_${it.username}", it.id))
                    }
                }
            }
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
            viewModel.setUserID(1)
//            scope.launch {
//                var books: List<Book>?
//                withContext(Dispatchers.IO) {
//                    books = bookrepostitory.getAllBook()
//                }
//                books?.let {
//                    it.forEach { book ->
//                        println("书籍的名称：${book.title}")
//                    }
//                }
//            }
        }
    }
}