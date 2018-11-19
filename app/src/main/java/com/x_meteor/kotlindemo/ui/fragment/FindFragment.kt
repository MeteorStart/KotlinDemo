package com.x_meteor.kotlindemo.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.base.BaseFragment
import com.x_meteor.kotlindemo.base.BaseFragmentAdapter
import com.x_meteor.kotlindemo.utils.StatusBarUtils
import com.x_meteor.kotlindemo.view.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * @author: X_Meteor
 * @description: 发现Fragment
 * @version: V_1.0.0
 * @date: 2018/10/29 15:10
 * @company:
 * @email: lx802315@163.com
 */
class FindFragment : BaseFragment() {

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private var mTitle: String? = null

    companion object {

        fun getInstance(title: String): FindFragment {
            val fragment = FindFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }

    }

    override fun getLayoutId(): Int = R.layout.fragment_find

    override fun initView() {
        //状态栏透明和间距处理
        activity?.let { StatusBarUtils.darkMode(it) }
        activity?.let { StatusBarUtils.setPaddingSmart(it, toolBarFind) }
        activity?.let { StatusBarUtils.setPaddingSmart(it, tvFindTitle) }
        activity?.let { StatusBarUtils.setPaddingSmart(it, tabLayoutFind) }

        tvFindTitle.text = mTitle

        tabList.add("关注")
        tabList.add("分类")

        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))

        vpFind.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        tabLayoutFind.setupWithViewPager(vpFind)
        TabLayoutHelper.setUpIndicatorWidth(tabLayoutFind)
    }

    override fun lazyLoad() {
    }
}