package com.x_meteor.kotlindemo.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.x_meteor.kotlindemo.base.BasePresenter
import com.x_meteor.kotlindemo.mvp.contract.FollowContract
import com.x_meteor.kotlindemo.mvp.model.FollowModel

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/1 16:29
 * @company:
 * @email: lx802315@163.com
 */
class FollowPresenter : BasePresenter<FollowContract.FollowView>(), FollowContract.FollowPresenter {

    private val followModel by lazy { FollowModel() }

    private var nextPageUrl: String? = null

    override fun requestFollowList() {
        checkViewAttached()

        mRootView?.showLoading()
        val disposable = followModel.requestFollowList()
            .subscribe({ issue ->
                mRootView?.apply {
                    dismissLoading()
                    nextPageUrl = issue.nextPageUrl
                    setFollowInfo(issue)
                }
            }, { throwable ->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)

    }

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            followModel.loadMoreData(it)
                .subscribe({ issue->
                    mRootView?.apply {
                        nextPageUrl = issue.nextPageUrl
                        setFollowInfo(issue)
                    }

                },{ t ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }
                })


        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }

}