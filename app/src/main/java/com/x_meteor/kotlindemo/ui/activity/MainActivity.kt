package com.x_meteor.kotlindemo.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.base.BaseActivity
import com.x_meteor.kotlindemo.base.BaseFragmentAdapter
import com.x_meteor.kotlindemo.ui.fragment.FindFragment
import com.x_meteor.kotlindemo.ui.fragment.HandpickFragment
import com.x_meteor.kotlindemo.ui.fragment.HotFragment
import com.x_meteor.kotlindemo.ui.fragment.MyFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {

    private var pagerAdapter: FragmentPagerAdapter? = null
    //声明一个集合用于存放Fragment
    private var fragments: ArrayList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView() {
        bottomMain.enableAnimation(false)
        bottomMain.enableShiftingMode(false)

    }

    override fun initListener() {

        vpMain.addOnAdapterChangeListener { viewPager, oldAdapter, newAdapter ->

        }
        //viewpager更换监听
        vpMain.addOnAdapterChangeListener(object : ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
            override fun onAdapterChanged(viewPager: ViewPager, oldAdapter: PagerAdapter?, newAdapter: PagerAdapter?) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bottomMain.menu.getItem(position).isChecked = true
            }

        })


        //设置viewPager为不可滑动状态,存在主Fragment无法切换左右切换
        vpMain?.setOnTouchListener({ v, event ->
            //修改为true 不可滑动，false 可滑动
            false
        })

        //bottom点击监听
        bottomMain.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.btn_handpick -> vpMain.currentItem = 0
                R.id.btn_find -> vpMain.currentItem = 1
                R.id.btn_hot -> vpMain.currentItem = 2
                R.id.btn_my -> vpMain.currentItem = 3
                else -> -1
            }
        }

    }

    override fun initData() {
        //初始化数据
        fragments = ArrayList()
        fragments?.add(HandpickFragment())
        fragments?.add(FindFragment())
        fragments?.add(HotFragment())
        fragments?.add(MyFragment())

        pagerAdapter = BaseFragmentAdapter(supportFragmentManager, fragments!!)
        //为viewpager设置适配器
        vpMain?.adapter = pagerAdapter

        bottomMain?.setupWithViewPager(vpMain)
    }

    override fun getNetData() {
    }

}
