package com.x_meteor.kotlindemo.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.base.BaseFragment
import com.x_meteor.kotlindemo.mvp.contract.CategoryContract
import com.x_meteor.kotlindemo.mvp.contract.RankContract
import com.x_meteor.kotlindemo.mvp.model.bean.CategoryBean
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.mvp.presenter.CategoryPersenterImp
import com.x_meteor.kotlindemo.mvp.presenter.RankPresenterImp
import com.x_meteor.kotlindemo.showToast
import com.x_meteor.kotlindemo.ui.adapter.CategoryDetailAdapter
import com.x_meteor.kotlindemo.ui.adapter.RankAdapter
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/12 16:11
 * @company:
 * @email: lx802315@163.com
 */
class RankFragment : BaseFragment(), RankContract.RankView {

    private val rankPersenter by lazy { RankPresenterImp() }
    private var itemList = ArrayList<HandpickBean.Issue.Item>()

    private val mAdapter by lazy {
        activity?.let {
            RankAdapter(R.layout.item_rank, itemList)
        }
    }

    private var apiUrl: String? = null

    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    init {
        rankPersenter.attachView(this)
    }

    override fun getLayoutId() = R.layout.fragment_rank

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter

        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            rankPersenter.attachView(this)
            rankPersenter.requestRankList(apiUrl!!)
        }
    }

    override fun setRankList(itemList: ArrayList<HandpickBean.Issue.Item>) {
        mLayoutStatusView?.showContent()
        mAdapter?.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
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
        rankPersenter.detachView()
    }
}