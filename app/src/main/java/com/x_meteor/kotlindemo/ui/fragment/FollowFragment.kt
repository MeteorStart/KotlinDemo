package com.x_meteor.kotlindemo.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.base.BaseFragment
import com.x_meteor.kotlindemo.mvp.contract.FollowContract
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.mvp.presenter.FollowPresenter
import com.x_meteor.kotlindemo.ui.adapter.FollowAdapter
import com.x_meteor.kotlindemo.utils.StatusBarUtils
import com.x_meteor.kotlindemo.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.fragment_follow.*
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.x_meteor.kotlindemo.R.id.mRecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.support.v7.widget.RecyclerView
import com.x_meteor.kotlindemo.R.id.mRecyclerView


/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/1 14:17
 * @company:
 * @email: lx802315@163.com
 */
class FollowFragment : BaseFragment(), FollowContract.FollowView {

    private var mTitle: String? = null

    private var itemList = ArrayList<HandpickBean.Issue.Item>()

    private val mPresenter by lazy { FollowPresenter() }

    private val mFollowAdapter by lazy { activity?.let { FollowAdapter(R.layout.item_follow, itemList) } }

    /**
     * 是否加载更多
     */
    private var loadingMore = false

    init {
        mPresenter.attachView(this)
    }

    companion object {
        fun getInstance(title: String): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_follow

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mFollowAdapter
        //实现自动加载
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    mFollowAdapter?.setEnableLoadMore(true)
                    mPresenter.loadMoreData()
                }
            }
        })

        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }

    override fun setFollowInfo(issue: HandpickBean.Issue) {
        loadingMore = false
        itemList = issue.itemList

        mFollowAdapter?.setOnLoadMoreListener({
            mRecyclerView.postDelayed({
                if (mFollowAdapter?.isLoadMoreEnable!!) {
                    if (itemList?.size < 4) {
                        mFollowAdapter?.loadMoreEnd()
                    } else if (itemList.size == 4) {
                        mFollowAdapter?.loadMoreComplete()
                    }
                }
            }, 1000)
        }, mRecyclerView)

        mFollowAdapter?.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        ToastUtils.showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}