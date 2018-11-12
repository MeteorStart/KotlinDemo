package com.x_meteor.kotlindemo.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.x_meteor.kotlindemo.base.BasePresenter
import com.x_meteor.kotlindemo.mvp.contract.RankContract
import com.x_meteor.kotlindemo.mvp.model.RankModel

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/8 17:23
 * @company:
 * @email: lx802315@163.com
 */
class RankPersenter : BasePresenter<RankContract.RankView>(), RankContract.RankPresenter {

    private val rankModel by lazy { RankModel() }

    /**
     * 请求排行榜数据
     * */
    override fun requestRankList() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = rankModel.requestRankList()
            .subscribe({ data ->
                mRootView?.apply {
                    dismissLoading()
                    setRankList(data)
                }
            }, { error ->
                mRootView?.showError(ExceptionHandle.handleException(error), ExceptionHandle.errorCode)
            })

        addSubscription(disposable)
    }
}