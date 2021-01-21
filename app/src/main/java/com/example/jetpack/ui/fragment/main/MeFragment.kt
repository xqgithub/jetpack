package com.example.jetpack.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.jetpack.R
import com.example.jetpack.ui.viewmodel.CustomViewModelProvider
import com.example.jetpack.ui.viewmodel.MeModel
import kotlinx.android.synthetic.main.fragment_me.*

/**
 * 首页---Me页面
 */
class MeFragment : Fragment() {

    // MeModel懒加载
    private val model: MeModel by viewModels {
        CustomViewModelProvider.providerMeModel(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tv_changeavatar.setOnClickListener {
            model.applyBlur(3)

        }

        //任务状态观测
        model.outPutWorkInfos.observe(this, Observer {
            val state = it[0]
            if (state.state.isFinished) {
                 // 更新头像
            }
        })
    }


}