package com.example.jetpack.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack.R
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.entity.Shoe
import com.example.jetpack.ui.adapter.ShoeAdapter
import com.example.jetpack.ui.adapter.ShoePageListAdapter
import com.example.jetpack.ui.viewmodel.CustomViewModelProvider
import com.example.jetpack.ui.viewmodel.ShoeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_market.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * 首页---Market页面
 */
class MarketFragment : Fragment() {

    val scope = CoroutineScope(Dispatchers.Main)

    private val viewModel: ShoeModel by viewModels {
        CustomViewModelProvider.providerShoeModelFactory(context!!)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_showshoes.setOnClickListener {
            val showbrandshoes = et_showbrandshoes.text.toString()
            if (showbrandshoes.isEmpty()) {
                viewModel.setBrand("ALL")
            } else {
                viewModel.setBrand(showbrandshoes)
            }
        }


        scope.launch {
            val job = viewModel.getShoesFromAssets()
            initRecyclerview()
        }
    }

    /**
     * 初始化recyclerview
     */
    private fun initRecyclerview() {
        //创建一个layout管理器
        val linearlayoutmanager = LinearLayoutManager(context)
        linearlayoutmanager.orientation = RecyclerView.VERTICAL
        rv_shoe.layoutManager = linearlayoutmanager

        //初始化recyclerview的适配器
        val shoeAdapter = ShoeAdapter(context!!)
        rv_shoe.adapter = shoeAdapter
        onSubscribeUi(shoeAdapter)


//        val shoePageListAdapter = ShoePageListAdapter(context!!)
//        rv_shoe.adapter = shoePageListAdapter
//        onSubscribeUi2(shoePageListAdapter)
    }

    /**
     * 鞋子数据更新的通知
     */
    private fun onSubscribeUi(adapter: ShoeAdapter) {
        viewModel.shoes.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.updateShoeData(it)
                adapter.notifyDataSetChanged()
            }
        })
    }


//    private fun onSubscribeUi2(adapter: ShoePageListAdapter) {
//        viewModel.shoes.observe(viewLifecycleOwner, Observer {
//            if (it != null) {
//                adapter.submitList(it)
//            }
//        })
//    }
}