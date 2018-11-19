package com.x_meteor.kotlindemo.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.base.BaseFragment
import com.x_meteor.kotlindemo.base.BaseFragmentAdapter
import com.x_meteor.kotlindemo.mvp.contract.HotTabContract
import com.x_meteor.kotlindemo.mvp.model.bean.TabInfoBean
import com.x_meteor.kotlindemo.mvp.presenter.HotTabPresenterImp
import com.x_meteor.kotlindemo.showToast
import com.x_meteor.kotlindemo.utils.StatusBarUtils
import com.x_meteor.kotlindemo.view.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/10/29 14:32
 * @company:
 * @email: lx802315@163.com
 */
class HotFragment : BaseFragment(), HotTabContract.HotTabView {

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private var mTitle: String? = null

    private val mPresenter by lazy { HotTabPresenterImp() }

    init {
        mPresenter.attachView(this)
    }

    companion object {

        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }

    }

    override fun getLayoutId(): Int = R.layout.fragment_find

    override fun initView() {

        mLayoutStatusView = multipleStatusViewFind
        //状态栏透明和间距处理
        activity?.let { StatusBarUtils.darkMode(it) }
        activity?.let { StatusBarUtils.setPaddingSmart(it, toolBarFind) }
        activity?.let { StatusBarUtils.setPaddingSmart(it, tvFindTitle) }
        activity?.let { StatusBarUtils.setPaddingSmart(it, tabLayoutFind) }

        tvFindTitle.text = mTitle
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        mLayoutStatusView?.showContent()

        tabInfoBean.tabInfo.tabList.mapTo(tabList) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(fragments) { RankFragment.getInstance(it.apiUrl) }

        vpFind.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        tabLayoutFind.setupWithViewPager(vpFind)
        TabLayoutHelper.setUpIndicatorWidth(tabLayoutFind, 30f)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}