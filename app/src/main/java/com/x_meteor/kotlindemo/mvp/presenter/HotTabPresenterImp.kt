package com.x_meteor.kotlindemo.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.x_meteor.kotlindemo.base.BasePresenter
import com.x_meteor.kotlindemo.mvp.contract.HotTabContract
import com.x_meteor.kotlindemo.mvp.model.HotTabModel

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/14 16:25
 * @company:
 * @email: lx802315@163.com
 */
class HotTabPresenterImp : BasePresenter<HotTabContract.HotTabView>(), HotTabContract.HotTabPresenter {

    private val hotTabModel by lazy { HotTabModel() }

    override fun getTabInfo() {
        checkViewAttached()
        mRootView?.showLoading()

        val disposable = hotTabModel.getTabInfo()
            .subscribe({ tabInfo ->
                mRootView?.setTabInfo(tabInfo)
            }, { t ->
                mRootView?.showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
            })
        addSubscription(disposable)
    }
}