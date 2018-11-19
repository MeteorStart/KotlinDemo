package com.x_meteor.kotlindemo.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.base.BaseFragment
import com.x_meteor.kotlindemo.mvp.contract.CategoryContract
import com.x_meteor.kotlindemo.mvp.model.bean.CategoryBean
import com.x_meteor.kotlindemo.mvp.presenter.CategoryPersenterImp
import com.x_meteor.kotlindemo.ui.adapter.CategoryDetailAdapter
import com.x_meteor.kotlindemo.utils.DisplayManager
import com.x_meteor.kotlindemo.utils.ToastUtils.Companion.showToast
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/8 16:54
 * @company:
 * @email: lx802315@163.com
 */
class CategoryFragment : BaseFragment(), CategoryContract.CategoryView {

    private val mPresenter by lazy { CategoryPersenterImp() }

    private var itemList = ArrayList<CategoryBean>()

    private var mTitle: String? = null

    private val mAdapter by lazy {
        activity?.let {
            CategoryDetailAdapter(R.layout.item_category_detail, itemList)
        }
    }

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId() = R.layout.fragment_category

    override fun initView() {
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                val position = parent.getChildPosition(view)
                val offset = DisplayManager.dip2px(2f)!!

                outRect.set(
                    if (position % 2 == 0) 0 else offset, offset,
                    if (position % 2 == 0) offset else 0, offset
                )
            }

        })
        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        mPresenter.requestRankList()
    }

    override fun setRankList(itemList: ArrayList<CategoryBean>) {
        multipleStatusView.showContent()

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