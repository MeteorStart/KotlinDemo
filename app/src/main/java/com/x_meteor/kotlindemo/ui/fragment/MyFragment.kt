package com.x_meteor.kotlindemo.ui.fragment

import android.os.Bundle
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.base.BaseFragment
import com.x_meteor.kotlindemo.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/10/29 14:32
 * @company:
 * @email: lx802315@163.com
 */
class MyFragment : BaseFragment() {

    private var mTitle:String? =null

    companion object {
        fun getInstance(title:String): MyFragment {
            val fragment = MyFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_my

    override fun initView() {
        //状态栏透明和间距处理
        activity?.let { StatusBarUtils.darkMode(it) }
        activity?.let { StatusBarUtils.setPaddingSmart(it, toolbar) }
    }

    override fun lazyLoad() {

    }
}