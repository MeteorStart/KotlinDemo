package com.x_meteor.kotlindemo.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.x_meteor.kotlindemo.base.BasePresenter
import com.x_meteor.kotlindemo.mvp.contract.HandpickContract
import com.x_meteor.kotlindemo.mvp.model.HandpickMode
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean

/**
 * @author: X_Meteor
 * @description: * 首页精选的 Presenter
 * (数据是 Banner 数据和一页数据组合而成的 HomeBean,查看接口然后在分析就明白了)
 * @version: V_1.0.0
 * @date: 2018/10/30 10:23
 * @company:
 * @email: lx802315@163.com
 */
class HandpickPresenter : BasePresenter<HandpickContract.HandpickView>(), HandpickContract.Presenter {

    //网络获取数据体
    private var bannerHandpickBean: HandpickBean? = null
    //加载首页的Banner 数据 + 一页数据合并后，nextPageUrl没 add
    private var nextPageUrl: String? = null
    //懒加载 HandpickMode
    private val handpickModel: HandpickMode by lazy {
        HandpickMode()
    }


    /**
     * @description: 获取首页精选数据 banner 加 一页数据
     * @date: 2018/10/30 10:54
     * @author: Meteor
     * @email: lx802315@163.com
     */
    override fun requestHomeData(num: Int) {
        // 检测是否绑定 View
        checkViewAttached()
        //加载loading框
        mRootView?.showLoading()
        val disposable = handpickModel.requestHomeData(num)
            .flatMap { it ->
                //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                val bannerItemList = it.issueList[0].itemList

                bannerItemList.filter { item ->
                    item.type == "banner2" || item.type == "horizontalScrollCard"
                }.forEach { item ->
                    //移除 item
                    bannerItemList.remove(item)
                }

                bannerHandpickBean = it //记录第一页是当做 banner 数据
                //根据 nextPageUrl 请求下一页数据
                handpickModel.loadMoreData(it.nextPageUrl)
            }.subscribe({
                //请求成功处理
                mRootView?.apply {
                    dismissLoading()
                    nextPageUrl = it.nextPageUrl

                    //过滤掉不需要的数据
                    val newBannerItemList = it.issueList[0].itemList
                    newBannerItemList.filter { item ->
                        //如果item type == "banner2" 或者 item type == "horizontalScrollCard"
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        newBannerItemList.remove(item)
                    }

                    newBannerItemList.filter { item ->
                        item.type == "video"
                    }.forEach { item ->
                        item.type = "banner"
                    }

                    // 重新赋值 Banner 长度
                    bannerHandpickBean!!.issueList[0].count = bannerHandpickBean!!.issueList[0].itemList.size

                    //赋值过滤后的数据 + banner 数据
                    bannerHandpickBean?.issueList!![0].itemList.addAll(newBannerItemList)

                    //为页面设置数据
                    setHandpickData(bannerHandpickBean!!)
                }
            }, { t ->
                //请求失败处理
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })
        //添加请求到请求列表
        addSubscription(disposable)
    }

    /**
     * @description: 加载更多
     * @date: 2018/10/30 10:54
     * @author: Meteor
     * @email: lx802315@163.com
     */
    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {

            handpickModel.loadMoreData(it)
                .subscribe({ handpickBean ->
                    mRootView?.apply {
                        //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                        val newItemList = handpickBean.issueList[0].itemList

                        newItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            //移除 item
                            newItemList.remove(item)
                        }

                        nextPageUrl = handpickBean.nextPageUrl
                        setMoreData(newItemList)
                    }

                }, { t ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })


        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }
}