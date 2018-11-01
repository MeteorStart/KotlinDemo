package com.x_meteor.kotlindemo.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.scwang.smartrefresh.header.MaterialHeader
import com.x_meteor.kotlindemo.MyApplication
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.base.BaseFragment
import com.x_meteor.kotlindemo.mvp.contract.HandpickContract
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.mvp.presenter.HandpickPresenter
import com.x_meteor.kotlindemo.ui.adapter.HandpickAdapter
import com.x_meteor.kotlindemo.utils.StatusBarUtils
import com.x_meteor.kotlindemo.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_handpick.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: X_Meteor
 * @description: 每日精选Fragment
 * @version: V_1.0.0
 * @date: 2018/10/29 14:32
 * @company:
 * @email: lx802315@163.com
 */
class HandpickFragment : BaseFragment(), HandpickContract.HandpickView {

    private val mPresenter by lazy { HandpickPresenter() }

    private var mTitle: String? = null

    private var num: Int = 1

    private var mHandpickAdapter: HandpickAdapter? = null

    private var loadingMore = false

    private var isRefresh = false

    private var mMaterialHeader: MaterialHeader? = null

    companion object {
        fun getInstance(title: String): HandpickFragment {
            val fragment = HandpickFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    override fun getLayoutId(): Int = R.layout.fragment_handpick

    override fun initView() {
        mPresenter.attachView(this)
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景:
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)


        recyHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = recyHome.childCount
                    val itemCount = recyHome.layoutManager.itemCount
                    val firstVisibleItem =
                        (recyHome.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            //RecyclerView滚动的时候调用
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolBar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHandpickAdapter?.mData!!.size > 1) {
                        toolBar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHandpickAdapter!!.mData
                        val item = itemList[currentVisibleItemPosition + mHandpickAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }


            }
        })

        iv_search.setOnClickListener { openSearchActivity() }

        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        activity?.let { StatusBarUtils.darkMode(it) }
        activity?.let { StatusBarUtils.setPaddingSmart(it, toolBar) }

    }

    private fun openSearchActivity() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val options = activity?.let { ActivityOptionsCompat.makeSceneTransitionAnimation(it, iv_search, iv_search.transitionName) }
//            startActivity(Intent(activity, SearchActivity::class.java), options?.toBundle())
//        } else {
//            startActivity(Intent(activity, SearchActivity::class.java))
//        }
    }

    override fun lazyLoad() {
        mPresenter.requestHomeData(num)
    }

    override fun setHandpickData(handpickBean: HandpickBean) {
        mLayoutStatusView?.showContent()
//        Logger.d(handpickBean)

        // Adapter
        mHandpickAdapter = activity?.let { HandpickAdapter(it,handpickBean.issueList[0].itemList) }
        //设置 banner 大小
        mHandpickAdapter?.setBannerSize(handpickBean.issueList[0].count)

        recyHome.adapter = mHandpickAdapter
        recyHome.layoutManager = linearLayoutManager
        recyHome.itemAnimator = DefaultItemAnimator()

    }

    override fun setMoreData(itemList: ArrayList<HandpickBean.Issue.Item>) {
        loadingMore = false
        mHandpickAdapter?.addItemData(itemList)
    }

    override fun showError(msg: String, errorCode: Int) {
        ToastUtils.showToast(MyApplication.context, msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }
}