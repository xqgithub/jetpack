package com.example.jetpack.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack.R
import com.example.jetpack.application.MyApplication
import com.example.jetpack.data.entity.Shoe
import com.example.jetpack.ui.adapter.ShoeAdapter
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
            getShoesFromAssets()
        }
    }


    /**
     * 获取assets中shoes.json的数据
     */
    fun getShoesFromAssets() {
        try {
            scope.launch {
                var resultjson = withContext(Dispatchers.IO) {
                    MyApplication.myapplication.applicationContext.assets.open("shoes.json").use {
                        BufferedReader(InputStreamReader(it, "utf-8")).use { bf ->
                            var line = ""
                            var stringBuilder = StringBuilder()
                            while (bf.readLine()?.let {
                                    line = it
                                } != null) {
                                stringBuilder.append(line)
                            }
                            stringBuilder.toString()
                        }

                    }
                }
                val shoeType = object : TypeToken<List<Shoe>>() {}.type
                val shoeList = Gson().fromJson(resultjson, shoeType) as List<Shoe>
                initRecyclerview(shoeList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 初始化recyclerview
     */
    private fun initRecyclerview(shoeList: List<Shoe>) {
        //创建一个layout管理器
        val linearlayoutmanager = LinearLayoutManager(context)
        linearlayoutmanager.orientation = RecyclerView.VERTICAL
        rv_shoe.layoutManager = linearlayoutmanager

        //初始化recyclerview的适配器
        val shoeAdapter = ShoeAdapter(context!!, shoeList)
        rv_shoe.adapter = shoeAdapter
        //数据刷新
        (rv_shoe.adapter as ShoeAdapter).notifyDataSetChanged()
    }


}