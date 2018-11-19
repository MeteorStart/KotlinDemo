package com.x_meteor.kotlindemo.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.x_meteor.kotlindemo.base.BasePresenter
import com.x_meteor.kotlindemo.mvp.contract.CategoryContract
import com.x_meteor.kotlindemo.mvp.model.CategoryModel

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/8 17:23
 * @company:
 * @email: lx802315@163.com
 */
class CategoryPersenterImp : BasePresenter<CategoryContract.CategoryView>(), CategoryContract.CategoryPresenter {

    private val rankModel by lazy { CategoryModel() }

    /**
     * 请求排行榜数据
     * */
    override fun requestRankList() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = rankModel.requestCategoryList()
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